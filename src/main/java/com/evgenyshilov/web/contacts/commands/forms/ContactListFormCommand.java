package com.evgenyshilov.web.contacts.commands.forms;

import com.evgenyshilov.web.contacts.commands.Command;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.LogHelper;
import com.evgenyshilov.web.contacts.help.database.DBHelper;
import com.evgenyshilov.web.contacts.help.pagination.PaginationBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 11.09.2016.
 */
public class ContactListFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CustomException {
        LogHelper.info("Contact list page request");

        String VIEW_URL = "/contactlist.jsp";
        DBHelper dbHelper = new DBHelper();
        PaginationBuilder builder = new PaginationBuilder();
        ArrayList<Contact> contacts = dbHelper.getContactsFromDAO();
        try {
            builder.setRequestPagination(request, contacts);
            processActionMessage(request);
        } catch (CustomException e) {
            throw new CustomException("Can't show contact list form: ", e);
        }
        return VIEW_URL;
    }

    private void processActionMessage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String actionMessage = (String) session.getAttribute("action-message");
        if (actionMessage != null) {
            session.removeAttribute("action-message");
        }
        request.setAttribute("actionMessage", actionMessage);
    }
}
