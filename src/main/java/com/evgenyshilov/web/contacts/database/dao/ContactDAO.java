package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.Address;
import com.evgenyshilov.web.contacts.database.model.Contact;

import java.lang.reflect.InvocationTargetException;
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
    public ArrayList<Contact> getAll() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ArrayList<Contact> contacts = new ArrayList<>();
        Statement statement = connection.createStatement();
        String GET_ALL_CONTACTS_QUERY = "SELECT id, first_name, " +
                "last_name, job, birthday, address_id  FROM contact;";
        ResultSet contactSet = statement.executeQuery(GET_ALL_CONTACTS_QUERY);
        while (contactSet.next()) {
            Contact contact = new Contact();

            contact.setId(contactSet.getInt(ID_FIELD_NAME));
            contact.setFirstName(contactSet.getString(FIRST_NAME_FIELD_NAME));
            contact.setLastName(contactSet.getString(LAST_NAME_FIELD_NAME));
            contact.setJob(contactSet.getString(JOB_FIELD_NAME));
            contact.setBirthday(contactSet.getDate(BIRTHDAY_FIELD_NAME));

            AddressDAO addressDAO = (AddressDAO) DAOFactory.getDAO(Address.class);
            Address contact_address = addressDAO.get(contactSet.getInt("address_id"));
            addressDAO.close();

            contact.setAddress((contact_address != null) ? contact_address : new Address());

            contacts.add(contact);
        }
        contactSet.close();
        statement.close();

        return contacts;
    }

    @Override
    public Contact get(Integer key) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Statement statement = connection.createStatement();
        String query = "SELECT id, first_name, last_name, patronymic, birthday, sex, nationality, " +
                "marital_status, email, job, address_id FROM contact WHERE id = " + key + ";";
        ResultSet contactResult = statement.executeQuery(query);
        Contact contact = new Contact();
        if (contactResult.next()) {
            contact.setId(contactResult.getInt("id"));
            contact.setFirstName(contactResult.getString("first_name"));
            contact.setLastName(contactResult.getString("last_name"));
            contact.setPatronymic(contactResult.getString("patronymic"));
            contact.setBirthday(contactResult.getDate("birthday"));
            contact.setSex(contactResult.getString("sex"));
            contact.setEmail(contactResult.getString("email"));
            contact.setNationality(contactResult.getString("nationality"));
            contact.setMaritalStatus(contactResult.getInt("marital_status"));
            contact.setJob(contactResult.getString("job"));

            AddressDAO addressDAO = (AddressDAO) DAOFactory.getDAO(Address.class);
            Address address = addressDAO.get(contactResult.getInt("address_id"));
            addressDAO.close();

            contact.setAddress(address);

            return contact;
        } else {
            return null;
        }
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
