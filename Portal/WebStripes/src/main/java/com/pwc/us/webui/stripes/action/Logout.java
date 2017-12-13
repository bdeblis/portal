package com.pwc.us.webui.stripes.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class Logout extends BaseActionBean {
    private static final String VIEW = "/WEB-INF/jsp/user/login.jsp";
    private static Logger logger = LoggerFactory.getLogger(Logout.class);
    
    private String message = null;
    
    @DefaultHandler
    public Resolution logoutUser() {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
        } catch (AuthenticationException ex) {
            logger.error("Problem logging out user");
        }        
        return new ForwardResolution(VIEW);
    }
    
    
    public Resolution logoutFromPc() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        this.getContext().getMessages().add(new SimpleMessage(message));
        return new ForwardResolution(VIEW);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
