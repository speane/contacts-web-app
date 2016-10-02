package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.Attachment;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.LogHelper;
import com.evgenyshilov.web.contacts.help.utils.StatementUtils;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 18.09.2016.
 */
public class AttachmentDAO extends BaseDAO<Long, Attachment> {

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
            StatementUtils.setStatementStringValue(statement, 2, attachment.getCommentary());
            StatementUtils.setStatementLongValue(statement, 3, key);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("Can't update attachment in database: ", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                LogHelper.error("Can't close statement: ", e);
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
                LogHelper.error("Can't close statement: ", e);
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
                LogHelper.error("Can't close statement: ", e);
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

            StatementUtils.setStatementStringValue(statement, 1, attachment.getFilename());
            StatementUtils.setStatementStringValue(statement, 2, attachment.getCommentary());
            StatementUtils.setStatementDateValue(statement, 3, attachment.getUploadDate());
            StatementUtils.setStatementLongValue(statement, 4, attachment.getContactId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("Can't insert attachment: ", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                LogHelper.error("Can't close statement: ", e);
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
                LogHelper.error("Can't close statement: ", e);
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
                LogHelper.error("Can't close statement: ", e);
            }
        }
    }
}
