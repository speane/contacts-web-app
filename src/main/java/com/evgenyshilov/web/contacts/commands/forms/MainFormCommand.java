package com.evgenyshilov.web.contacts.commands.forms;

import com.evgenyshilov.web.contacts.commands.Command;
import com.evgenyshilov.web.contacts.database.dao.ContactDAO;
import com.evgenyshilov.web.contacts.database.dao.DAOFactory;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.help.transfer.PaginationDTO;
import com.evgenyshilov.web.contacts.help.transfer.PaginationFactory;
import com.evgenyshilov.web.contacts.resources.ApplicationResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 11.09.2016.
 */
public class MainFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ALL_CONTACTS_ATTRIBUTE_NAME = "contacts";
        String PAGINATION_ATTRIBUTE_NAME = "pagination";
        String PAGE_NUMBER_PARAMETER_NAME = "page";
        int CONTACTS_PER_PAGE = 3;


        /* Get contact from database */

        ContactDAO contactDAO = (ContactDAO)DAOFactory.getDAO(Contact.class);
        ArrayList<Contact> contacts = contactDAO.getAll();
        contactDAO.close();

        int totalContacts = contacts.size();
        String pageString = request.getParameter(PAGE_NUMBER_PARAMETER_NAME);
        int activePage = (pageString != null) ? Integer.parseInt(pageString) : 1;

        PaginationDTO paginationDTO = PaginationFactory.createPagination(totalContacts,
                CONTACTS_PER_PAGE, activePage);

        activePage = paginationDTO.getActivePage();

        int startIndex = (activePage - 1) * CONTACTS_PER_PAGE;
        int endIndex = (activePage < paginationDTO.getEndPage())
                ? (startIndex + CONTACTS_PER_PAGE) : contacts.size();

        request.setAttribute(ALL_CONTACTS_ATTRIBUTE_NAME, contacts.subList(startIndex, endIndex));
        request.setAttribute(PAGINATION_ATTRIBUTE_NAME, paginationDTO);

        return ApplicationResources.MAIN_FORM_URL;
    }


}