package com.pwc.us.webui.stripes.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Robert Snelling <robert.snelling@us.pwc.com>
 */
public class OutOfStateGuidelines extends BaseActionBean {

    private static Logger logger = LoggerFactory.getLogger(OutOfStateGuidelines.class);
    private static final String FORM = "/WEB-INF/jsp/outOfStateGuidelines.jsp";

    @DefaultHandler
    public Resolution form() {
        Resolution ret = null;

        ret = new ForwardResolution(FORM);

        return ret;
    }
}
