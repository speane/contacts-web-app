package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.Contact;

import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 12.09.2016.
 */
public class ContactDAO extends GenericDAO<Integer, Contact> {

    @Override
    public ArrayList<Contact> getAll() {
        return null;
    }

    @Override
    public Contact get(Integer key) {
        return null;
    }

    @Override
    public void update(Integer key, Contact value) {

    }

    @Override
    public void delete(Integer key) {
        System.out.println(key);
    }

    @Override
    public void insert(Contact value) {

    }
}
