package com.evgenyshilov.web.contacts.commands.search;

import com.evgenyshilov.web.contacts.commands.Command;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.LogHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 19.09.2016.
 */
public class SearchContactCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CustomException {
        String VIEW_URL = "/contactlist.jsp";
        try {
            SearchParams searchParams = new SearchParamsDTOBuilder().createFromRequest(request);

            LogHelper.info("Search contact request with search parameters: " + searchParams.toString());

            ArrayList<Contact> contacts = new ContactSearcher().searchContacts(searchParams);
            request.setAttribute("contacts", contacts);
        } catch (CustomException e) {
            throw new CustomException("Can't execute search command: ", e);
        }
        return VIEW_URL;
    }
}
