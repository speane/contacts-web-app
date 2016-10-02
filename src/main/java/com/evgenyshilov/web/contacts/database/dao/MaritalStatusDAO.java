package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.MaritalStatus;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.LogHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 21.09.2016.
 */
public class MaritalStatusDAO extends BaseDAO {

    public MaritalStatusDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<MaritalStatus> getAll() throws CustomException {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String query = "SELECT id, name FROM marital_status";
            ArrayList<MaritalStatus> maritalStatuses = new ArrayList<>();
            ResultSet maritalStatusResultSet = statement.executeQuery(query);
            while (maritalStatusResultSet.next()) {
                MaritalStatus maritalStatus = new MaritalStatus();
                maritalStatus.setId(maritalStatusResultSet.getInt("id"));
                maritalStatus.setName(maritalStatusResultSet.getString("name"));
                maritalStatuses.add(maritalStatus);
            }
            return maritalStatuses;
        } catch (SQLException e) {
            throw new CustomException("Can't get all marital statuses from database: ", e);
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
