package com.evgenyshilov.web.contacts.commands.forms;

import com.evgenyshilov.web.contacts.commands.Command;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.database.DBHelper;
import com.evgenyshilov.web.contacts.help.pagination.PaginationBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 11.09.2016.
 */
public class ContactListFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CustomException {
        String VIEW_URL = "/contactlist.jsp";
        DBHelper dbHelper = new DBHelper();
        PaginationBuilder builder = new PaginationBuilder();
        ArrayList<Contact> contacts = dbHelper.getContactsFromDAO();
        try {
            builder.setRequestPagination(request, contacts);
        } catch (CustomException e) {
            throw new CustomException("Can't show contact list form: ", e);
        }
        return VIEW_URL;
    }
}
