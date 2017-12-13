/*
 * APIImpl.java
 */
package com.pwc.us.impl;

import com.pwc.us.common.OutOfState;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.model.OutOfStateAttachments;
import com.pwc.us.common.utils.NullChecker;
import com.pwc.us.common.utils.JndiUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author generated-by-archetype
 *
 */
public class OutOfStateImpl implements OutOfState {

    private static final String HOST = JndiUtils.getSmtpHost();
    private static final String PORT = JndiUtils.getSmtpPort();
    private static final String TO = JndiUtils.getSmtpToOOS();
    private static final String FROM = JndiUtils.getSmtpFrom();
    private static final String SUBJECT = JndiUtils.getSmtpSubjectOOS();
    private static final String TEMPLATE = "outOfStateEmai.ftl";
    private static final String TEMPLATE_TO = "to";
    private static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_SOCKET_FACTORY_PORT = "mail.smtp.socketFactory.port";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_PORT = "mail.smtp.port";
    private static final String UTF8 = "UTF-8";
    private static Logger logger = LoggerFactory.getLogger(OutOfStateImpl.class);


    public boolean processRequest(OutOfStateAttachments attachments)
            throws GwIntegrationException {

        boolean ret = false;

        Session session = Session.getDefaultInstance(createOOSEmailProps());

        try {
            String emailTxt = createEmailText();
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(TO));
            message.setSubject(SUBJECT);

            BodyPart msgBP = new MimeBodyPart();
            msgBP.setText(emailTxt);
            Multipart mPart = new MimeMultipart();
            mPart.addBodyPart(msgBP);
            
            if (NullChecker.isNotNullOrEmpty(attachments.getApplication())) {
                addAttachments(mPart, attachments.getApplication());
            }

            if (NullChecker.isNotNullOrEmpty(attachments.getSupplemental())) {
                addAttachments(mPart, attachments.getSupplemental());
            }
            
            if (NullChecker.isNotNullOrEmpty(attachments.getAddDocument1())) {
                addAttachments(mPart, attachments.getAddDocument1());
            }
            if (NullChecker.isNotNullOrEmpty(attachments.getAddDocument2())) {
                addAttachments(mPart, attachments.getAddDocument2());
            }
            if (NullChecker.isNotNullOrEmpty(attachments.getAddDocument3())) {
                addAttachments(mPart, attachments.getAddDocument3());
            }
            if (NullChecker.isNotNullOrEmpty(attachments.getAddDocument4())) {
                addAttachments(mPart, attachments.getAddDocument4());
            }
            if (NullChecker.isNotNullOrEmpty(attachments.getAddDocument5())) {
                addAttachments(mPart, attachments.getAddDocument5());
            }

            message.setContent(mPart);



            //Nerfing the send until we get an open connection to the SMTP server (contacted Robert Fenton)
            Transport.send(message);
            logger.info("Email sent for password recovery: ");
            logger.info(emailTxt);

            ret = true;

        } catch (MessagingException e) {
            String msg = "JavaMail threw exception trying to send out of state request email";
            throw new GwIntegrationException(msg, e);
        } catch (IOException ex) {
            logger.error("Unable to create email template for out of state request", ex);
            throw new GwIntegrationException("Unable to create email template for out of state request", ex);
        }

        return ret;

    }

    public void addAttachments(Multipart mPart, File file) {

        try {
            MimeBodyPart msgBP = new MimeBodyPart();
            msgBP.setFileName(file.getName());
            msgBP.attachFile(file);
            mPart.addBodyPart(msgBP);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String createEmailText()
            throws IOException, GwIntegrationException {

        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(OutOfStateImpl.class, "/");
        Template template = cfg.getTemplate(TEMPLATE);
        Map<String, String> rootMap = new HashMap<String, String>();
        rootMap.put(TEMPLATE_TO, TO);
        Writer out = new StringWriter();
        try {
            template.process(rootMap, out);
        } catch (TemplateException e) {
            logger.error("Unable to create email template for out of state request", e);
            throw new GwIntegrationException("Unable to create email template for out of state request", e);
        }

        return out.toString();
    }

    private static Properties createOOSEmailProps() {
        Properties props = new Properties();
        props.put(MAIL_SMTP_HOST, HOST);
        props.put(MAIL_SMTP_SOCKET_FACTORY_PORT, PORT);
        props.put(MAIL_SMTP_AUTH, false);
        props.put(MAIL_SMTP_PORT, PORT);
        return props;
    }
}
