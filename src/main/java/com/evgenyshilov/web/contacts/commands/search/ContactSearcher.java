package com.evgenyshilov.web.contacts.commands.search;

import com.evgenyshilov.web.contacts.database.dao.ContactDAO;
import com.evgenyshilov.web.contacts.database.dao.DAOFactory;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.LogHelper;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 29.09.2016.
 */
public class ContactSearcher {
    public ArrayList<Contact> searchContacts(SearchParams params) throws CustomException {
        ContactDAO contactDAO = null;
        try {
            contactDAO = (ContactDAO) DAOFactory.getDAO(Contact.class);
            return contactDAO.getContactsByParams(params);
        } catch (CustomException e) {
            throw new CustomException("Can't search contacts in dao: ", e);
        } finally {
            try {
                if (contactDAO != null) {
                    contactDAO.close();
                }
            } catch (SQLException e) {
                LogHelper.error("Can't close contact dao: ", e);
            }
        }
    }
}
