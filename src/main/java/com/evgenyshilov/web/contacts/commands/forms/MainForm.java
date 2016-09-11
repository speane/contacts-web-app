package com.evgenyshilov.web.contacts.commands.forms;

import com.evgenyshilov.web.contacts.commands.Command;
import com.evgenyshilov.web.contacts.resources.ApplicationResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Evgeny Shilov on 11.09.2016.
 */
public class MainForm implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return ApplicationResources.MAIN_FORM_URL;
    }
}
