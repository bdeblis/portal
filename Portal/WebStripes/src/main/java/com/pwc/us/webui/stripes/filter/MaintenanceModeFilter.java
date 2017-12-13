/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.webui.stripes.filter;

import com.pwc.us.common.model.User;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.MDC;

public class MaintenanceModeFilter implements Filter {

    private FilterConfig filterConfigObj;
    private String redirectURL;
    private String adminPassword;

    private static final boolean MAINTENANCE_MODE = false;
    private static final int LENGTH = 90;

    private boolean maintenanceMode = MAINTENANCE_MODE;

    public void init(FilterConfig config) throws ServletException {
        this.filterConfigObj = config;
        this.redirectURL = config.getInitParameter("redirectURL");
        this.adminPassword = config.getInitParameter("adminPassword");

        StringBuilder initText = new StringBuilder(FilterUtils.outputTextDelimiter(true, "*", LENGTH));
        initText.append(FilterUtils.outputTextCentered("Servlet Misc Filters - Maintenance Filter", "*", LENGTH));

        if (this.redirectURL != null && this.adminPassword != null) {

            initText.append(FilterUtils.outputTextCentered("Redirect requests to: " + this.redirectURL, "*", LENGTH));
            initText.append(FilterUtils.outputTextCentered("", "*", LENGTH));
            initText.append(FilterUtils.outputTextCentered("Initialization successful!", "*", LENGTH));

        } else {
            initText.append(FilterUtils.outputTextCentered("Initialization failure! Parameter not found!", "*", LENGTH));
        }

        initText.append(FilterUtils.outputTextDelimiter(false, "*", LENGTH));
        this.filterConfigObj.getServletContext().log(initText.toString());

    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String maintenanceparam = request.getParameter("maintenancemode");
        //   String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            MDC.put("user-id", user.getLogin()+":"+user.getFirstName()+user.getLastName());
        }
        if (maintenanceparam != null && password != null) {
            //If the three needed parameters aren't null and their values are correct...
            if (this.adminPassword.equals(password)) {
                this.maintenanceMode = Boolean.parseBoolean(maintenanceparam);
            }
        }

        if (this.maintenanceMode == true) {
            //     System.out.println("MAINTENANCE MODE ON");
            request.getRequestDispatcher(this.redirectURL).forward(request, response);
            return;

        } else {
            //         System.out.println("MAINTENANCE MODE OFF");
        }
        try {
            chain.doFilter(request, response);
        } finally {
            if (user != null) {
                MDC.remove("user-id");
            }
        }

    }

    public void destroy() {
    }

}
