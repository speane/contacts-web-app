package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.Phone;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.utils.StatementUtils;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 18.09.2016.
 */
public class PhoneDAO extends BaseDAO<Long, Phone> {

    public PhoneDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<Phone> getAll() {
        return null;
    }

    @Override
    public Phone get(Long key) {
        return null;
    }

    @Override
    public void update(Long key, Phone phone) throws CustomException {
        String query = "UPDATE phone SET country_code=?, operator_code=?, number=?, " +
                "commentary=?, contact_id=?, phone_type_id=? WHERE id=" + key;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = prepareStatement(query, phone);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("Can't update phone in database: ", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    @Override
    public void delete(Long key) throws CustomException {
        String query = "DELETE FROM phone WHERE id = " + key;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new CustomException("Can't delete phone from database: ", e);
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

    public void deleteAllContactPhones(long id) throws CustomException {
        String query = "DELETE FROM phone WHERE contact_id = '" + id + "'";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new CustomException("Can't delete all contact phones: ", e);
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

    private PreparedStatement prepareStatement(String query, Phone phone) throws CustomException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            StatementUtils.setStatementStringValue(statement, 1, phone.getCountryCode());
            StatementUtils.setStatementStringValue(statement, 2, phone.getOperatorCode());
            StatementUtils.setStatementStringValue(statement, 3, phone.getNumber());
            StatementUtils.setStatementStringValue(statement, 4, phone.getCommentary());
            StatementUtils.setStatementLongValue(statement, 5, phone.getContactId());
            StatementUtils.setStatementLongValue(statement, 6, getPhoneTypeId(phone.getType()));
            return statement;
        } catch (SQLException e) {
            throw new CustomException("Can't prepare statement: ", e);
        }
    }

    @Override
    public void insert(Phone phone) throws CustomException {
        String query = "INSERT INTO phone (`country_code`, `operator_code`, `number`, " +
                "`commentary`, `contact_id`, `phone_type_id`) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = null;
        try {
            statement = prepareStatement(query, phone);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("Can't insert phone: ", e);
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

    private Long getPhoneTypeId(String typeName) throws CustomException {
        String query = "SELECT id FROM phone_type WHERE name = '" + typeName + "'";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet.next() ? resultSet.getLong("id") : null;
        } catch (SQLException e) {
            throw new CustomException("Can't get phone type id from database: ", e);
        }
    }

    public ArrayList<Phone> getAllByContactId(long id) throws CustomException {
        String query = "SELECT phone.id, country_code, operator_code, number, phone_type.name AS type, commentary " +
                "FROM phone " +
                "JOIN phone_type ON phone_type.id = phone.phone_type_id " +
                "WHERE contact_id = " + id + ";";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet phoneResultSet = statement.executeQuery(query);
            ArrayList<Phone> phones = new ArrayList<>();
            while (phoneResultSet.next()) {
                Phone phone = new Phone();
                phone.setId(phoneResultSet.getLong("id"));
                phone.setCountryCode(phoneResultSet.getString("country_code"));
                phone.setOperatorCode(phoneResultSet.getString("operator_code"));
                phone.setNumber(phoneResultSet.getString("number"));
                phone.setType(phoneResultSet.getString("type"));
                phone.setCommentary(phoneResultSet.getString("commentary"));
                phones.add(phone);
            }
            return phones;
        } catch (SQLException e) {
            throw new CustomException("Can't get all phones by contact id: ", e);
        }
    }
}
