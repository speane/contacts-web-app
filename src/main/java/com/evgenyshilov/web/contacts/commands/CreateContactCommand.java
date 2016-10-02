package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.database.model.Attachment;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.database.DBHelper;
import com.evgenyshilov.web.contacts.help.files.FileItemWriter;
import com.evgenyshilov.web.contacts.help.files.FileNamingUtils;
import com.evgenyshilov.web.contacts.resources.ApplicationConfig;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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
            HashMap<Long, FileItem> attachmentItems =
                    (HashMap<Long, FileItem>) request.getAttribute("attachment-items");
            FileItem photoItem = (FileItem) request.getAttribute("photo-item");
            createNewContact(contact, attachmentItems, photoItem);
            request.getSession().setAttribute("action-message", String.format("Контакт '%s %s' был создан",
                    contact.getLastName(), contact.getFirstName()));
            response.sendRedirect(REDIRECT_URL);
        } catch (CustomException | IOException e) {
            throw new CustomException("Can't execute create contact command: ", e);
        }

        return null;
    }

    private Long createNewContact(Contact contact, HashMap<Long, FileItem> attachmentItems,
                                  FileItem photoFileItem) throws CustomException {
        DBHelper dbHelper = new DBHelper();
        FileItemWriter fileItemWriter = new FileItemWriter();
        String attachmentPath = ApplicationConfig.getProperty("ROOT_PATH") + File.separator +
                "attachments" + File.separator + "attachment_";
        String photoPath = ApplicationConfig.getProperty("ROOT_PATH") + File.separator +
                "images" + File.separator + "image";
        long contactId = 0;
        try {
            contact.setImageFileName(savePhotoFile(photoFileItem, photoPath));
            contactId = dbHelper.insertContact(contact);
            dbHelper.insertContactPhones(contact.getPhones(), contactId);
            for (Attachment attachment : contact.getAttachments()) {
                FileItem attachmentItem = attachmentItems.get(attachment.getId());
                attachment.setContactId(contactId);
                int attachmentId = dbHelper.insertContactAttachment(attachment);
                fileItemWriter.writeFileItem(attachmentItem, attachmentPath + attachmentId);
            }
            return contactId;
        } catch (Exception e) {
            throw new CustomException("Can't create new contact: ", e);
        }
    }
    private String savePhotoFile(FileItem photoFileItem, String path) throws CustomException {
        if (photoFileItem != null) {
            String uniquePhotoPath = FileNamingUtils.getUniqueFilePath(path);
            File photoFile = new File(uniquePhotoPath);
            try {
                photoFileItem.write(photoFile);
                return photoFile.getName();
            } catch (Exception e) {
                throw new CustomException("Can't save photo file: ", e);
            }
        } else {
            return null;
        }
    }
}
