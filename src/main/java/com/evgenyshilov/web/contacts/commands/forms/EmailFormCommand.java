package com.evgenyshilov.web.contacts.commands.forms;

import com.evgenyshilov.web.contacts.commands.Command;
import com.evgenyshilov.web.contacts.database.dao.ContactDAO;
import com.evgenyshilov.web.contacts.database.dao.DAOFactory;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.help.RequestParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 25.09.2016.
 */
public class EmailFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequestParser requestParser = new RequestParser(request);
        ArrayList<Contact> recipients = new ArrayList<>();
        ArrayList<Integer> checkedContacts = requestParser.getCheckedIdList("contact-check");
        if (checkedContacts.size() > 0) {
            ContactDAO contactDAO = (ContactDAO) DAOFactory.getDAO(Contact.class);
            for (Integer checkedContactId : checkedContacts) {
                Contact contact = contactDAO.get(checkedContactId);
                recipients.add(contact);
            }

            request.setAttribute("recipients", recipients);
            ArrayList<String> patterns = new ArrayList<>();
            patterns.add("first pattern <first_name>");
            patterns.add("second pattern <last_name> <first_name>");
            request.setAttribute("patterns", patterns);

            return "/sendemail.jsp";
        } else {
            response.sendRedirect("/app/contact-list");
            return null;
        }
    }
}
