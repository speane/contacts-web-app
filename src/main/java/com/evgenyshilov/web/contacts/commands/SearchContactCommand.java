package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.database.dao.ContactDAO;
import com.evgenyshilov.web.contacts.database.dao.DAOFactory;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.help.transfer.PaginationDTO;
import com.evgenyshilov.web.contacts.help.transfer.PaginationFactory;
import com.evgenyshilov.web.contacts.resources.ApplicationResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 19.09.2016.
 */
public class SearchContactCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ArrayList<Contact> contacts = new ArrayList<>();
        ContactDAO contactDAO = (ContactDAO) DAOFactory.getDAO(Contact.class);
        contacts = contactDAO.getAllByParams(request.getParameter("first-name"), request.getParameter("last-name"),
                request.getParameter("patronymic"), Date.valueOf(request.getParameter("birthday")), true,
                request.getParameter("sex"), request.getParameter("marital-status"),
                request.getParameter("nationality"), request.getParameter("state"), request.getParameter("city"),
                request.getParameter("house"), request.getParameter("flat"));
        request.setAttribute("contacts", contacts);

        PaginationDTO paginationDTO = PaginationFactory.createPagination(contacts.size(), 1, 1);
        request.setAttribute("pagination", paginationDTO);
        return ApplicationResources.MAIN_FORM_URL;
    }
}
