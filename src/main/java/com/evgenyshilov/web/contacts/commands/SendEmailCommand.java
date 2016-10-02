package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.email.EmailSender;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.database.DBHelper;
import com.evgenyshilov.web.contacts.help.email.EmailTemplateElementsFactory;
import org.apache.commons.lang3.StringUtils;
import org.stringtemplate.v4.ST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 25.09.2016.
 */
public class SendEmailCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CustomException {
        try {
            request.setCharacterEncoding("UTF-8");
            String REDIRECT_URL = "/app/contact-list";
            String emailTemplate = request.getParameter("message-text");
            String emailSubject = request.getParameter("message-theme");
            ArrayList<Contact> recipients;
            recipients = getRecipientsIdsFromRequest(request);
            sendEmails(recipients, emailTemplate, emailSubject);
            response.sendRedirect(REDIRECT_URL);
        } catch (CustomException | IOException e) {
            throw new CustomException("Can't execute send email command: ", e);
        }

        return null;
    }

    private ArrayList<Contact> getRecipientsIdsFromRequest(HttpServletRequest request) throws CustomException {
        ArrayList<Contact> recipients = new ArrayList<>();
        DBHelper dbHelper = new DBHelper();
        try {
            for (String idString : request.getParameterValues("recipient-id")) {
                int contactId = Integer.parseInt(idString);
                Contact contact = dbHelper.getContactFromDAO(contactId);
                if (!StringUtils.isEmpty(contact.getEmail())) {
                    recipients.add(contact);
                }
            }
            return recipients;
        } catch (NumberFormatException | CustomException e) {
            throw new CustomException("Can't get recipients list from request: ", e);
        }

    }

    private void sendEmails(ArrayList<Contact> recipients, String template, String subject) throws CustomException {
        EmailSender emailSender = new EmailSender();
        EmailTemplateElementsFactory elementsFactory = new EmailTemplateElementsFactory();
        template = elementsFactory.translateTemplateToEnglish(template);
        try {
            for (Contact recipient : recipients) {

                ST emailTemplate = new ST(template);
                String mailText = elementsFactory.buildTemplateWithContactFields(emailTemplate, recipient);
                emailSender.sendEmail(recipient.getEmail(), mailText, subject);
            }
        } catch (CustomException e) {
            throw new CustomException("Can't send emails: ", e);
        }
    }
}
