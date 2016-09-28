package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.database.dao.AttachmentDAO;
import com.evgenyshilov.web.contacts.database.dao.ContactDAO;
import com.evgenyshilov.web.contacts.database.dao.DAOFactory;
import com.evgenyshilov.web.contacts.database.dao.PhoneDAO;
import com.evgenyshilov.web.contacts.database.model.Attachment;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.database.model.Phone;
import com.evgenyshilov.web.contacts.resources.ApplicationConfig;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;

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

        FileItem photoItem = (FileItem) request.getAttribute("photo-item");
        if (photoItem != null) {
            File photoFile = new File(ApplicationConfig.getProperty("ROOT_PATH") + File.separator + "images" + File.separator + "image_" + contactId);
            photoItem.write(photoFile);
        }

        HashMap<Integer, FileItem> attachmentItems = (HashMap<Integer, FileItem>) request.getAttribute("attachment-items");

        contact = (Contact) request.getAttribute("contact");
        contact.setImageFileName("image_" + contact.getId());
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
            int id = attachmentDAO.getLastInsertId();
            FileItem attachmentItem = attachmentItems.get(attachment.getId());
            attachmentItem.write(new File(ApplicationConfig.getProperty("ROOT_PATH") + File.separator + "attachments" + File.separator + "attachment_" + id));
        }

        response.sendRedirect("/app/contact-list");
        return null;
    }
}
