package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.Contact;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 12.09.2016.
 */
public class ContactDAO extends GenericDAO<Integer, Contact> {

    private static final String ID_FIELD_NAME = "id";
    private static final String FIRST_NAME_FIELD_NAME = "first_name";
    private static final String LAST_NAME_FIELD_NAME = "last_name";
    private static final String JOB_FIELD_NAME = "job";
    private static final String BIRTHDAY_FIELD_NAME = "birthday";

    public ContactDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<Contact> getAll() throws SQLException {
        ArrayList<Contact> contacts = new ArrayList<>();
        Statement statement = connection.createStatement();
        String GET_ALL_CONTACTS_QUERY = "SELECT id, first_name, " +
                "last_name, job, birthday  FROM contact;";
        ResultSet contactSet = statement.executeQuery(GET_ALL_CONTACTS_QUERY);
        while (contactSet.next()) {
            Contact newContact = new Contact();

            newContact.setId(contactSet.getInt(ID_FIELD_NAME));
            newContact.setFirstName(contactSet.getString(FIRST_NAME_FIELD_NAME));
            newContact.setLastName(contactSet.getString(LAST_NAME_FIELD_NAME));
            newContact.setJob(contactSet.getString(JOB_FIELD_NAME));
            newContact.setBirthday(contactSet.getDate(BIRTHDAY_FIELD_NAME));

            contacts.add(newContact);
        }
        contactSet.close();
        statement.close();

        return contacts;
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
    }

    @Override
    public void insert(Contact value) {

    }
}
