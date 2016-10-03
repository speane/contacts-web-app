package com.evgenyshilov.web.contacts.commands.forms;

import com.evgenyshilov.web.contacts.commands.Command;
import com.evgenyshilov.web.contacts.database.dao.ContactDAO;
import com.evgenyshilov.web.contacts.database.dao.DAOFactory;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.LogHelper;
import com.evgenyshilov.web.contacts.help.email.EmailTemplateElementsFactory;
import com.evgenyshilov.web.contacts.help.email.EmailTemplateFactory;
import com.evgenyshilov.web.contacts.help.utils.RequestParser;
import com.evgenyshilov.web.contacts.help.utils.RussianEnglishTranslator;
import com.evgenyshilov.web.contacts.resources.ApplicationConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 25.09.2016.
 */
public class EmailFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CustomException {
        String EMAIL_VIEW_PAGE = "/sendemail.jsp";
        ArrayList<Contact> recipients = null;
        try {
            recipients = getRecipients(request);

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
                throw new CustomException("No recipients found");
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

            LogHelper.info(String.format("Send email request to contacts with ids: %s", checkedContacts.toString()));

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
                LogHelper.error("Can't close contactDAO: ", e);
            }
        }

    }
}
