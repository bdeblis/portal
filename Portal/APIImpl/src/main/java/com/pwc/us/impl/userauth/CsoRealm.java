package com.pwc.us.impl.userauth;

import com.pwc.us.authentication.AuthenticationImpl;
import com.pwc.us.common.Authentication;
import com.pwc.us.common.exception.CsoAuthException;
import com.pwc.us.common.exception.CsoLoginNoAuthException;
import com.pwc.us.common.model.User;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class CsoRealm extends AuthorizingRealm {

    private static Logger logger = LoggerFactory.getLogger(CsoRealm.class);
    Authentication authServer = new AuthenticationImpl();
    private static final String AGENT_ROLE = "agent";
    private static final String POLICYHOLDER_ROLE = "policyholder";
    private static final String CASHIER_ROLE = "cashier";

    public CsoRealm() {
        try {
            authServer.connectToServer();
        } catch (CsoAuthException e) {
            logger.error("fatal error connecting to Authentication server", e);
        }
    }

    /**
     * This method is called when the user logs into the system.
     *
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        User user = null;
        try {
            user = authServer.login(upToken.getUsername(),
                    new String(upToken.getPassword()));
            if (user == null) {
                throw new AuthenticationException("Login name [" + upToken.getUsername() + "] not found!");
            } else if (authServer.isAccountLocked(upToken.getUsername())) {
                throw new LockedAccountException("Login name [" + upToken.getUsername() + "] has a locked account!");
            }
            Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();
            session.setAttribute("user", user);
            logger.info("Found user with username [{}]", upToken.getUsername());
        } catch (CsoLoginNoAuthException ex) {
            logger.error("unable to authenticate user " + upToken.getUsername(), ex);
        } catch (CsoAuthException e) {
            logger.error("unable to authenticate user " + upToken.getUsername(), e);
        }
        return new SimpleAuthenticationInfo(user, upToken.getPassword(), getName());
    }

    /**
     * this method loads user authorization data from an authorization source.
     * User, Role are custom POJOs (beans) and are loaded from the authorization
     * implementation. WildcardPermission implements Shiro's Permission
     * interface, allowing the permissions to get accepted by Shiro's security
     * framework.
     *
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
        Set<String> roles = new HashSet<String>();
        Set<Permission> permissions = new HashSet<Permission>();
        Collection<User> principalsList = principals.byType(User.class);

        if (principalsList.isEmpty()) {
            throw new AuthorizationException("Empty principals list!");
        }

        for (User principal : principalsList) {
            logger.info("login: " + principal.getLogin());
        }
        User[] principalArray = principalsList.toArray(new User[principalsList.size()]);
        User principal = principalArray[0];

        // The roles right now are very simple: you're either an agent or a policyholder
        try {
            if (authServer.isAgent(principal.getLogin())) {
                roles.add(AGENT_ROLE);
            }
            if (authServer.isPolicyholder(principal.getLogin())) {
                roles.add(POLICYHOLDER_ROLE);
            }
            if(authServer.isCashier(principal.getLogin())){
                roles.add(CASHIER_ROLE);
            }
            
        } catch (CsoAuthException e) {
            logger.error("unable to determine if user " + principal.getLogin() + " is agent or policyholder.", e);
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        info.setRoles(roles); //fill in roles 
        info.setObjectPermissions(permissions);

        return info;
    }
}
