package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.Attachment;
import com.evgenyshilov.web.contacts.exceptions.CustomException;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 18.09.2016.
 */
public class AttachmentDAO extends GenericDAO<Long, Attachment> {

    public AttachmentDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<Attachment> getAll() {
        return null;
    }

    @Override
    public Attachment get(Long key) {
        return null;
    }

    @Override
    public void update(Long key, Attachment attachment) throws CustomException {
        String query = "UPDATE attachment SET " +
                "filename=?, commentary=? WHERE id=?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, attachment.getFilename());
            statement.setString(2, attachment.getCommentary());
            statement.setLong(3, key);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("Can't update attachment in database: ", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    public int getLastInsertId() throws CustomException {
        String query = "SELECT last_insert_id() as last_id FROM attachment";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet.next() ? resultSet.getInt("last_id") : -1;
        } catch (SQLException e) {
            throw new CustomException("Can't get last inserted attachment id: ", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    @Override
    public void delete(Long key) throws CustomException {
        String query = "DELETE FROM attachment WHERE id = " + key;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new CustomException("Can't delete attachment: ", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    @Override
    public void insert(Attachment attachment) throws CustomException {
        String query = "INSERT INTO attachment (`filename`, `commentary`, `upload_date`, `contact_id`) " +
                "VALUES (?, ?, ?, ?);";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);

            statement.setString(1, attachment.getFilename());
            statement.setString(2, attachment.getCommentary());
            statement.setDate(3, attachment.getUploadDate());
            statement.setLong(4, attachment.getContactId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("Can't insert attachment: ", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    public ArrayList<Attachment> getAllByContactId(long id) throws CustomException {
        String query = "SELECT id, filename, upload_date, commentary " +
                "FROM attachment " +
                "WHERE contact_id = " + id + ";";

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet attachmentSet = statement.executeQuery(query);
            ArrayList<Attachment> attachments = new ArrayList<>();
            while (attachmentSet.next()) {
                Attachment attachment = new Attachment();
                attachment.setId(attachmentSet.getLong("id"));
                attachment.setFilename(attachmentSet.getString("filename"));
                attachment.setCommentary(attachmentSet.getString("commentary"));
                attachment.setUploadDate(attachmentSet.getDate("upload_date"));
                attachments.add(attachment);
            }
            return attachments;

        } catch (SQLException e) {
            throw new CustomException("Can't get all attachments by contact id: ", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    public void deleteAllContactAttachments(long id) throws CustomException {
        String query = "DELETE FROM attachment WHERE contact_id = " + id;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new CustomException("Can't delete all attachments: ", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }
}
