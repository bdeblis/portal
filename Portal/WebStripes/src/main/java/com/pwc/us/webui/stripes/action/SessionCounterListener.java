/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.webui.stripes.action;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionCounterListener implements HttpSessionListener {

    private static Logger logger = LoggerFactory.getLogger(SessionCounterListener.class);
    private static int totalActiveSessions=0;

    public static int getTotalActiveSession() {
        return totalActiveSessions;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        totalActiveSessions++;
        logger.info("time: "+System.currentTimeMillis() + " (session) Created:ID=" + session.getId() + " active sessions "+totalActiveSessions);
        
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        totalActiveSessions--;
        logger.info("time: "+ System.currentTimeMillis() + " (session) Destroyed:ID="+ session.getId() + " active sessions "+totalActiveSessions);
    }

}