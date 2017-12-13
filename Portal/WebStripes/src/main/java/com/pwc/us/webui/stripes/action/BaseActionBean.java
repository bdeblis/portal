/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.webui.stripes.action;

import com.google.inject.Inject;
import com.pwc.us.common.WebAppAPI;
import com.pwc.us.common.model.User;
import javax.servlet.http.HttpSession;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public abstract class BaseActionBean implements ActionBean {

    @Inject
    WebAppAPI api;
    protected ActionBeanContext ctx;

    public void setContext(ActionBeanContext ctx) {
        this.ctx = ctx;
    }

    public ActionBeanContext getContext() {
        return this.ctx;
    }

    public User getUser() {
        HttpSession session = ctx.getRequest().getSession();
        User user  = (User) session.getAttribute("user");
        return user;
    }
}
