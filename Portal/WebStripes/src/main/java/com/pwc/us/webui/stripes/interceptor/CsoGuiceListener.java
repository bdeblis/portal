package com.pwc.us.webui.stripes.interceptor;

import com.google.inject.Injector;
import com.pwc.us.common.Authentication;
import com.silvermindsoftware.sg.context.GuiceContextListener;
import javax.servlet.ServletContextEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class CsoGuiceListener extends GuiceContextListener {

    private static Logger logger = LoggerFactory.getLogger(CsoGuiceListener.class);

    @Override
    protected Injector getInjector() {
        return super.getInjector();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            Authentication auth = getInjector().getInstance(Authentication.class);
            auth.shutDownConnectionPool();
            
            super.contextDestroyed(sce);

        } catch (SecurityException e) {
            String msg = "SecurityException cleaning up CsoGuiceListener";
            logger.warn(msg, e);
        } catch (IllegalArgumentException e) {
            String msg = "IllegalArgumentException cleaning up CsoGuiceListener";
            logger.warn(msg, e);
        }
    }
}
