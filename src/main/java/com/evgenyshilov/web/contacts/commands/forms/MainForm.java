package com.evgenyshilov.web.contacts.commands.forms;

import com.evgenyshilov.web.contacts.commands.Command;
import com.evgenyshilov.web.contacts.database.dao.ContactDAO;
import com.evgenyshilov.web.contacts.database.dao.DAOFactory;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.resources.ApplicationResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 11.09.2016.
 */
public class MainForm implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ArrayList<Contact> contacts = ((ContactDAO) DAOFactory.getDAO(Contact.class)).getAll();
        request.setAttribute(ApplicationResources.ALL_CONTACTS_ATTRIBUTE_NAME, contacts);

        return "/test.jsp";
    }
}
