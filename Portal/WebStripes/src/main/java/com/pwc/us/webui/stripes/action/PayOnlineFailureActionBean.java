package com.pwc.us.webui.stripes.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.LocalizableError;

/**
 * 
 * @author rturnau001
 */
public class PayOnlineFailureActionBean extends BaseActionBean {
    
    private static final String RESULT_VIEW = "/WEB-INF/jsp/pay_online_result.jsp";
    private static final String PAY_FAILURE = "PayOnline.Error.PayFailure";

    @DefaultHandler
    public Resolution form() {
        getContext().getValidationErrors().addGlobalError(new LocalizableError(PAY_FAILURE));
        return new ForwardResolution(RESULT_VIEW);
    }
}
