package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.database.dao.ContactDAO;
import com.evgenyshilov.web.contacts.database.dao.DAOFactory;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.email.EmailSender;
import org.stringtemplate.v4.ST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 25.09.2016.
 */
public class SendEmailCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        String emailPattern = request.getParameter("email-text-area");
        ArrayList<Contact> recipients = new ArrayList<>();
        ContactDAO contactDAO = (ContactDAO) DAOFactory.getDAO(Contact.class);
        for (String idString : request.getParameterValues("recipient-id")) {
            recipients.add(contactDAO.get(Integer.parseInt(idString)));
        }
        EmailSender emailSender = new EmailSender();
        for (Contact recipient : recipients) {
            ST emailTemplate = new ST(emailPattern);
            emailTemplate.add("first_name", recipient.getFirstName());
            emailTemplate.add("last_name", recipient.getLastName());
            emailTemplate.add("patronymic", recipient.getPatronymic());
            emailSender.sendEmail(recipient.getEmail(), emailTemplate.render());
        }

        response.sendRedirect("/app/contact-list");

        return null;
    }
}
