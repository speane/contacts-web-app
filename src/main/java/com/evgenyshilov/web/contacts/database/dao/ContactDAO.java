package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.Attachment;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.database.model.ContactBuilder;
import com.evgenyshilov.web.contacts.database.model.Phone;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 12.09.2016.
 */
public class ContactDAO extends GenericDAO<Long, Contact> {
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
                // TODO log exception
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
            // TODO remove this
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
                // TODO log exception
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
                // TODO log exception
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
                // TODO log exception
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
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            setStatementStringValue(statement, 1, contact.getFirstName());
            setStatementStringValue(statement, 2, contact.getLastName());
            setStatementStringValue(statement, 3, contact.getPatronymic());
            setStatementDateValue(statement, 4, contact.getBirthday());
            setStatementStringValue(statement, 5, contact.getSex());
            setStatementLongValue(statement, 6, getContactNationalityId(contact));
            setStatementLongValue(statement, 7, contact.getMaritalStatus());
            setStatementStringValue(statement, 8, contact.getWebsite());
            setStatementStringValue(statement, 9, contact.getEmail());
            setStatementStringValue(statement, 10, contact.getJob());
            setStatementLongValue(statement, 11, getContactStateId(contact));
            setStatementLongValue(statement, 12, getContactCityId(contact));
            setStatementStringValue(statement, 13, contact.getStreet());
            setStatementStringValue(statement, 14, contact.getHouse());
            setStatementStringValue(statement, 15, contact.getFlat());
            setStatementStringValue(statement, 16, contact.getZipCode());
            setStatementStringValue(statement, 17, contact.getImageFileName());
            return statement;
        } catch (SQLException e) {
            throw new CustomException("Can't create prepared statement: ", e);
        }
    }

    private void setStatementDateValue(PreparedStatement statement, int index, Date value) throws CustomException {
        try {
            if (value != null) {
                statement.setDate(index, value);
            } else {
                statement.setNull(index, Types.DATE);
            }
        } catch (SQLException e) {
            throw new CustomException("Can't set statement date value: ", e);
        }
    }

    private void setStatementStringValue(PreparedStatement statement, int index, String value) throws CustomException {
        try {
            if (!StringUtils.isEmpty(value)) {
                statement.setString(index, value);
            } else {
                statement.setNull(index, Types.VARCHAR);
            }
        } catch (SQLException e) {
            throw new CustomException("Can't set statement string value: ", e);
        }
    }

    private void setStatementLongValue(PreparedStatement statement, int index, Long value) throws CustomException {
        try {
            if (value != null) {
                statement.setLong(index, value);
            } else {
                statement.setNull(index, Types.BIGINT);
            }
        } catch (SQLException e) {
            throw new CustomException("Can't set statement long value: " ,e);
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
                // TODO log exception
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
                // TODO log exception
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
                // TODO log exception
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
                // TODO log exception
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


}
