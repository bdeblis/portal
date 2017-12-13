package com.pwc.us.impl.web;

import com.pwc.us.common.exception.CsoPasswordRecoveryException;
import com.pwc.us.common.model.PasswordRecover;
import com.pwc.us.common.utils.JndiUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class PasswordRecoveryEmail {

    private static final String HOST = JndiUtils.getSmtpHost();
    private static final String PORT = JndiUtils.getSmtpPort();
    private static final String FROM = JndiUtils.getSmtpFrom();
    private static final String SUBJECT = JndiUtils.getSmtpSubject();
    private static final String TEMPLATE = "recoverPasswordEmail.ftl";
    private static final String TO = "to";
    private static final String CHANGE_PASSWORD_URL = "changePasswordUrl";
    private static final String RECOVER_EVENT_FOR_URL = "_eventName=retrieveLink";
    private static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_SOCKET_FACTORY_PORT = "mail.smtp.socketFactory.port";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_PORT = "mail.smtp.port";
    private static final String UTF8 = "UTF-8";
    private static Logger logger = LoggerFactory.getLogger(PasswordRecoveryEmail.class);

    public static void sendPasswordRecoveryEmail(PasswordRecover recover)
            throws IOException, CsoPasswordRecoveryException {
        String emailTxt = createEmailText(recover);

        Session session = Session.getDefaultInstance(createEmailProps());
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recover.getEmail()));
            message.setSubject(SUBJECT);
            message.setText(emailTxt);

            //Nerfing the send until we get an open connection to the SMTP server (contacted Robert Fenton)
            Transport.send(message);
            logger.info("Email sent for password recovery: ");
            logger.info(emailTxt);

        } catch (MessagingException e) {
            String msg = "JavaMail threw exception trying to send password recovery email";
            throw new CsoPasswordRecoveryException(msg, e);
        }
    }

    public static String createEmailText(PasswordRecover recover)
            throws IOException, CsoPasswordRecoveryException {

        String emailUrl = createEmailUrl(recover);
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(PasswordRecoveryEmail.class, "/");
        Template template = cfg.getTemplate(TEMPLATE);
        Map<String, String> rootMap = new HashMap<String, String>();
        rootMap.put(TO, recover.getFullName());
        rootMap.put(CHANGE_PASSWORD_URL, emailUrl);
        Writer out = new StringWriter();
        try {
            template.process(rootMap, out);
        } catch (TemplateException e) {
            throw new CsoPasswordRecoveryException("Unable to create email template for password recovery", e);
        }

        return out.toString();
    }

    public static String createEmailUrl(PasswordRecover recover) throws UnsupportedEncodingException {
        String encTok = URLEncoder.encode(recover.getSecurityToken(), UTF8);
        String encLogin = URLEncoder.encode(recover.getUsername(), UTF8);
        return recover.getHost().append('?').append(RECOVER_EVENT_FOR_URL)
                .append("&token=").append(encTok).append("&login=")
                .append(encLogin).toString();
    }

    private static Properties createEmailProps() {
        Properties props = new Properties();
        props.put(MAIL_SMTP_HOST, HOST);
        props.put(MAIL_SMTP_SOCKET_FACTORY_PORT, PORT);
        props.put(MAIL_SMTP_AUTH, false);
        props.put(MAIL_SMTP_PORT, PORT);
        return props;
    }
}
