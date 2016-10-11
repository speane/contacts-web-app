package com.evgenyshilov.web.contacts.commands.forms;

import com.evgenyshilov.web.contacts.commands.Command;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.ContactIdNotSpecifiedException;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.LogHelper;
import com.evgenyshilov.web.contacts.help.database.DBHelper;
import com.evgenyshilov.web.contacts.help.utils.MonthListBuilder;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Evgeny Shilov on 16.09.2016.
 */
public class EditContactFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CustomException {
        String VIEW_URL = "/contact.jsp";
        int contactId;
        DBHelper dbHelper = new DBHelper();
        try {
            try {
                contactId = getContactId(request);
            } catch (ContactIdNotSpecifiedException | CustomException e) {
                LogHelper.error("Cannot get contact id: ", e);
                contactId = 0;
            }

            LogHelper.info(String.format("Edit contact with id = %d request", contactId));

            Contact contact = dbHelper.getContactFromDAO(contactId);
            request.setAttribute("contact", contact);
            request.setAttribute("maritalStatuses", dbHelper.getMaritalStatuses());
            request.setAttribute("months", new MonthListBuilder().getMonthList());
            request.setAttribute("phoneTypes", dbHelper.getPhoneTypes());
        } catch (CustomException e) {
            throw new CustomException("Cannot execute edit contact command: ", e);
        }
        return VIEW_URL;
    }

    private int getContactId(HttpServletRequest request) throws ContactIdNotSpecifiedException, CustomException {
        String CONTACT_ID_PARAMETER_NAME = "id";
        String contactIdString = request.getParameter(CONTACT_ID_PARAMETER_NAME);
        if (StringUtils.isEmpty(contactIdString)) {
            contactIdString = (String) request.getAttribute(CONTACT_ID_PARAMETER_NAME);
            if (StringUtils.isEmpty(contactIdString)) {
                throw new ContactIdNotSpecifiedException();
            }
        }
        try {
            return Integer.parseInt(contactIdString);
        } catch (NumberFormatException e) {
            throw new CustomException("Can't parse id parameter: ", e);
        }
    }
}
