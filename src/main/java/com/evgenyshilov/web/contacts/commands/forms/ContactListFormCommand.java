package com.evgenyshilov.web.contacts.commands.forms;

import com.evgenyshilov.web.contacts.commands.Command;
import com.evgenyshilov.web.contacts.database.dao.ContactDAO;
import com.evgenyshilov.web.contacts.database.dao.DAOFactory;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.transfer.PaginationDTO;
import com.evgenyshilov.web.contacts.help.transfer.PaginationFactory;
import com.evgenyshilov.web.contacts.resources.ApplicationConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 11.09.2016.
 */
public class ContactListFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CustomException {
        String VIEW_URL = "/contactlist.jsp";
        ArrayList<Contact> contacts = getContactsFromDB();
        try {
            setPagination(contacts, request);
        } catch (CustomException e) {
            throw new CustomException("Can't show contact list form: ", e);
        }
        return VIEW_URL;
    }

    private ArrayList<Contact> getContactsFromDB() throws CustomException {
        ContactDAO contactDAO = null;
        try {
            contactDAO = (ContactDAO) DAOFactory.getDAO(Contact.class);
            return contactDAO.getAll();
        } catch (CustomException e) {
            throw new CustomException("Can't get contacts from DAO: ", e);
        } finally {
            try {
                if (contactDAO != null) {
                    contactDAO.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    private void setPagination(ArrayList<Contact> contacts, HttpServletRequest request) throws CustomException {
        String PAGE_NUMBER_PARAMETER_NAME = "page";
        String ALL_CONTACTS_ATTRIBUTE_NAME = "contacts";
        String PAGINATION_ATTRIBUTE_NAME = "pagination";
        int CONTACTS_PER_PAGE;
        try {
            CONTACTS_PER_PAGE = getContactsPerPage();
            int totalContacts = contacts.size();
            String pageString = request.getParameter(PAGE_NUMBER_PARAMETER_NAME);
            int activePage = (pageString != null) ? Integer.parseInt(pageString) : 1;

            PaginationDTO paginationDTO = PaginationFactory.createPagination(totalContacts,
                    CONTACTS_PER_PAGE, activePage);

            activePage = paginationDTO.getActivePage();

            int startIndex = (activePage - 1) * CONTACTS_PER_PAGE;
            int endIndex = (activePage < paginationDTO.getEndPage())
                    ? (startIndex + CONTACTS_PER_PAGE) : contacts.size();

            request.setAttribute(PAGINATION_ATTRIBUTE_NAME, paginationDTO);
            request.setAttribute(ALL_CONTACTS_ATTRIBUTE_NAME, contacts.subList(startIndex, endIndex));
        } catch (CustomException e) {
            throw new CustomException("Can't create pagination: ", e);
        }
    }

    private int getContactsPerPage() throws CustomException {
        String contactPerPageProperty = ApplicationConfig.getProperty("CONTACTS_PER_PAGE");
        try {
            if (contactPerPageProperty != null) {
                try {
                    return Integer.parseInt(contactPerPageProperty);
                } catch (NumberFormatException e) {
                    throw new CustomException("Can't parse contacts per page property: ", e);
                }
            }
            else {
                throw new CustomException("Can't find contacts per page property: ", new NullPointerException());
            }
        } catch (CustomException e) {
            throw new CustomException("Can't get contacts per page value", e);
        }
    }
}
