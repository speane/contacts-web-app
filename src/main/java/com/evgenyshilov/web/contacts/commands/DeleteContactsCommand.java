package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.database.dao.ContactDAO;
import com.evgenyshilov.web.contacts.database.dao.DAOFactory;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.help.RequestParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 20.09.2016.
 */
public class DeleteContactsCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ArrayList<Integer> deleteContactIdList = new RequestParser(request).getCheckedIdList("contact-check");
        removeContacts(deleteContactIdList);
        response.sendRedirect("/app/contact-list");
        return null;
    }

    private void removeContacts(ArrayList<Integer> ids) throws InvocationTargetException, SQLException,
            InstantiationException, NoSuchMethodException, IllegalAccessException {
        ContactDAO contactDAO = (ContactDAO) DAOFactory.getDAO(Contact.class);
        ids.forEach(contactDAO::delete);
        contactDAO.close();
    }
}
