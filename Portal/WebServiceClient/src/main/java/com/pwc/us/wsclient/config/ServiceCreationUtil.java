/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.wsclient.config;

import com.sun.xml.ws.client.BindingProviderProperties;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class ServiceCreationUtil {

    private static final Logger logger = LoggerFactory.getLogger(ServiceCreationUtil.class);

    public static <T> T createService(String wsdl, String svcQnameNamespaceUri,
            String svcQnameLocalpart, String portQnameNamespaceUri,
            String portQnameLocalpart, Class<T> serviceInterface,
            int requestTimeout, int connectTimeout)
            throws WebServiceException, MalformedURLException {
        T port = null;
        try {
            URL wsdlLocation = new URL(wsdl);
            QName serviceQName = new QName(svcQnameNamespaceUri, svcQnameLocalpart);
            Service service = Service.create(wsdlLocation, serviceQName);
            com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump = true;
            port = service.getPort(new QName(portQnameNamespaceUri, portQnameLocalpart), serviceInterface);
            Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
            requestContext.put(BindingProviderProperties.REQUEST_TIMEOUT, requestTimeout);
            requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, connectTimeout);
           // set up a way to log requests 
            BindingProvider bindProv = (BindingProvider) port;
            SOAPRequestResponseSpitter.addToPort(bindProv.getBinding());
            logger.debug("createService "+wsdl+" req time "+requestTimeout+" connect "+connectTimeout);
            
        } catch (MalformedURLException e) {
            logger.error("could not create service", e);
            throw e;
        }
        return port;
    }
    
}
