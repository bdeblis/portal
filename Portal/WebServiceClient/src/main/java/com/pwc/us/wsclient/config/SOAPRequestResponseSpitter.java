/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.wsclient.config;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.Binding;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SOAPRequestResponseSpitter implements SOAPHandler<SOAPMessageContext> {

    private static final Logger logger = LoggerFactory.getLogger(SOAPRequestResponseSpitter.class);

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        logToSystemOut(context);
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        logToSystemOut(context);
        return true;
    }

    private void logToSystemOut(SOAPMessageContext smc) {
        Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty.booleanValue()) {
            System.out.println("\nOutbound message:");
            logger.info("\nOutbound message:");
        } else {
            System.out.println("\nInbound message:");
            logger.info("\nInbound message:");
        }

        SOAPMessage message = smc.getMessage();
        try {
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            // Set formatting
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            Source sc = message.getSOAPPart().getContent();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(baos);
            tf.transform(sc, result);
            System.out.println(baos.toString());
            logger.info(baos.toString());

        } catch (TransformerConfigurationException e) {
            logger.error("TransformerConfiguraionException trying to format SOAP messages");
        } catch (Exception e) {
            System.out.println("Exception in handler: " + e);
        }
    }

    @Override
    public void close(MessageContext context) {
    }

    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

    @SuppressWarnings("rawtypes")
    /**
     * This static method adds the handler to the provided port's binding
     * object.
     *
     * @param binding - The binding object can be fetched *
     * by <code>((BindingProvider) port).getBinding()</code>
     */
    public static void addToPort(Binding binding) {
        List<Handler> handlerChain = binding.getHandlerChain();
        handlerChain.add(new SOAPRequestResponseSpitter());

        /*
         * Check List<Handler> javax.xml.ws.Binding.getHandlerChain() javadocs.
         * It states: Gets a copy of the handler chain for a protocol binding
         * instance. If the returned chain is modified a call to setHandlerChain
         * is required to configure the binding instance with the new chain.
         */
        binding.setHandlerChain(handlerChain);
    }
}