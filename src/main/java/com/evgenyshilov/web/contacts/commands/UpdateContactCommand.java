package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.resources.ApplicationResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Evgeny Shilov on 19.09.2016.
 */
public class UpdateContactCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println((String) request.getParameter("created-phones"));

        return ApplicationResources.MAIN_FORM_URL;
    }
}
