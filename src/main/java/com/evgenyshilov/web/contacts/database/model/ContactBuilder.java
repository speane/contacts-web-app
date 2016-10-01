package com.evgenyshilov.web.contacts.database.model;

import com.evgenyshilov.web.contacts.exceptions.CustomException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Evgeny Shilov on 28.09.2016.
 */
public class ContactBuilder {
    public static Contact createFromResultSet(ResultSet contactSet) throws CustomException {
        Contact contact = new Contact();
        try {
            contact.setId(contactSet.getLong("id"));
            contact.setFirstName(contactSet.getString("first_name"));
            contact.setLastName(contactSet.getString("last_name"));
            contact.setPatronymic(contactSet.getString("patronymic"));
            contact.setBirthday(contactSet.getDate("birthday"));
            contact.setSex(contactSet.getString("sex"));
            contact.setEmail(contactSet.getString("email"));
            contact.setWebsite(contactSet.getString("website"));
            contact.setNationality(contactSet.getString("nationality"));
            contact.setMaritalStatus(contactSet.getLong("marital_status"));
            contact.setJob(contactSet.getString("job"));
            contact.setState(contactSet.getString("state"));
            contact.setCity(contactSet.getString("city"));
            contact.setStreet(contactSet.getString("street"));
            contact.setHouse(contactSet.getString("house"));
            contact.setFlat(contactSet.getString("flat"));
            contact.setZipCode(contactSet.getString("zip_code"));
            contact.setImageFileName(contactSet.getString("image_filename"));
        } catch (SQLException e) {
            throw new CustomException("Can't create contact from result set: ", e);
        }
        return contact;
    }
}
