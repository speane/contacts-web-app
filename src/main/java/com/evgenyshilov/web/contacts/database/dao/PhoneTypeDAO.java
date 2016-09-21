package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.PhoneType;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 21.09.2016.
 */
public class PhoneTypeDAO extends GenericDAO {

    public PhoneTypeDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList getAll() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Statement statement = connection.createStatement();
        String query = "SELECT id, name FROM phone_type";
        ArrayList<PhoneType> phoneTypes = new ArrayList<>();
        ResultSet phoneTypeResultSet = statement.executeQuery(query);
        while (phoneTypeResultSet.next()) {
            PhoneType phoneType = new PhoneType();
            phoneType.setId(phoneTypeResultSet.getInt("id"));
            phoneType.setName(phoneTypeResultSet.getString("name"));
            phoneTypes.add(phoneType);
        }
        statement.close();
        return phoneTypes;
    }

    @Override
    public Object get(Object key) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return null;
    }

    @Override
    public void update(Object key, Object value) {

    }

    @Override
    public void delete(Object key) throws SQLException {

    }

    @Override
    public void insert(Object value) {

    }
}
