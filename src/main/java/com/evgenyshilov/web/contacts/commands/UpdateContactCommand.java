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

/**
 * Created by Evgeny Shilov on 19.09.2016.
 */
public class UpdateContactCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int contactId = Integer.parseInt(request.getParameter("id"));

        ContactDAO contactDAO = (ContactDAO) DAOFactory.getDAO(Contact.class);
        Contact contact = contactDAO.get(contactId);

        request.setAttribute("contact", contact);

        ContactInputHandleCommand inputHandleCommand = new ContactInputHandleCommand();
        inputHandleCommand.execute(request, response);

        contact = (Contact) request.getAttribute("contact");

        contactDAO.update(contactId, contact);

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

        response.sendRedirect("/app/contact-list");
        return null;
    }
}
