package com.evgenyshilov.web.contacts.help.pagination;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.resources.ApplicationConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 02.10.2016.
 */
public class PaginationBuilder {
    public void setRequestPagination(HttpServletRequest request, ArrayList<Contact> contacts) throws CustomException {
        String PAGE_NUMBER_PARAMETER_NAME = "page";
        String ALL_CONTACTS_ATTRIBUTE_NAME = "contacts";
        String PAGINATION_ATTRIBUTE_NAME = "pagination";
        int CONTACTS_PER_PAGE;
        try {
            CONTACTS_PER_PAGE = getContactsPerPage();
            int totalContacts = contacts.size();
            String pageString = request.getParameter(PAGE_NUMBER_PARAMETER_NAME);
            int activePage = (pageString != null) ? Integer.parseInt(pageString) : 1;

            PaginationDTO paginationDTO = new PaginationFactory().createPagination(totalContacts,
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
