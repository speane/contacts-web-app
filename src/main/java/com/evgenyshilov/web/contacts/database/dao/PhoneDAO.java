package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.Phone;
import com.evgenyshilov.web.contacts.exceptions.CustomException;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 18.09.2016.
 */
public class PhoneDAO extends GenericDAO<Integer, Phone> {

    public PhoneDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<Phone> getAll() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return null;
    }

    @Override
    public Phone get(Integer key) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return null;
    }

    @Override
    public void update(Integer key, Phone phone) throws CustomException {
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
    public void delete(Integer key) throws CustomException {
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

    public void deleteAllContactPhones(int id) throws CustomException {
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
            statement.setInt(1, phone.getCountryCode());
            statement.setInt(2, phone.getOperatorCode());
            statement.setInt(3, phone.getNumber());
            statement.setString(4, phone.getCommentary());
            statement.setInt(5, phone.getContactId());
            statement.setInt(6, getPhoneTypeId(phone.getType()));
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

    private int getPhoneTypeId(String typeName) throws SQLException {
        String query = "SELECT id FROM phone_type WHERE name = '" + typeName + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return resultSet.next() ? resultSet.getInt("id") : -1;
    }

    public ArrayList<Phone> getAllByContactId(int id) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT phone.id, country_code, operator_code, number, phone_type.name AS type, commentary " +
                "FROM phone " +
                "JOIN phone_type ON phone_type.id = phone.phone_type_id " +
                "WHERE contact_id = " + id + ";";
        ResultSet phoneResultSet = statement.executeQuery(query);
        ArrayList<Phone> phones = new ArrayList<>();
        while (phoneResultSet.next()) {
            Phone phone = new Phone();
            phone.setId(phoneResultSet.getInt("id"));
            phone.setCountryCode(phoneResultSet.getInt("country_code"));
            phone.setOperatorCode(phoneResultSet.getInt("operator_code"));
            phone.setNumber(phoneResultSet.getInt("number"));
            phone.setType(phoneResultSet.getString("type"));
            phone.setCommentary(phoneResultSet.getString("commentary"));
            phones.add(phone);
        }
        return phones;
    }
}
