package com.pwc.us.webui.stripes.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.exception.ActionBeanNotFoundException;
import net.sourceforge.stripes.exception.DefaultExceptionHandler;
import net.sourceforge.stripes.validation.LocalizableError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * General Exception handler for errors that aren't handled in the normal
 * programming flow.
 * @author Roger
 */
public class PortalExceptionHandler extends DefaultExceptionHandler {
    
    private static final String VIEW = "/WEB-INF/jsp/error.jsp";
    private static Logger logger = LoggerFactory.getLogger(PortalExceptionHandler.class);
    
    public Resolution catchActionBeanNotFound(
        ActionBeanNotFoundException exc,
        HttpServletRequest req, HttpServletResponse resp)
    {
        logger.debug("com.pwc.us.webui.stripes.action.PortalExceptionHandler.Resoulution catchActionBeanNotFound uri: "+req.getRequestURI());
        logger.debug("com.pwc.us.webui.stripes.action.PortalExceptionHandler.Resoulution url: "+req.getRequestURL());
        return new ErrorResolution(HttpServletResponse.SC_NOT_FOUND);
    }

    public void catchAll(Throwable throwable, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.error("An unhandled exception occurred that resulted in a system error message.", throwable);
        request.getRequestDispatcher(VIEW).forward(request, response);
    }
    
}
