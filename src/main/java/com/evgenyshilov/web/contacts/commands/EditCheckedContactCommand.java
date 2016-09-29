package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.RequestParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 20.09.2016.
 */
public class EditCheckedContactCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CustomException {
        String redirectURL;
        ArrayList<Integer> checkedIds = new RequestParser(request).getCheckedIdList("contact-check");
        if (!checkedIds.isEmpty()) {
            int checkedContactId = checkedIds.get(0);
            request.setAttribute("id", checkedContactId);
            redirectURL = "/app/edit-contact?id=" + checkedContactId;
        } else {
            redirectURL = "/app/contact-list";
        }
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            throw new CustomException("Can't redirect to url: ", e);
        }
        return null;
    }
}
