package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.Attachment;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 18.09.2016.
 */
public class AttachmentDAO extends GenericDAO<Integer, Attachment> {

    public AttachmentDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<Attachment> getAll() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return null;
    }

    @Override
    public Attachment get(Integer key) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return null;
    }

    @Override
    public void update(Integer key, Attachment attachment) throws SQLException {
        String query = "UPDATE attachment SET " +
                "filename=?, commentary=? WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, attachment.getFilename());
        statement.setString(2, attachment.getCommentary());
        statement.setInt(3, key);
        statement.executeUpdate();
        statement.close();
    }

    public int getLastInsertId() throws SQLException {
        String query = "SELECT last_insert_id() as last_id FROM attachment";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return resultSet.next() ? resultSet.getInt("last_id") : -1;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        String query = "DELETE FROM attachment WHERE id = " + key;
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        statement.close();
    }

    @Override
    public void insert(Attachment attachment) throws SQLException {
        String query = "INSERT INTO attachment (`filename`, `commentary`, `upload_date`, `contact_id`) " +
                "VALUES (?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, attachment.getFilename());
        statement.setString(2, attachment.getCommentary());
        statement.setDate(3, attachment.getUploadDate());
        statement.setInt(4, attachment.getContactId());
        statement.executeUpdate();
        statement.close();
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

    public void deleteAllContactAttachments(int id) throws SQLException {
        String query = "DELETE FROM attachment WHERE contact_id = '" + id + "'";
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        statement.close();
    }
}
