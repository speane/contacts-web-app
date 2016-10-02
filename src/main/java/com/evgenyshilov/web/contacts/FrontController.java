package com.evgenyshilov.web.contacts;

import com.evgenyshilov.web.contacts.commands.Command;
import com.evgenyshilov.web.contacts.commands.CommandFactory;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.LogHelper;
import com.evgenyshilov.web.contacts.resources.ApplicationConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//import org.apache.logging.log4j.LogManager;

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
            LogHelper.error("Unable to init front controller servlet: ", e);
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
        String viewPageURL = null;
        try {
            Command command = getCommand(request);
            if (command != null) {
                viewPageURL = command.execute(request, response);
            } else {
                viewPageURL = getNotFoundViewPage(response);
            }
        } catch (Exception e) {
            LogHelper.error("Application error: ", e);
            viewPageURL = "/error.jsp";
        }
        if (viewPageURL != null) {
            LogHelper.info(String.format("Redirecting to %s", viewPageURL));
            request.getRequestDispatcher(viewPageURL).forward(request, response);
        }
    }

    private String getNotFoundViewPage(HttpServletResponse response) {
        int FILE_NOT_FOUND_STATUS_CODE = 404;
        String NOT_FOUND_VIEW_PAGE = "/notfound.jsp";

        response.setStatus(FILE_NOT_FOUND_STATUS_CODE);
        return NOT_FOUND_VIEW_PAGE;
    }

    private Command getCommand(HttpServletRequest request) throws CustomException {
        String commandURI = request.getPathInfo();
        try {
            return commandFactory.create(commandURI);
        } catch (CustomException e) {
            throw new CustomException("Cannot create command instance ", e);
        }
    }

}
