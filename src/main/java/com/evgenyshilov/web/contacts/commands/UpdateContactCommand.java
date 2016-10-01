package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.database.model.Attachment;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.DBHelper;
import com.evgenyshilov.web.contacts.help.FileNamingUtils;
import com.evgenyshilov.web.contacts.resources.ApplicationConfig;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Evgeny Shilov on 19.09.2016.
 */
public class UpdateContactCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CustomException {
        DBHelper dbHelper = new DBHelper();
        String REDIRECT_URL = "/app/contact-list";
        try {
            int contactId = getContactIdFromRequest(request);
            Contact contact = dbHelper.getContactFromDAO(contactId);
            contact = processInputFields(contact, request, response);
            writeAttachmentsFromRequest(request, contact);
            String imageFileName = writeContactPhotoFileFromRequest(request);
            if (imageFileName != null) {
                contact.setImageFileName(imageFileName);
            }
            dbHelper.updateContact(contactId, contact);
            dbHelper.insertContactPhones(contact.getPhones(), contactId);

            response.sendRedirect(REDIRECT_URL);
        } catch (CustomException | IOException e) {
            throw new CustomException("Can't update contact: ", e);
        }
        return null;
    }

    private void writeAttachmentsFromRequest(HttpServletRequest request, Contact contact) throws CustomException {
        DBHelper helper = new DBHelper();
        try {
            HashMap<Integer, FileItem> attachmentItems =
                    (HashMap<Integer, FileItem>) request.getAttribute("attachment-items");
            for (Attachment attachment : contact.getAttachments()) {
                attachment.setContactId(contact.getId());
                int attachmentId = helper.insertAttachment(attachment);
                FileItem attachmentItem = attachmentItems.get(attachment.getId());
                writeAttachment(attachmentItem, attachmentId);
            }
        } catch (CustomException e) {
            throw new CustomException("Can't write contact attachments: ", e);
        }
    }

    private Contact processInputFields(Contact contact, HttpServletRequest request,
                                       HttpServletResponse response) throws CustomException {
        request.setAttribute("contact", contact);
        ContactInputHandleCommand inputHandleCommand = new ContactInputHandleCommand();
        try {
            inputHandleCommand.execute(request, response);
            return (Contact) request.getAttribute("contact");
        } catch (CustomException e) {
            throw new CustomException("Can't process input fields: ", e);
        }
    }

    private void writeAttachment(FileItem attachmentItem, int attachmentId) throws CustomException {
        String attachmentFilePath = ApplicationConfig.getProperty("ROOT_PATH") + File.separator + "attachments" +
                File.separator + "attachment_" + attachmentId;
        File attachmentFile = new File(attachmentFilePath);
        try {
            attachmentItem.write(attachmentFile);
        } catch (Exception e) {
            throw new CustomException("Can't write attachment to file: ", e);
        }
    }

    private String writeContactPhotoFileFromRequest(HttpServletRequest request) throws CustomException {
        FileItem photoItem = (FileItem) request.getAttribute("photo-item");
        if (photoItem != null) {
            String photoFilePath = ApplicationConfig.getProperty("ROOT_PATH") + File.separator + "images" +
                    File.separator + "image";
            photoFilePath = FileNamingUtils.getUniqueFilePath(photoFilePath);
            File photoFile = new File(photoFilePath);
            try {
                photoItem.write(photoFile);
                return photoFile.getName();
            } catch (Exception e) {
                throw new CustomException("Can't write photo file from request: ", e);
            }
        }
        return null;
    }

    private int getContactIdFromRequest(HttpServletRequest request) throws CustomException {
        String idString = request.getParameter("id");
        try {
            return Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new CustomException("Can't get contact id from request: ", e);
        }
    }
}
