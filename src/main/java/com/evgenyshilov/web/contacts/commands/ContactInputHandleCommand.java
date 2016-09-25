package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.fieldhanlders.FieldHandler;
import com.evgenyshilov.web.contacts.fieldhanlders.factory.FieldHandlerFactory;
import com.evgenyshilov.web.contacts.resources.ApplicationResources;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class ContactInputHandleCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        HashMap<Integer, FileItem> attachmentFiles = new HashMap<>();
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        File uploadDir = new File(ApplicationResources.FILE_UPLOAD_PATH);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        try {
            List<FileItem> formItems = upload.parseRequest(request);
            if (formItems != null) {
                Contact contact = (Contact) request.getAttribute("contact");
                FieldHandlerFactory fieldHandlerFactory = new FieldHandlerFactory();
                for (FileItem item : formItems) {
                    if (item.isFormField()) {
                        System.out.println(item.getFieldName() + " " + item.getString("UTF-8"));
                        FieldHandler handler = fieldHandlerFactory.getFieldHandler(item.getFieldName());
                        if (handler != null) {
                            handler.setField(contact, item.getString("UTF-8"));
                        }
                    } else {
                        String inputFieldName = item.getFieldName();
                        if (inputFieldName.startsWith("attachment")) {
                            int createdAttachmentIndex = Integer.parseInt(inputFieldName.substring(11, inputFieldName.length()));
                            System.out.println("INDEX: " + createdAttachmentIndex);
                            attachmentFiles.put(createdAttachmentIndex, item);
                            request.setAttribute("attachment-items", attachmentFiles);
                        } else if (inputFieldName.startsWith("upload-photo")) {
                            request.setAttribute("photo-item", item);
                            System.out.println("photo_ITEMEEEEE");
                        }
                    }
                }
                request.setAttribute("contact", contact);
            }
        } finally {
            System.out.println("sdfsdf");
        }

        return null;
    }
}
