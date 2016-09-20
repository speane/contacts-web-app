package com.evgenyshilov.web.contacts.commands.forms;

import com.evgenyshilov.web.contacts.commands.Command;
import com.evgenyshilov.web.contacts.database.dao.ContactDAO;
import com.evgenyshilov.web.contacts.database.dao.DAOFactory;
import com.evgenyshilov.web.contacts.database.dao.MaritalStatusDAO;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.database.model.MaritalStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 16.09.2016.
 */
public class EditFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contactIdParameter = request.getParameter("id");
        Contact contact = null;
        if (contactIdParameter != null) {
            contact = getContactFromDAO(Integer.parseInt(contactIdParameter));
        } else {
            String contactIdAttribute = (String) request.getAttribute("id");
            if (contactIdAttribute != null) {
                contact = getContactFromDAO(Integer.parseInt(contactIdAttribute));
            }
        }
        request.setAttribute("contact", contact);
        request.setAttribute("maritalStatuses", getMaritalStatuses());

        return "/contact.jsp";
    }

    private ArrayList<MaritalStatus> getMaritalStatuses() throws InvocationTargetException, SQLException,
            InstantiationException, NoSuchMethodException, IllegalAccessException {
        MaritalStatusDAO maritalStatusDAO = (MaritalStatusDAO) DAOFactory.getDAO(MaritalStatus.class);
        ArrayList<MaritalStatus> maritalStatuses = maritalStatusDAO.getAll();
        maritalStatusDAO.close();
        return maritalStatuses;
    }

    private Contact getContactFromDAO(int id) throws InvocationTargetException, SQLException,
            InstantiationException, NoSuchMethodException, IllegalAccessException {
        ContactDAO contactDAO = (ContactDAO) DAOFactory.getDAO(Contact.class);
        Contact contact = contactDAO.get(id);
        contactDAO.close();

        return contact;
    }
}
