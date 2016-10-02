package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.LogHelper;
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

        LogHelper.info(String.format("Request to remove contacts with ids: %s", deleteContactIdList));

        try {
            setActionMessage(request, getRemovedContactsList(deleteContactIdList));
            removeContacts(deleteContactIdList);
            response.sendRedirect(REDIRECT_URL);
        } catch (CustomException | IOException e) {
            throw new CustomException("Can't execute contact remove command: ", e);
        }
        return null;
    }

    private ArrayList<Contact> getRemovedContactsList(ArrayList<Long> ids) throws CustomException {
        ArrayList<Contact> removedContacts = new ArrayList<>();
        DBHelper dbHelper = new DBHelper();
        for (Long id : ids) {
            try {
                Contact contact = dbHelper.getContactFromDAO(id);
                if (contact != null) {
                    removedContacts.add(contact);
                }
            } catch (CustomException e) {
                throw new CustomException("Can't get removed contacts list: ", e);
            }
        }
        return removedContacts;
    }

    private void setActionMessage(HttpServletRequest request, ArrayList<Contact> contacts) {
        String message = (contacts.size() == 1) ? "Контакт\r\n" : "Контакты\r\n";
        for (Contact contact : contacts) {
            message += String.format("'%s %s' ", contact.getLastName(), contact.getFirstName());
        }
        message += (contacts.size() == 1) ? "Был удален" : "Были удалены";
        request.getSession().setAttribute("action-message", message);
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
