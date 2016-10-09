package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.commands.search.DateSearchParam;
import com.evgenyshilov.web.contacts.commands.search.SearchParams;
import com.evgenyshilov.web.contacts.database.model.Attachment;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.database.model.ContactBuilder;
import com.evgenyshilov.web.contacts.database.model.Phone;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.LogHelper;
import com.evgenyshilov.web.contacts.help.utils.StatementUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 12.09.2016.
 */
public class ContactDAO extends BaseDAO<Long, Contact> {
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
                LogHelper.error("Can't close statement: ", e);
            }
        }
    }

    private ArrayList<Phone> getPhonesFromDatabase(long id) throws CustomException {
        PhoneDAO phoneDAO = null;
        try {
            phoneDAO = (PhoneDAO) DAOFactory.getDAO(Phone.class);
            return phoneDAO.getAllByContactId(id);
        } catch (CustomException e) {
            throw new CustomException("Can't get phones from database: ", e);
        } finally {
            try {
                if (phoneDAO != null) {
                    phoneDAO.close();
                }
            } catch (SQLException e) {
                LogHelper.error("Can't close phone dao: ", e);
            }
        }
    }

    private ArrayList<Attachment> getAttachmentsFromDatabase(long id) throws CustomException {
        AttachmentDAO attachmentDAO = null;
        try {
            attachmentDAO = (AttachmentDAO) DAOFactory.getDAO(Attachment.class);
            return attachmentDAO.getAllByContactId(id);
        } catch (CustomException e) {
            throw new CustomException("Can't get attachments from database: ", e);
        } finally {
            try {
                if (attachmentDAO != null) {
                    attachmentDAO.close();
                }
            } catch (SQLException e) {
                LogHelper.error("Can't close attachment dao: ", e);
            }
        }
    }

    @Override
    public Contact get(Long key) throws CustomException {
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
    public void update(Long key, Contact value) throws CustomException {
        String query = "UPDATE contact SET " +
                "first_name=?, last_name=?, patronymic=?, birthday=?, sex=?, nationality_id=?, " +
                "marital_status_id=?, website=?, email=?, job=?, state_id=?, city_id=?, " +
                "street=?, house=?, flat=?, zip_code=?, image_filename=? WHERE id=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = createPreparedContactStatement(query, value);
            System.out.println(preparedStatement);
            preparedStatement.setLong(18, key);
            preparedStatement.executeUpdate();
        } catch (CustomException | SQLException e) {
            throw new CustomException("Can't update contact: ", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                LogHelper.error("Can't close statement: ", e);
            }
        }
    }

    @Override
    public void delete(Long id) throws CustomException {
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
                LogHelper.error("Can't close statement: ", e);
            }
        }
    }

    private void removeContactAttachments(long contactId) throws CustomException {
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
                LogHelper.error("Can't close statement: ", e);
            }
        }
    }

    private void removeContactPhones(long contactId) throws CustomException {
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
                LogHelper.error("Can't close statement: ", e);
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
                LogHelper.error("Can't close statement: ", e);
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
                LogHelper.error("Can't close statement: ", e);
            }
        }
    }

    private PreparedStatement createPreparedContactStatement(String query, Contact contact) throws CustomException {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            StatementUtils.setStatementStringValue(statement, 1, contact.getFirstName());
            StatementUtils.setStatementStringValue(statement, 2, contact.getLastName());
            StatementUtils.setStatementStringValue(statement, 3, contact.getPatronymic());
            StatementUtils.setStatementDateValue(statement, 4, contact.getBirthday());
            StatementUtils.setStatementStringValue(statement, 5, contact.getSex());
            StatementUtils.setStatementLongValue(statement, 6, getContactNationalityId(contact));
            StatementUtils.setStatementLongValue(statement, 7, contact.getMaritalStatus());
            StatementUtils.setStatementStringValue(statement, 8, contact.getWebsite());
            StatementUtils.setStatementStringValue(statement, 9, contact.getEmail());
            StatementUtils.setStatementStringValue(statement, 10, contact.getJob());
            StatementUtils.setStatementLongValue(statement, 11, getContactStateId(contact));
            StatementUtils.setStatementLongValue(statement, 12, getContactCityId(contact));
            StatementUtils.setStatementStringValue(statement, 13, contact.getStreet());
            StatementUtils.setStatementStringValue(statement, 14, contact.getHouse());
            StatementUtils.setStatementStringValue(statement, 15, contact.getFlat());
            StatementUtils.setStatementStringValue(statement, 16, contact.getZipCode());
            StatementUtils.setStatementStringValue(statement, 17, contact.getImageFileName());
            return statement;
        } catch (SQLException e) {
            throw new CustomException("Can't create prepared statement: ", e);
        }
    }

    private Long getContactCityId(Contact contact) throws CustomException {
        Statement statement = null;
        try {
            if (!StringUtils.isEmpty(contact.getCity())) {
                Long cityId;
                if ((cityId = getCityId(contact.getCity())) != null) {
                    return cityId;
                }
                else {
                    statement = connection.createStatement();
                    String insertQuery = "INSERT INTO city (name) VALUES (\"" + contact.getCity() + "\");";
                    statement.executeUpdate(insertQuery);
                    return getCityId(contact.getCity());
                }
            } else {
                return null;
            }
        } catch (SQLException | CustomException e) {
            throw new CustomException("Can't get contact city id: ", e);
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

    private Long getCityId(String city) throws CustomException {
        String query = "SELECT id FROM city WHERE name = \"" + city + "\" LIMIT 1;";
        try {
            return getIdFromQuery(query);
        } catch (CustomException e) {
            throw new CustomException("Can't get city id from database: ", e);
        }
    }

    private Long getContactStateId(Contact contact) throws CustomException {
        Statement statement = null;
        try {
            if (!StringUtils.isEmpty(contact.getState())) {
                Long stateId;
                if ((stateId = getStateId(contact.getState())) != null) {
                    return stateId;
                }
                else {
                    String insertQuery = "INSERT INTO state (name) VALUES (\"" + contact.getState() + "\");";
                    statement = connection.createStatement();
                    statement.executeUpdate(insertQuery);
                    return getStateId(contact.getState());
                }
            } else {
                return null;
            }
        } catch (CustomException | SQLException e) {
            throw new CustomException("Can't get contact state id from database: ", e);
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

    private Long getIdFromQuery(String query) throws CustomException {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            Long id;
            if (resultSet.next()) {
                id = resultSet.getLong("id");
            }
            else {
                id = null;
            }
            return id;
        } catch (SQLException e) {
            throw new CustomException("Can't get id from query: ", e);
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

    private Long getStateId(String state) throws CustomException {
        String query = "SELECT id FROM state WHERE name = \"" + state + "\" LIMIT 1;";
        try {
            return getIdFromQuery(query);
        } catch (CustomException e) {
            throw new CustomException("Can't get state id from database: ", e);
        }
    }

    private Long getContactNationalityId(Contact contact) throws CustomException {
        Statement statement = null;
        try {
            if (!StringUtils.isEmpty(contact.getNationality())) {
                Long nationalityId;
                if ((nationalityId = getNationalityId(contact.getNationality())) != null) {
                    return nationalityId;
                }
                else {
                    statement = connection.createStatement();
                    String insertQuery = "INSERT INTO nationality (name) VALUES (\"" + contact.getNationality() + "\");";
                    statement.executeUpdate(insertQuery);
                    return getNationalityId(contact.getNationality());
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new CustomException("Can't get contact nationality by id: ", e);
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

    private Long getNationalityId(String nationality) throws CustomException {
        String query = "SELECT id FROM nationality WHERE name = \"" + nationality + "\" LIMIT 1;";
        try {
            return getIdFromQuery(query);
        } catch (CustomException e) {
            throw new CustomException("Can't get nationality id from database: ", e);
        }
    }

    public ArrayList<Contact> getContactsByParams(SearchParams params) throws CustomException {
        String query = "SELECT contact.id AS id, first_name, last_name, patronymic, " +
                "birthday, sex, nationality.name AS nationality, " +
                "marital_status.id AS marital_status, email, website, job, state.name AS state, " +
                "city.name AS city, street, house, flat, zip_code, image_filename " +
                "FROM contact " +
                "LEFT JOIN nationality ON nationality.id = contact.nationality_id " +
                "LEFT JOIN marital_status ON marital_status.id = contact.marital_status_id " +
                "LEFT JOIN state ON state.id = contact.state_id " +
                "LEFT JOIN city ON city.id = contact.city_id WHERE TRUE";
        query += createSearchStringPart("first_name", params.getFirstName());
        query += createSearchStringPart("last_name", params.getLastName());
        query += createSearchStringPart("patronymic", params.getPatronymic());
        query += createSearchDatePart("birthday", params.getBirthday(), params.getDateSearchParam());
        query += createSearchSexPart(params.getSex());
        query += createSearchIdPart("marital_status.name", params.getMaritalStatus());
        query += createSearchStringPart("nationality.name", params.getNationality());
        query += createSearchStringPart("state.name", params.getState());
        query += createSearchStringPart("city.name", params.getCity());
        query += createSearchStringPart("street", params.getStreet());
        query += createSearchStringPart("house", params.getHouse());
        query += createSearchStringPart("flat", params.getFlat());
        query += createSearchStringPart("zip_code", params.getZipcode());

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ArrayList<Contact> contacts = new ArrayList<>();
            while (resultSet.next()) {
                contacts.add(ContactBuilder.createFromResultSet(resultSet));
            }
            return contacts;
        } catch (SQLException e) {
            throw new CustomException("Can't get contacts by params from database: ", e);
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

    private String createSearchStringPart(String paramName, String paramValue) {
        return !StringUtils.isEmpty(paramValue) ? String.format(" AND %s LIKE '%s'", paramName, paramValue) : "";
    }

    private String createSearchDatePart(String paramName, Date date, DateSearchParam searchParam) {
        if (date != null) {
            String dateSearchString = " AND " + paramName;
            switch (searchParam) {
                case OLDER:
                    dateSearchString += " < ";
                    break;
                case YOUNGER:
                    dateSearchString += " > ";
                    break;
                case EQUAL:
                    dateSearchString += " = ";
                    break;
                default:
                    break;
            }
            dateSearchString += "'" + date.toString() + "'";
            return dateSearchString;
        }
        return "";
    }

    private String createSearchSexPart(String sex) {
        if (StringUtils.equals(sex, "f") || StringUtils.equals(sex, "m")) {
            return createSearchStringPart("sex", sex);
        }
        return "";
    }

    private String createSearchIdPart(String paramName, Long paramValue) {
        if (paramValue != null && paramValue != 0) {
            return String.format(" AND %s = %d", paramName, paramValue);
        }
        return "";
    }
}
