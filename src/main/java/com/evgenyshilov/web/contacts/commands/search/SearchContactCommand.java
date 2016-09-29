package com.evgenyshilov.web.contacts.commands.search;

import com.evgenyshilov.web.contacts.commands.Command;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.transfer.PaginationFactory;

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
            SearchParamsDTO searchParamsDTO = new SearchParamsDTOBuilder().createFromRequest(request);
            ArrayList<Contact> contacts = new ContactSearcher().searchContacts(searchParamsDTO);
            request.setAttribute("contacts", contacts);
            request.setAttribute("pagination", new PaginationFactory().createPagination(contacts.size(), 1, 1));
        } catch (CustomException e) {
            throw new CustomException("Can't execute search command: ", e);
        }
        return VIEW_URL;
    }
}
