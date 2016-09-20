package com.evgenyshilov.web.contacts.commands.forms;

import com.evgenyshilov.web.contacts.commands.Command;
import com.evgenyshilov.web.contacts.database.dao.ContactDAO;
import com.evgenyshilov.web.contacts.database.dao.DAOFactory;
import com.evgenyshilov.web.contacts.database.model.Contact;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * Created by Evgeny Shilov on 16.09.2016.
 */
public class EditForm implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contactIdAttribute = request.getParameter("id");
        if (contactIdAttribute != null) {
            Contact contact = getContactFromDAO(Integer.parseInt(contactIdAttribute));
            request.setAttribute("contact", contact);
        }

        return "/contact.jsp";
    }

    private Contact getContactFromDAO(int id) throws InvocationTargetException, SQLException,
            InstantiationException, NoSuchMethodException, IllegalAccessException {
        ContactDAO contactDAO = (ContactDAO) DAOFactory.getDAO(Contact.class);
        Contact contact = contactDAO.get(id);
        contactDAO.close();

        return contact;
    }
}
