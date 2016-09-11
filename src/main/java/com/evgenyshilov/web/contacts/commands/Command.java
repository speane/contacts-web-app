package com.evgenyshilov.web.contacts.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Evgeny Shilov on 11.09.2016.
 */
public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse response);
}
