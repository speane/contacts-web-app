package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.MaritalStatus;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 21.09.2016.
 */
public class MaritalStatusDAO extends GenericDAO {

    @Override
    public ArrayList getAll() throws SQLException, InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        Statement statement = connection.createStatement();
        String query = "SELECT id, name FROM marital_status";
        ArrayList<MaritalStatus> maritalStatuses = new ArrayList<>();
        ResultSet maritalStatusResultSet = statement.executeQuery(query);
        while (maritalStatusResultSet.next()) {
            MaritalStatus maritalStatus = new MaritalStatus();
            maritalStatus.setId(maritalStatusResultSet.getInt("id"));
            maritalStatus.setName(maritalStatusResultSet.getString("name"));
            maritalStatuses.add(maritalStatus);
        }
        statement.close();
        return maritalStatuses;
    }

    @Override
    public Object get(Object key) throws SQLException, InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
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
