package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.Phone;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 18.09.2016.
 */
public class PhoneDAO extends GenericDAO {

    public PhoneDAO(Connection connection) {
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
