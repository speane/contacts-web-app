package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.email.EmailSender;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.DBHelper;
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
        String REDIRECT_URL = "/contactlist.jsp";
        String emailTemplate = request.getParameter("email-text-area");
        ArrayList<Contact> recipients = null;
        try {
            recipients = getRecipientsIdsFromRequest(request);
            sendEmails(recipients, emailTemplate);
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
                recipients.add(contact);
            }
            return recipients;
        } catch (NumberFormatException | CustomException e) {
            throw new CustomException("Can't get recipients list from request: ", e);
        }

    }

    private void sendEmails(ArrayList<Contact> recipients, String template) throws CustomException {
        EmailSender emailSender = new EmailSender();
        try {
            for (Contact recipient : recipients) {
                ST emailTemplate = new ST(template);
                emailTemplate.add("first_name", recipient.getFirstName());
                emailTemplate.add("last_name", recipient.getLastName());
                emailTemplate.add("patronymic", recipient.getPatronymic());
                emailSender.sendEmail(recipient.getEmail(), emailTemplate.render());
            }
        } catch (CustomException e) {
            throw new CustomException("Can't send emails: ", e);
        }
    }
}
