package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.Attachment;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 18.09.2016.
 */
public class AttachmentDAO extends GenericDAO {

    public AttachmentDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList getAll() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return null;
    }

    @Override
    public Object get(Object key) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return null;
    }

    @Override
    public void update(Object key, Object value) {

    }

    @Override
    public void delete(Object key) {

    }

    @Override
    public void insert(Object value) {

    }

    public ArrayList<Attachment> getAllByContactId(int id) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT id, filename, upload_date, commentary " +
                "FROM attachment " +
                "WHERE contact_id = " + id + ";";
        ResultSet attachmentSet = statement.executeQuery(query);

        ArrayList<Attachment> attachments = new ArrayList<>();
        while (attachmentSet.next()) {
            Attachment attachment = new Attachment();
            attachment.setId(attachmentSet.getInt("id"));
            attachment.setFilename(attachmentSet.getString("filename"));
            attachment.setCommentary(attachmentSet.getString("commentary"));
            attachment.setUploadDate(attachmentSet.getDate("upload_date"));
            attachments.add(attachment);
        }
        statement.close();
        return attachments;
    }
}
