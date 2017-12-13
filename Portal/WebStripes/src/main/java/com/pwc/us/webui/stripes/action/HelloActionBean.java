package com.pwc.us.webui.stripes.action;

import com.pwc.us.webui.stripes.util.Security;
import java.util.Date;
import java.util.Random;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class HelloActionBean extends BaseActionBean {
    
    private Date date;
    private static final String VIEW = "/WEB-INF/jsp/hello.jsp";
    private static Logger logger = LoggerFactory.getLogger(HelloActionBean.class);
    
    public Date getDate() {
        return this.date;
    }
    
    @DefaultHandler
    public Resolution currentDate() {
        if (!Security.checkIfPolicyholder()  && !Security.checkIfCashier()) {
            return new ForwardResolution(Logout.class);
        }
  
        Resolution ret = new ForwardResolution(VIEW);
        this.date = new Date();
        return ret;
    }
    
    public Resolution randomDate() {
        long max = System.currentTimeMillis();
        long random = new Random().nextLong() % max;
        date = new Date(random);
        return new ForwardResolution(VIEW);
    }
}
