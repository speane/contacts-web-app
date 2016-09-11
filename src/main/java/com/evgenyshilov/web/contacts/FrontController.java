package com.evgenyshilov.web.contacts;

import com.evgenyshilov.web.contacts.resources.ApplicationResources;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Evgeny Shilov on 11.09.2016.
 */

public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LogManager.getLogger(FrontController.class).info("0000 " + request.getRequestURI() + " "
                + request.getAttribute(ApplicationResources.PRIMARY_URI_ATTRIBUTE_NAME));
    }
}
