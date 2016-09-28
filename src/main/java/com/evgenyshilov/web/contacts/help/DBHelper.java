package com.evgenyshilov.web.contacts.help;

import com.evgenyshilov.web.contacts.database.dao.ContactDAO;
import com.evgenyshilov.web.contacts.database.dao.DAOFactory;
import com.evgenyshilov.web.contacts.database.dao.MaritalStatusDAO;
import com.evgenyshilov.web.contacts.database.dao.PhoneTypeDAO;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.database.model.MaritalStatus;
import com.evgenyshilov.web.contacts.database.model.PhoneType;
import com.evgenyshilov.web.contacts.exceptions.CustomException;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 29.09.2016.
 */
public class DBHelper {
    public ArrayList<Contact> getContactsFromDAO() throws CustomException {
        ContactDAO contactDAO = null;
        try {
            contactDAO = (ContactDAO) DAOFactory.getDAO(Contact.class);
            return contactDAO.getAll();
        } catch (CustomException e) {
            throw new CustomException("Can't get contacts from DAO: ", e);
        } finally {
            try {
                if (contactDAO != null) {
                    contactDAO.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    public ArrayList<MaritalStatus> getMaritalStatuses() throws CustomException {
        MaritalStatusDAO maritalStatusDAO = null;
        try {
            maritalStatusDAO = (MaritalStatusDAO) DAOFactory.getDAO(MaritalStatus.class);
            return maritalStatusDAO.getAll();
        } catch (CustomException e) {
            throw new CustomException("Can't get all marital statuses from dao: ", e);
        } finally {
            try {
                if (maritalStatusDAO != null) {
                    maritalStatusDAO.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    public ArrayList<PhoneType> getPhoneTypes() throws CustomException {
        PhoneTypeDAO phoneTypeDAO = null;
        try {
            phoneTypeDAO = (PhoneTypeDAO) DAOFactory.getDAO(PhoneType.class);
            return phoneTypeDAO.getAll();
        } catch (CustomException e) {
            throw new CustomException("Can't get all phone types from dao: ", e);
        } finally {
            try {
                if (phoneTypeDAO != null) {
                    phoneTypeDAO.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    public Contact getContactFromDAO(int id) throws CustomException {
        ContactDAO contactDAO = null;
        try {
            contactDAO = (ContactDAO) DAOFactory.getDAO(Contact.class);
            return contactDAO.get(id);
        } catch (CustomException e) {
            throw new CustomException("Can't get contact from dao: ", e);
        } finally {
            try {
                if (contactDAO != null) {
                    contactDAO.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }
}
