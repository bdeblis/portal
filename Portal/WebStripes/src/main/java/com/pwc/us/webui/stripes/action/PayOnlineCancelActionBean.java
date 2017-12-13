package com.pwc.us.webui.stripes.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.Resolution;

/**
 *
 * @author rturnau001
 */
public class PayOnlineCancelActionBean extends BaseActionBean {

    private static final String RESULT_VIEW = "/WEB-INF/jsp/pay_online_result.jsp";
    private static final String PAY_CANCEL = "PayOnline.Error.PayCancellation";
    
    @DefaultHandler
    public Resolution form() {
        getContext().getMessages().add(new LocalizableMessage(PAY_CANCEL));
        return new ForwardResolution(RESULT_VIEW);
    }
}
