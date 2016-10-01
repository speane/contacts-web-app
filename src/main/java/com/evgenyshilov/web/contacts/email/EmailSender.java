package com.evgenyshilov.web.contacts.email;

import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.resources.ApplicationConfig;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Evgeny Shilov on 25.09.2016.
 */
public class EmailSender {

    public void sendEmail(String emailAddress, String mail, String theme) throws CustomException {
        Properties properties = prepareProperties();
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String password = ApplicationConfig.getProperty("EMAIL_SEND_AUTH_PASSWORD");
                String email = ApplicationConfig.getProperty("EMAIL_SEND_AUTH_ADDRESS");
                return new PasswordAuthentication(email, password);
            }
        });

        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("contacts.mail.server225@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
            message.setSubject(theme);
            message.setText(mail);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new CustomException("Can't send message: ", e);
        }
    }

    private Properties prepareProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        return properties;
    }
}
