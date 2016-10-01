package com.evgenyshilov.web.contacts.commands.forms;

import com.evgenyshilov.web.contacts.commands.Command;
import com.evgenyshilov.web.contacts.database.dao.ContactDAO;
import com.evgenyshilov.web.contacts.database.dao.DAOFactory;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.EmailTemplateElementsFactory;
import com.evgenyshilov.web.contacts.help.EmailTemplateFactory;
import com.evgenyshilov.web.contacts.help.RequestParser;
import com.evgenyshilov.web.contacts.resources.ApplicationConfig;
import com.evgenyshilov.web.contacts.resources.RussianEnglishTranslator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 25.09.2016.
 */
public class EmailFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CustomException {
        String EMAIL_VIEW_PAGE = "/sendemail.jsp";
        String CONTACT_LIST_URL = "/app/contact-list";
        ArrayList<Contact> recipients = null;
        try {
            recipients = getRecipients(request);

            System.out.println(recipients);

            if (recipients.size() > 0) {
                request.setAttribute("recipients", recipients);

                EmailTemplateFactory templateFactory = new EmailTemplateFactory(
                        ApplicationConfig.getProperty("EMAIL_TEMPLATE_GROUP_FILENAME"));

                request.setAttribute("templates", templateFactory.getAllEmailTemplates());

                EmailTemplateElementsFactory elementsFactory = new EmailTemplateElementsFactory();
                request.setAttribute("elements",
                        new RussianEnglishTranslator().getRussianList(elementsFactory.getElements()));

                return EMAIL_VIEW_PAGE;
            } else {
                // TODO log redirection
                try {
                    response.sendRedirect(CONTACT_LIST_URL);
                } catch (IOException e) {
                    throw new CustomException("Can't redirect response: ", e);
                }
                return null;
            }
        } catch (CustomException e) {
            throw new CustomException("Can't create email send form: ", e);
        }
    }

    private ArrayList<Contact> getRecipients(HttpServletRequest request) throws CustomException {
        RequestParser requestParser = new RequestParser(request);
        ArrayList<Contact> recipients = new ArrayList<>();
        ContactDAO contactDAO = null;
        try {
            ArrayList<Long> checkedContacts = requestParser.getCheckedIdList("contact-check");
            contactDAO = (ContactDAO) DAOFactory.getDAO(Contact.class);
            for (Long id : checkedContacts) {
                Contact contact = contactDAO.get(id);
                recipients.add(contact);
            }
            return recipients;
        } catch (CustomException e) {
            throw new CustomException("Can't get email recipients: ", e);
        } finally {
            try {
                if (contactDAO != null) {
                    contactDAO.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }

    }
}
