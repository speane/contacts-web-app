package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.database.DBHelper;
import com.evgenyshilov.web.contacts.help.utils.RequestParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 20.09.2016.
 */
public class DeleteContactsCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CustomException {
        String REDIRECT_URL = "/app/contact-list";
        ArrayList<Long> deleteContactIdList = new RequestParser(request).getCheckedIdList("contact-check");
        try {
            removeContacts(deleteContactIdList);
            response.sendRedirect(REDIRECT_URL);
        } catch (CustomException | IOException e) {
            throw new CustomException("Can't execute contact remove command: ", e);
        }
        return null;
    }

    private void removeContacts(ArrayList<Long> ids) throws CustomException {
        DBHelper dbHelper = new DBHelper();
        for (Long id : ids) {
            try {
                dbHelper.removeContact(id);
            } catch (CustomException e) {
                throw new CustomException("Can't remove contact: ", e);
            }
        }
    }
}
