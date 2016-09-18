package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.Phone;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 18.09.2016.
 */
public class PhoneDAO extends GenericDAO {
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
        String query = "SELECT country_code, operator_code, number " +
                "FROM phone " +
                "WHERE contact_id = " + id + ";";
        ResultSet phoneResultSet = statement.executeQuery(query);
        ArrayList<Phone> phones = new ArrayList<>();
        while (phoneResultSet.next()) {
            Phone phone = new Phone();
            phone.setCountryCode(phoneResultSet.getInt("country_code"));
            phone.setOperatorCode(phoneResultSet.getInt("operator_code"));
            phone.setNumber(phoneResultSet.getInt("number"));
            phones.add(phone);
        }
        return phones;
    }
}
