package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.Address;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 13.09.2016.
 */
public class AddressDAO extends GenericDAO<Integer, Address> {

    public AddressDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<Address> getAll() throws SQLException {
        return null;
    }

    @Override
    public Address get(Integer key) throws SQLException {
        String query = "SELECT id, country, city, street, house, flat, postcode FROM address WHERE id = " + key + ";";
        System.out.println(query);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            Address address = new Address();
            address.setId(resultSet.getInt("id"));
            address.setCountry(resultSet.getString("country"));
            address.setCity(resultSet.getString("city"));
            address.setStreet(resultSet.getString("street"));
            address.setFlat(resultSet.getString("flat"));
            address.setHouse(resultSet.getString("house"));
            address.setPostcode(resultSet.getString("postcode"));
            return address;
        } else {
            return null;
        }
    }

    @Override
    public void update(Integer key, Address value) {

    }

    @Override
    public void delete(Integer key) {

    }

    @Override
    public void insert(Address value) {

    }
}
