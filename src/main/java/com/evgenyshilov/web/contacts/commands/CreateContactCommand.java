package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.database.dao.AttachmentDAO;
import com.evgenyshilov.web.contacts.database.dao.ContactDAO;
import com.evgenyshilov.web.contacts.database.dao.DAOFactory;
import com.evgenyshilov.web.contacts.database.dao.PhoneDAO;
import com.evgenyshilov.web.contacts.database.model.Attachment;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.database.model.Phone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class CreateContactCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Contact contact = new Contact();
        request.setAttribute("contact", contact);
        ContactInputHandleCommand inputHandleCommand = new ContactInputHandleCommand();
        inputHandleCommand.execute(request, response);
        contact = (Contact) request.getAttribute("contact");
        createNewContact(contact);

        response.sendRedirect("/app/contact-list");

        return null;
    }

    private void createNewContact(Contact contact) throws InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException, IllegalAccessException {
        ContactDAO contactDAO = (ContactDAO) DAOFactory.getDAO(Contact.class);
        contactDAO.insert(contact);
        int contactId = contactDAO.getLastInsertedId();

        PhoneDAO phoneDAO = (PhoneDAO) DAOFactory.getDAO(Phone.class);
        for (Phone phone : contact.getPhones()) {
            phone.setContactId(contactId);
            phoneDAO.insert(phone);
        }
        phoneDAO.close();

        AttachmentDAO attachmentDAO = (AttachmentDAO) DAOFactory.getDAO(Attachment.class);
        for (Attachment attachment : contact.getAttachments()) {
            attachment.setContactId(contactId);
            attachmentDAO.insert(attachment);
        }
    }
}
