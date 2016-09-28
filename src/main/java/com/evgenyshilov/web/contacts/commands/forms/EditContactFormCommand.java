package com.evgenyshilov.web.contacts.commands.forms;

import com.evgenyshilov.web.contacts.commands.Command;
import com.evgenyshilov.web.contacts.database.dao.ContactDAO;
import com.evgenyshilov.web.contacts.database.dao.DAOFactory;
import com.evgenyshilov.web.contacts.database.dao.MaritalStatusDAO;
import com.evgenyshilov.web.contacts.database.dao.PhoneTypeDAO;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.database.model.MaritalStatus;
import com.evgenyshilov.web.contacts.database.model.PhoneType;
import com.evgenyshilov.web.contacts.exceptions.ContactIdNotSpecifiedException;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.MonthListBuilder;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 16.09.2016.
 */
public class EditContactFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CustomException {
        String VIEW_URL = "/contact.jsp";
        int contactId;
        try {
            contactId = getContactId(request);
            Contact contact = getContactFromDAO(contactId);
            request.setAttribute("contact", contact);
        } catch (ContactIdNotSpecifiedException | CustomException e) {
            // TODO log exception
        }
        try {
            request.setAttribute("maritalStatuses", getMaritalStatuses());
            request.setAttribute("months", new MonthListBuilder().getMonthList());
            request.setAttribute("phoneTypes", getPhoneTypes());
        } catch (CustomException e) {
            throw new CustomException("Can't create contact form: ", e);
        }
        return VIEW_URL;
    }

    private int getContactId(HttpServletRequest request) throws ContactIdNotSpecifiedException, CustomException {
        String CONTACT_ID_PARAMETER_NAME = "id";
        String contactIdString = request.getParameter(CONTACT_ID_PARAMETER_NAME);
        if (StringUtils.isEmpty(contactIdString)) {
            contactIdString = (String) request.getAttribute(CONTACT_ID_PARAMETER_NAME);
            if (StringUtils.isEmpty(contactIdString)) {
                throw new ContactIdNotSpecifiedException();
            }
        }
        try {
            return Integer.parseInt(contactIdString);
        } catch (NumberFormatException e) {
            throw new CustomException("Can't parse id parameter: ", e);
        }
    }

    private ArrayList<MaritalStatus> getMaritalStatuses() throws CustomException {
        MaritalStatusDAO maritalStatusDAO = null;
        try {
            maritalStatusDAO = (MaritalStatusDAO) DAOFactory.getDAO(MaritalStatus.class);
            return maritalStatusDAO.getAll();
        } catch (CustomException e) {
            throw new CustomException("Can't get all marital statuses for edit contact form: ", e);
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

    private ArrayList<PhoneType> getPhoneTypes() throws CustomException {
        PhoneTypeDAO phoneTypeDAO = null;
        try {
            phoneTypeDAO = (PhoneTypeDAO) DAOFactory.getDAO(PhoneType.class);
            return phoneTypeDAO.getAll();
        } catch (CustomException e) {
            throw new CustomException("Can't get all phone types for edit contact form: ", e);
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

    private Contact getContactFromDAO(int id) throws CustomException {
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
