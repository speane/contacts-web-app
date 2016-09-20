package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.Attachment;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.database.model.Phone;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
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
        String GET_ALL_CONTACTS_QUERY = "SELECT contact.id AS id, first_name, last_name, patronymic, birthday, sex, nationality.name AS nationality, " +
                "marital_status.name AS marital_status, email, website, job, state.name AS state, city.name AS city, street, house, flat, zip_code " +
                "FROM contact " +
                "LEFT JOIN nationality ON nationality.id = contact.nationality_id " +
                "LEFT JOIN marital_status ON marital_status.id = contact.marital_status_id " +
                "LEFT JOIN state ON state.id = contact.state_id " +
                "LEFT JOIN city ON city.id = contact.city_id;";
        ResultSet contactSet = statement.executeQuery(GET_ALL_CONTACTS_QUERY);
        while (contactSet.next()) {
            Contact contact = new Contact();

            contact.setId(contactSet.getInt("id"));
            contact.setFirstName(contactSet.getString("first_name"));
            contact.setLastName(contactSet.getString("last_name"));
            contact.setPatronymic(contactSet.getString("patronymic"));
            contact.setBirthday(contactSet.getDate("birthday"));
            contact.setSex(contactSet.getString("sex"));
            contact.setEmail(contactSet.getString("email"));
            contact.setWebsite(contactSet.getString("website"));
            contact.setNationality(contactSet.getString("nationality"));
            contact.setMaritalStatus(contactSet.getString("marital_status"));
            contact.setJob(contactSet.getString("job"));
            contact.setState(contactSet.getString("state"));
            contact.setCity(contactSet.getString("city"));
            contact.setStreet(contactSet.getString("street"));
            contact.setHouse(contactSet.getString("house"));
            contact.setFlat(contactSet.getString("flat"));
            contact.setZipCode(contactSet.getString("zip_code"));

            PhoneDAO phoneDAO = (PhoneDAO) DAOFactory.getDAO(Phone.class);
            contact.setPhones(phoneDAO.getAllByContactId(contact.getId()));
            phoneDAO.close();

            contacts.add(contact);
        }
        contactSet.close();
        statement.close();

        return contacts;
    }

    @Override
    public Contact get(Integer key) throws SQLException, InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        Statement statement = connection.createStatement();
        String query = "SELECT contact.id AS id, first_name, last_name, patronymic, birthday, sex, nationality.name AS nationality, " +
                "marital_status.name AS marital_status, email, website, job, state.name AS state, city.name AS city, street, house, flat, zip_code " +
                "FROM contact " +
                "LEFT JOIN nationality ON nationality.id = contact.nationality_id " +
                "LEFT JOIN marital_status ON marital_status.id = contact.marital_status_id " +
                "LEFT JOIN state ON state.id = contact.state_id " +
                "LEFT JOIN city ON city.id = contact.city_id " +
                "WHERE contact.id = " + key + ";";
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
            contact.setWebsite(contactResult.getString("website"));
            contact.setNationality(contactResult.getString("nationality"));
            contact.setMaritalStatus(contactResult.getString("marital_status"));
            contact.setJob(contactResult.getString("job"));
            contact.setState(contactResult.getString("state"));
            contact.setCity(contactResult.getString("city"));
            contact.setStreet(contactResult.getString("street"));
            contact.setHouse(contactResult.getString("house"));
            contact.setFlat(contactResult.getString("flat"));
            contact.setZipCode(contactResult.getString("zip_code"));

            PhoneDAO phoneDAO = (PhoneDAO) DAOFactory.getDAO(Phone.class);
            contact.setPhones(phoneDAO.getAllByContactId(contact.getId()));
            phoneDAO.close();

            AttachmentDAO attachmentDAO = (AttachmentDAO) DAOFactory.getDAO(Attachment.class);
            contact.setAttachments(attachmentDAO.getAllByContactId(contact.getId()));
            attachmentDAO.close();

            return contact;
        } else {
            return null;
        }
    }

    @Override
    public void update(Integer key, Contact value) {

    }

    @Override
    public void delete(Integer key) throws SQLException {
        String query = "DELETE FROM contact WHERE id = " + key + ";";
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        statement.close();
    }

    @Override
    public void insert(Contact value) {

    }

    public ArrayList<Contact> getAllByParams(String firstName, String lastName, String patronymic,
                                             Date birthday, boolean older, String sex,
                                             String maritalStatus, String nationality,
                                             String state, String city, String house, String flat) throws SQLException {
        String query = "SELECT contact.id AS id, first_name, last_name, patronymic, birthday, sex, nationality.name AS nationality, " +
                "marital_status.name AS marital_status, email, website, job, state.name AS state, city.name AS city, street, house, flat, zip_code " +
                "FROM contact " +
                "LEFT JOIN nationality ON nationality.id = contact.nationality_id " +
                "LEFT JOIN marital_status ON marital_status.id = contact.marital_status_id " +
                "LEFT JOIN state ON state.id = contact.state_id " +
                "LEFT JOIN city ON city.id = contact.city_id " +
                "WHERE TRUE";
        if (!firstName.equals("")) {
            query += " AND first_name LIKE '" + firstName + "'";
        }
        if (!lastName.equals("")) {
            query += " AND last_name LIKE '" + lastName + "'";
        }
        if (!patronymic.equals("")) {
            query += " AND patronymic LIKE '" + patronymic + "'";
        }
        if (true) {
            query += " AND birthday ";
            if (older) {
                query += ">";
            } else {
                query += "<";
            }
            query += " '" + birthday + "'";
        }
        if (!sex.equals("")) {
            query += " AND sex = '" + sex + "'";
        }
        if (!maritalStatus.equals("")) {
            query += " AND marital_status LIKE '" + maritalStatus + "'";
        }
        if (!nationality.equals("")) {
            query += " AND nationality LIKE '" + nationality + "'";
        }
        if (!state.equals("")) {
            query += " AND state LIKE '" + state + "'";
        }
        if (!city.equals("")) {
            query += " AND city LIKE '" + city + "'";
        }
        if (!house.equals("")) {
            query += " AND house LIKE '" + house + "'";
        }
        if (!flat.equals("")) {
            query += " AND flat LIKE '" + flat + "'";
        }
        query += ";";

        Statement statement = connection.createStatement();
        System.out.println(query);
        ResultSet contactResult = statement.executeQuery(query);
        ArrayList<Contact> contacts = new ArrayList<>();
        while (contactResult.next()) {
            Contact contact = new Contact();
            contact.setId(contactResult.getInt("id"));
            contact.setFirstName(contactResult.getString("first_name"));
            contact.setLastName(contactResult.getString("last_name"));
            contact.setPatronymic(contactResult.getString("patronymic"));
            contact.setBirthday(contactResult.getDate("birthday"));
            contact.setSex(contactResult.getString("sex"));
            contact.setEmail(contactResult.getString("email"));
            contact.setWebsite(contactResult.getString("website"));
            contact.setNationality(contactResult.getString("nationality"));
            contact.setMaritalStatus(contactResult.getString("marital_status"));
            contact.setJob(contactResult.getString("job"));
            contact.setState(contactResult.getString("state"));
            contact.setCity(contactResult.getString("city"));
            contact.setStreet(contactResult.getString("street"));
            contact.setHouse(contactResult.getString("house"));
            contact.setFlat(contactResult.getString("flat"));
            contact.setZipCode(contactResult.getString("zip_code"));

            contacts.add(contact);
        }
        return contacts;
    }
}
