package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.commands.forms.EditFormCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Evgeny Shilov on 19.09.2016.
 */
public class UpdateContactCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        /*DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        File uploadDir = new File(ApplicationResources.FILE_UPLOAD_PATH);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        try {
            List<FileItem> formItems = upload.parseRequest(request);
            if (formItems != null) {
                ContactFieldSetter fieldSetter = new ContactFieldSetter();
                for (FileItem item : formItems) {
                    if (item.isFormField()) {

                    } else {

                    }
                }
            }
        } finally {
            System.out.println("sdfsdf");
        }*/
        return new EditFormCommand().execute(request, response);
    }
}
