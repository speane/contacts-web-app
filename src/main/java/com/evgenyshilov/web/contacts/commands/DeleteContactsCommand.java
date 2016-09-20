package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.help.RequestParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Evgeny Shilov on 20.09.2016.
 */
public class DeleteContactsCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        new RequestParser(request).getCheckedIdList("contact-check");
        response.sendRedirect("/app/contact-list");
        return null;
    }
}
