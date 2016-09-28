package com.evgenyshilov.web.contacts.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Evgeny Shilov on 25.09.2016.
 */
public class EmailSender {

    public void sendEmail(String emailAddress, String mail) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("contacts.mail.server225@gmail.com", "123qwe123QWE");
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("contacts.mail.server225@gmail.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
        message.setSubject("test");
        message.setText(mail);

        Transport.send(message);
    }
}