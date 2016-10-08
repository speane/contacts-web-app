package com.evgenyshilov.web.contacts.database.model.builders;

import com.evgenyshilov.web.contacts.database.model.Attachment;
import com.evgenyshilov.web.contacts.exceptions.CustomException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Evgeny Shilov on 09.10.2016.
 */
public class AttachmentBuilder {
    public static Attachment createAttachmentFromResultSet(ResultSet resultSet) throws CustomException {
        Attachment attachment = new Attachment();
        try {
            attachment.setId(resultSet.getLong("id"));
            attachment.setFilename(resultSet.getString("filename"));
            attachment.setUploadDate(resultSet.getDate("upload_date"));
            attachment.setCommentary(resultSet.getString("commentary"));
            attachment.setContactId(resultSet.getLong("contact_id"));

            return attachment;
        } catch (SQLException e) {
            throw new CustomException("Cannot build attachment from result set: ", e);
        }
    }
}
