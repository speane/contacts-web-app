package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.Address;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 13.09.2016.
 */
public class AddressDAO extends GenericDAO<Integer, Address> {
    @Override
    public ArrayList<Address> getAll() throws SQLException {
        return null;
    }

    @Override
    public Address get(Integer key) {
        return null;
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
