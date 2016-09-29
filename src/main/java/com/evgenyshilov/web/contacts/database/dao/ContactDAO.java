package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.Attachment;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.database.model.ContactBuilder;
import com.evgenyshilov.web.contacts.database.model.Phone;
import com.evgenyshilov.web.contacts.exceptions.CustomException;

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
    public ArrayList<Contact> getAll() throws CustomException {
        ArrayList<Contact> contacts = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String GET_ALL_CONTACTS_QUERY =
                    "SELECT contact.id AS id, first_name, last_name, patronymic, " +
                    "birthday, sex, nationality.name AS nationality, " +
                    "marital_status.id AS marital_status, email, website, job, state.name AS state, " +
                    "city.name AS city, street, house, flat, zip_code, image_filename " +
                    "FROM contact " +
                    "LEFT JOIN nationality ON nationality.id = contact.nationality_id " +
                    "LEFT JOIN marital_status ON marital_status.id = contact.marital_status_id " +
                    "LEFT JOIN state ON state.id = contact.state_id " +
                    "LEFT JOIN city ON city.id = contact.city_id";

            ResultSet contactSet = statement.executeQuery(GET_ALL_CONTACTS_QUERY);
            while (contactSet.next()) {
                Contact contact = ContactBuilder.createFromResultSet(contactSet);
                contact.setPhones(getPhonesFromDatabase(contact.getId()));
                contacts.add(contact);
            }
            return contacts;

        } catch (CustomException | SQLException e) {
            throw new CustomException("Can't get all contacts: ", e);
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

    private ArrayList<Phone> getPhonesFromDatabase(int id) throws CustomException {
        PhoneDAO phoneDAO = null;
        try {
            phoneDAO = (PhoneDAO) DAOFactory.getDAO(Phone.class);
            return phoneDAO.getAllByContactId(id);
        } catch (CustomException e) {
            throw new CustomException("Can't get phones from database: ", e);
        } catch (SQLException e) {
            throw new CustomException("Can't get phones from database: ", e);
            // TODO remove this
        } finally {
            try {
                if (phoneDAO != null) {
                    phoneDAO.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    private ArrayList<Attachment> getAttachmentsFromDatabase(int id) throws CustomException {
        AttachmentDAO attachmentDAO = null;
        try {
            attachmentDAO = (AttachmentDAO) DAOFactory.getDAO(Attachment.class);
            return attachmentDAO.getAllByContactId(id);
        } catch (SQLException e) {
            throw new CustomException("Can't get attachments from database: ", e);
            // TODO remove this
        } catch (CustomException e) {
            throw new CustomException("Can't get attachments from database: ", e);
        } finally {
            try {
                if (attachmentDAO != null) {
                    attachmentDAO.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    @Override
    public Contact get(Integer key) throws CustomException {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String query =
                    "SELECT contact.id AS id, first_name, last_name, patronymic, birthday, " +
                    "sex, nationality.name AS nationality, " +
                    "marital_status.id AS marital_status, email, website, job, state.name AS state, " +
                    "city.name AS city, street, house, flat, zip_code, image_filename " +
                    "FROM contact " +
                    "LEFT JOIN nationality ON nationality.id = contact.nationality_id " +
                    "LEFT JOIN marital_status ON marital_status.id = contact.marital_status_id " +
                    "LEFT JOIN state ON state.id = contact.state_id " +
                    "LEFT JOIN city ON city.id = contact.city_id " +
                    "WHERE contact.id = " + key;
            ResultSet contactResult = statement.executeQuery(query);
            if (contactResult.next()) {
                Contact contact = ContactBuilder.createFromResultSet(contactResult);
                contact.setPhones(getPhonesFromDatabase(contact.getId()));
                contact.setAttachments(getAttachmentsFromDatabase(contact.getId()));
                return contact;
            } else {
                return null;
            }
        } catch (SQLException | CustomException e) {
            throw new CustomException("Can't get contact from database: ", e);
        }
    }

    @Override
    public void update(Integer key, Contact value) throws CustomException {
        String query = "UPDATE contact SET " +
                "first_name=?, last_name=?, patronymic=?, birthday=?, sex=?, nationality_id=?, " +
                "marital_status_id=?, website=?, email=?, job=?, state_id=?, city_id=?, " +
                "street=?, house=?, flat=?, zip_code=?, image_filename=? WHERE id=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = createPreparedContactStatement(query, value);
            preparedStatement.setInt(18, key);
            preparedStatement.executeUpdate();
        } catch (CustomException | SQLException e) {
            throw new CustomException("Can't update contact: ", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    @Override
    public void delete(Integer id) throws CustomException {
        Statement statement = null;
        try {
            statement = connection.createStatement();

            removeContactPhones(id);
            removeContactAttachments(id);

            String query = "DELETE FROM contact WHERE id = " + id + ";";
            statement.executeUpdate(query);
        } catch (SQLException | CustomException e) {
            throw new CustomException("Can't delete contact: ", e);
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

    private void removeContactAttachments(int contactId) throws CustomException {
        AttachmentDAO attachmentDAO = null;
        try {
            attachmentDAO = (AttachmentDAO) DAOFactory.getDAO(Attachment.class);
            attachmentDAO.deleteAllContactAttachments(contactId);
        } catch (CustomException e) {
            throw new CustomException("Can't remove all contact attachments: ", e);
        } finally {
            try {
                if (attachmentDAO != null) {
                    attachmentDAO.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    private void removeContactPhones(int contactId) throws CustomException {
        PhoneDAO phoneDAO = null;
        try {
            phoneDAO = (PhoneDAO) DAOFactory.getDAO(Phone.class);
            phoneDAO.deleteAllContactPhones(contactId);
        } catch (CustomException e) {
            throw new CustomException("Can't remove contact phones: ", e);
        } finally {
            try {
                if (phoneDAO != null) {
                    phoneDAO.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    @Override
    public void insert(Contact value) throws CustomException {
        String query = "INSERT INTO contact (`first_name`, `last_name`, `patronymic`, `birthday`, " +
                "`sex`, `nationality_id`, `marital_status_id`, `website`, `email`, `job`, `state_id`, `city_id`, " +
                "`street`,`house`, `flat`, `zip_code`, `image_filename`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = null;
        try {
            statement = createPreparedContactStatement(query, value);
            statement.executeUpdate();
        } catch (CustomException | SQLException e) {
            throw new CustomException("Can't insert contact: ", e);
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

    public int getLastInsertedId() throws CustomException {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String query = "SELECT last_insert_id() AS last_id FROM contact";
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet.next() ? resultSet.getInt("last_id") : -1;
        } catch (SQLException e) {
            throw new CustomException("Can't get last inserted contact id: ", e);
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

    private PreparedStatement createPreparedContactStatement(String query, Contact contact) throws CustomException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, contact.getFirstName());
            statement.setString(2, contact.getLastName());
            statement.setString(3, contact.getPatronymic());
            statement.setDate(4, contact.getBirthday());
            statement.setString(5, contact.getSex());
            statement.setInt(6, getContactNationalityId(contact));
            statement.setInt(7, contact.getMaritalStatus());
            statement.setString(8, contact.getWebsite());
            statement.setString(9, contact.getEmail());
            statement.setString(10, contact.getJob());
            statement.setInt(11, getContactStateId(contact));
            statement.setInt(12, getContactCityId(contact));
            statement.setString(13, contact.getStreet());
            statement.setString(14, contact.getHouse());
            statement.setString(15, contact.getFlat());
            statement.setString(16, contact.getZipCode());
            statement.setString(17, contact.getImageFileName());
        } catch (SQLException e) {
            throw new CustomException("Can't create prepared statement: ", e);
        }
        return statement;
    }

    public ArrayList<Contact> getAllByParams(String firstName, String lastName, String patronymic,
                                             Date birthday, boolean older, String sex,
                                             String maritalStatus, String nationality,
                                             String state, String city, String house, String flat) throws SQLException {
        String query = "SELECT contact.id AS id, first_name, last_name, patronymic, birthday, sex, nationality.name AS nationality, " +
                "marital_status.id AS marital_status, email, website, job, state.name AS state, city.name AS city, street, house, flat, zip_code " +
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
            contact.setMaritalStatus(contactResult.getInt("marital_status"));
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

    private int getContactCityId(Contact contact) throws SQLException {
        int cityId;
        if ((cityId = getCityId(contact.getCity())) != -1) {
            return cityId;
        }
        else {
            String insertQuery = "INSERT INTO city (name) VALUES (\"" + contact.getCity() + "\");";
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertQuery);
            statement.close();
            return getCityId(contact.getCity());
        }
    }

    private int getCityId(String city) throws SQLException {
        String query = "SELECT id FROM city WHERE name = \"" + city + "\" LIMIT 1;";
        System.out.println(query);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        int id;
        if (resultSet.next()) {
            id = resultSet.getInt("id");
        }
        else {
            id = -1;
        }
        statement.close();
        return id;
    }

    private int getContactStateId(Contact contact) throws SQLException {
        int stateId;
        if ((stateId = getStateId(contact.getState())) != -1) {
            return stateId;
        }
        else {
            String insertQuery = "INSERT INTO state (name) VALUES (\"" + contact.getState() + "\");";
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertQuery);
            statement.close();
            return getStateId(contact.getState());
        }
    }

    private int getStateId(String state) throws SQLException {
        String query = "SELECT id FROM state WHERE name = \"" + state + "\" LIMIT 1;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        int id;
        if (resultSet.next()) {
            id = resultSet.getInt("id");
        }
        else {
            id = -1;
        }
        statement.close();
        return id;
    }

    private int getContactNationalityId(Contact contact) throws SQLException {
        int nationalityId;
        if ((nationalityId = getNationalityId(contact.getNationality())) != -1) {
            return nationalityId;
        }
        else {
            String insertQuery = "INSERT INTO nationality (name) VALUES (\"" + contact.getNationality() + "\");";
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertQuery);
            statement.close();
            return getNationalityId(contact.getNationality());
        }
    }

    private int getNationalityId(String nationality) throws SQLException {
        String query = "SELECT id FROM nationality WHERE name = \"" + nationality + "\" LIMIT 1;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        int id;
        if (resultSet.next()) {
            id = resultSet.getInt("id");
        }
        else {
            id = -1;
        }
        statement.close();
        return id;
    }


}
