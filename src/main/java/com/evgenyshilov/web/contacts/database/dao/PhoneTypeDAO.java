package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.PhoneType;
import com.evgenyshilov.web.contacts.exceptions.CustomException;

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
    public ArrayList<PhoneType> getAll() throws CustomException {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String query = "SELECT id, name FROM phone_type";
            ArrayList<PhoneType> phoneTypes = new ArrayList<>();
            ResultSet phoneTypeResultSet = statement.executeQuery(query);
            while (phoneTypeResultSet.next()) {
                PhoneType phoneType = new PhoneType();
                phoneType.setId(phoneTypeResultSet.getInt("id"));
                phoneType.setName(phoneTypeResultSet.getString("name"));
                phoneTypes.add(phoneType);
            }
            return phoneTypes;
        } catch (SQLException e) {
            throw new CustomException("Can't get all phone types from database: ", e);
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
    public Object get(Object key) {
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
}
