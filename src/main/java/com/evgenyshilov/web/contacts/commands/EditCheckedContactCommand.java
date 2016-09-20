package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.help.RequestParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 20.09.2016.
 */
public class EditCheckedContactCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ArrayList<Integer> checkedIds = new RequestParser(request).getCheckedIdList("contact-check");
        if (!checkedIds.isEmpty()) {
            int checkedContactId = checkedIds.get(0);
            request.setAttribute("id", checkedContactId);
            response.sendRedirect("/app/edit-contact?id=" + checkedContactId);
        } else {
            response.sendRedirect("/app/contact-list");
        }
        return null;
    }
}
