package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.database.dao.ContactDAO;
import com.evgenyshilov.web.contacts.database.dao.DAOFactory;
import com.evgenyshilov.web.contacts.database.dao.MaritalStatusDAO;
import com.evgenyshilov.web.contacts.database.dao.PhoneTypeDAO;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.database.model.MaritalStatus;
import com.evgenyshilov.web.contacts.database.model.PhoneType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Evgeny Shilov on 20.09.2016.
 */
public class AddContactCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("maritalStatuses", getMaritalStatuses());
        request.setAttribute("months", new DateFormatSymbols(Locale.forLanguageTag("ru")).getMonths());
        request.setAttribute("phoneTypes", getPhoneTypes());

        return "/contact.jsp";
    }

    private ArrayList<MaritalStatus> getMaritalStatuses() throws InvocationTargetException, SQLException,
            InstantiationException, NoSuchMethodException, IllegalAccessException {
        MaritalStatusDAO maritalStatusDAO = (MaritalStatusDAO) DAOFactory.getDAO(MaritalStatus.class);
        ArrayList<MaritalStatus> maritalStatuses = maritalStatusDAO.getAll();
        maritalStatusDAO.close();
        return maritalStatuses;
    }

    private ArrayList<PhoneType> getPhoneTypes() throws InvocationTargetException, SQLException,
            InstantiationException, NoSuchMethodException, IllegalAccessException {
        PhoneTypeDAO phoneTypeDAO = (PhoneTypeDAO) DAOFactory.getDAO(PhoneType.class);
        ArrayList<PhoneType> phoneTypes = phoneTypeDAO.getAll();
        return phoneTypes;
    }

    private Contact getContactFromDAO(int id) throws InvocationTargetException, SQLException,
            InstantiationException, NoSuchMethodException, IllegalAccessException {
        ContactDAO contactDAO = (ContactDAO) DAOFactory.getDAO(Contact.class);
        Contact contact = contactDAO.get(id);
        contactDAO.close();

        return contact;
    }
}