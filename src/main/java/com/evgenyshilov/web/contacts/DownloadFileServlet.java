package com.evgenyshilov.web.contacts;

import com.evgenyshilov.web.contacts.database.model.Attachment;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.LogHelper;
import com.evgenyshilov.web.contacts.help.database.DBHelper;
import com.evgenyshilov.web.contacts.resources.ApplicationConfig;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;

/**
 * Created by Evgeny Shilov on 09.10.2016.
 */
public class DownloadFileServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            long attachmentId = getFileId(request);

            LogHelper.info(String.format("Download attachment with id = %d", attachmentId));

            Attachment attachment = getAttachment(attachmentId);
            String realAttachmentPath = getRealAttachmentPath(request, attachment);
            setResponseContent(response, realAttachmentPath, attachment);
        } catch (CustomException e) {
            LogHelper.error("Cannot handle get file request: ", e);
        }
    }

    private void setResponseContent(HttpServletResponse response, String filePath, Attachment attachment)
            throws CustomException {
        int BUFFER_SIZE = 4096;
        FileInputStream fileInputStream = null;
        OutputStream outputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            outputStream = response.getOutputStream();

            response.setContentType(URLConnection.guessContentTypeFromStream(fileInputStream));
            response.setHeader("Content-disposition", String.format("attachment; filename=%s",
                    attachment.getFilename()));

            byte[] buffer = new byte[BUFFER_SIZE];
            while (fileInputStream.read(buffer) > 0) {
                outputStream.write(buffer);
            }
            outputStream.flush();
        } catch (IOException e) {
            throw new CustomException("Cannot set response content: ", e);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                LogHelper.error("Cannot close file input stream: ", e);
            }
        }
    }

    private String getRealAttachmentPath(HttpServletRequest request, Attachment attachment) {
        String ROOT_PATH = ApplicationConfig.getProperty("ROOT_PATH");
        return ROOT_PATH + File.separator + "attachments/attachment_" + attachment.getId();
    }

    private Attachment getAttachment(long id) throws CustomException {
        DBHelper dbHelper = new DBHelper();
        try {
            return dbHelper.getAttachmentFromDao(id);
        } catch (CustomException e) {
            throw new CustomException("Cannot get specified attachment: ", e);
        }
    }

    private long getFileId(HttpServletRequest request) throws CustomException {
        String idString = request.getParameter("id");
        try {
            if (!StringUtils.isEmpty(idString)) {
                return Long.parseLong(idString);
            } else {
                throw new CustomException("Id not specified in request");
            }
        } catch (CustomException | NumberFormatException e) {
            throw new CustomException("Can't get file id: ", e);
        }
    }
}
