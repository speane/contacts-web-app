package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.database.model.Attachment;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.DBHelper;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class CreateContactCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CustomException {
        String REDIRECT_URL = "/app/contact-list";
        Contact contact = new Contact();
        request.setAttribute("contact", contact);
        ContactInputHandleCommand inputHandleCommand = new ContactInputHandleCommand();
        try {
            inputHandleCommand.execute(request, response);
            contact = (Contact) request.getAttribute("contact");
            HashMap<Integer, FileItem> attachmentItems =
                    (HashMap<Integer, FileItem>) request.getAttribute("attachment-items");
            FileItem photoItem = (FileItem)request.getAttribute("photo-item");
            createNewContact(contact, attachmentItems, photoItem);
            response.sendRedirect(REDIRECT_URL);
        } catch (CustomException | IOException e) {
            throw new CustomException("Can't execute create contact command: ", e);
        }

        return null;
    }

    private void createNewContact(Contact contact, HashMap<Integer, FileItem> attachmentItems,
                                  FileItem photoFileItem) throws CustomException {
        DBHelper dbHelper = new DBHelper();
        long contactId = 0;
        try {
            contactId = dbHelper.insertContact(contact);
            dbHelper.insertContactPhones(contact.getPhones(), contactId);
            for (Attachment attachment : contact.getAttachments()) {
                attachment.setContactId(contactId);
                int attachmentId = dbHelper.insertContactAttachment(attachment);
            }
        } catch (CustomException e) {
            throw new CustomException("Can't create new contact: ", e);
        }
    }
}
