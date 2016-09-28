package com.evgenyshilov.web.contacts;

import com.evgenyshilov.web.contacts.commands.Command;
import com.evgenyshilov.web.contacts.commands.CommandFactory;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.resources.ApplicationConfig;
import com.evgenyshilov.web.contacts.resources.ApplicationResources;
import com.evgenyshilov.web.contacts.resources.Messages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Evgeny Shilov on 11.09.2016.
 */

public class FrontController extends HttpServlet {

    private CommandFactory commandFactory;

    @Override
    public void init() throws ServletException {
        commandFactory = new CommandFactory();
        try {
            String COMMAND_MAPPING_REAL_PATH = getServletContext().getRealPath(
                    ApplicationConfig.getProperty("COMMAND_MAPPING_FILE_PATH"));
            commandFactory.init(COMMAND_MAPPING_REAL_PATH);
        } catch (CustomException e) {
            // TODO log exception
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String viewPageURL;
        try {
            String commandURI = request.getPathInfo();
            Command command = commandFactory.create(commandURI);
            if (command != null) {
                viewPageURL = command.execute(request, response);
            } else {
                response.setStatus(ApplicationResources.FILE_NOT_FOUND_STATUS_CODE);
                viewPageURL = ApplicationResources.ERROR_PAGE_URL;
                request.setAttribute(ApplicationResources.ERROR_MESSAGE_ATTRIBUTE_NAME,
                        Messages.FILE_NOT_FOUND_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(ApplicationResources.ERROR_MESSAGE_ATTRIBUTE_NAME, e.getStackTrace());
            viewPageURL = ApplicationResources.ERROR_PAGE_URL;
        }
        if (viewPageURL != null) {
            request.getRequestDispatcher(viewPageURL).forward(request, response);
        }
    }

}
