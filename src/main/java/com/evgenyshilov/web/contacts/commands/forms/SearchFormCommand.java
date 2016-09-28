package com.evgenyshilov.web.contacts.commands.forms;

import com.evgenyshilov.web.contacts.commands.Command;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.DBHelper;
import com.evgenyshilov.web.contacts.help.MonthListBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Evgeny Shilov on 19.09.2016.
 */
public class SearchFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CustomException {
        String VIEW_URL = "/contactsearch.jsp";
        DBHelper dbHelper = new DBHelper();
        MonthListBuilder monthListBuilder = new MonthListBuilder();
        try {
            request.setAttribute("maritalStatuses", dbHelper.getMaritalStatuses());
            request.setAttribute("months", monthListBuilder.getMonthList());
        } catch (CustomException e) {
            throw new CustomException("Can't prepare search form: ", e);
        }
        return VIEW_URL;
    }
}
