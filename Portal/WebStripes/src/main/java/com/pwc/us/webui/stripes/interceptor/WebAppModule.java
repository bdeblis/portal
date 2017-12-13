package com.pwc.us.webui.stripes.interceptor;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.pwc.us.authentication.AuthenticationImpl;
import com.pwc.us.common.Authentication;
import com.pwc.us.common.COIRequest;
import com.pwc.us.common.FNOIRequest;
import com.pwc.us.common.GwRegistrationService;
import com.pwc.us.common.OutOfState;
import com.pwc.us.common.PayOnline;
import com.pwc.us.common.PayrollReport;
import com.pwc.us.common.QuoteRequest;
import com.pwc.us.common.WebAppAPI;
import com.pwc.us.impl.COIRequestImpl;
import com.pwc.us.impl.FNOIRequestImpl;
import com.pwc.us.impl.OutOfStateImpl;
import com.pwc.us.impl.PayrollReportImpl;
import com.pwc.us.impl.QuoteRequestImpl;
import com.pwc.us.impl.userauth.CsoRealm;
import com.pwc.us.impl.PayOnlineImpl;
import com.pwc.us.impl.web.WebAppAPIImpl;
import com.pwc.us.wsclient.service.GwRegistrationServiceImpl;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class WebAppModule extends AbstractModule {

    private static Logger logger = LoggerFactory.getLogger(WebAppModule.class);

    @Override
    protected void configure() {
        logger.info("injecting Guice configuration WebAppModule");
        bind(WebAppAPI.class).to(WebAppAPIImpl.class);
        bind(Realm.class).to(CsoRealm.class).in(Singleton.class);
        bind(Authentication.class).to(AuthenticationImpl.class).in(Singleton.class);
        bind(GwRegistrationService.class).to(GwRegistrationServiceImpl.class);
        bind(COIRequest.class).to(COIRequestImpl.class);
        bind(FNOIRequest.class).to(FNOIRequestImpl.class);
        bind(OutOfState.class).to(OutOfStateImpl.class);
        bind(QuoteRequest.class).to(QuoteRequestImpl.class);
        bind(PayrollReport.class).to(PayrollReportImpl.class);
        bind(PayOnline.class).to(PayOnlineImpl.class);
    }
}
