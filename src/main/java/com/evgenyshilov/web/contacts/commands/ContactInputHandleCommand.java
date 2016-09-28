package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.fieldhanlders.FieldHandler;
import com.evgenyshilov.web.contacts.fieldhanlders.factory.FieldHandlerFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class ContactInputHandleCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CustomException {
        HashMap<Integer, FileItem> attachmentFiles = new HashMap<>();

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> formItems = null;
        try {
            formItems = upload.parseRequest(request);
            if (formItems != null) {
                Contact contact = (Contact) request.getAttribute("contact");
                FieldHandlerFactory fieldHandlerFactory = new FieldHandlerFactory();
                for (FileItem item : formItems) {
                    if (item.isFormField()) {
                        processFormFieldItem(contact, fieldHandlerFactory, item);
                    } else {
                        processFormFileItem(request, attachmentFiles, item);
                    }
                }
                request.setAttribute("attachment-items", attachmentFiles);
                request.setAttribute("contact", contact);
            }
            return null;
        } catch (FileUploadException | CustomException e) {
            throw new CustomException("Can't handle contact form input fields: ", e);
        }

    }

    private void processFormFileItem(HttpServletRequest request,
                                     HashMap<Integer, FileItem> attachmentFiles, FileItem item) throws CustomException {
        String inputFieldName = item.getFieldName();
        if (inputFieldName.startsWith("attachment")) {
            int createdAttachmentIndex = 0;
            try {
                createdAttachmentIndex = getAttachmentIndex(inputFieldName);
            } catch (CustomException e) {
                throw new CustomException("Can't process form attachment: ", e);
            }
            attachmentFiles.put(createdAttachmentIndex, item);
        } else if (inputFieldName.startsWith("upload-photo")) {
            request.setAttribute("photo-item", item);
        }
    }

    private int getAttachmentIndex(String fieldName) throws CustomException {
        int INDEX_STRING_START_POSITION = 11;
        String indexString = fieldName.substring(INDEX_STRING_START_POSITION);
        try {
            return Integer.parseInt(indexString);
        } catch (NumberFormatException e) {
            throw new CustomException("Can't get attachment index: ", e);
        }
    }

    private void processFormFieldItem(Contact contact, FieldHandlerFactory factory, FileItem item)
            throws CustomException {
        FieldHandler handler = factory.getFieldHandler(item.getFieldName());
        if (handler != null) {
            String fieldValue = null;
            try {
                fieldValue = item.getString("UTF-8");
                handler.handleField(contact, fieldValue);
            } catch (UnsupportedEncodingException e) {
                throw new CustomException("Can't process form field item: ", e);
            }
        }
    }
}
