package com.evgenyshilov.web.contacts;

import com.evgenyshilov.web.contacts.commands.Command;
import com.evgenyshilov.web.contacts.commands.CommandFactory;
import com.evgenyshilov.web.contacts.database.dao.DAOFactory;
import com.evgenyshilov.web.contacts.resources.ApplicationResources;
import com.evgenyshilov.web.contacts.resources.Messages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * Created by Evgeny Shilov on 11.09.2016.
 */

public class FrontController extends HttpServlet {

    private CommandFactory commandFactory;
    private Logger logger;

    @Override
    public void init() throws ServletException {
        logger = LogManager.getRootLogger();
        commandFactory = new CommandFactory();
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource =
                    (DataSource) initialContext.lookup(ApplicationResources.CONNECTION_POOL_DATA_SOURCE_URL);
            DAOFactory.init(dataSource);

            commandFactory.init(getServletContext().getRealPath(ApplicationResources.COMMAND_PROPERTY_FILE_NAME));
        } catch (IOException | ClassNotFoundException | NamingException e) {
            logger.error(e.getMessage());
            throw new ServletException(e.getMessage());
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
            String URI = (String) request.getAttribute(ApplicationResources.PRIMARY_URI_ATTRIBUTE_NAME);
            Command command = commandFactory.create(URI);
            if (command != null) {
                viewPageURL = command.execute(request, response);
            } else {
                response.setStatus(ApplicationResources.FILE_NOT_FOUND_STATUS_CODE);
                viewPageURL = ApplicationResources.ERROR_PAGE_URL;
                request.setAttribute(ApplicationResources.ERROR_MESSAGE_ATTRIBUTE_NAME,
                        Messages.FILE_NOT_FOUND_MESSAGE);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            request.setAttribute(ApplicationResources.ERROR_MESSAGE_ATTRIBUTE_NAME, Messages.SERVER_ERROR_MESSAGE);
            viewPageURL = ApplicationResources.ERROR_PAGE_URL;
        }
        request.getRequestDispatcher(viewPageURL).forward(request, response);
        /*
        try {

            Connection conn = ds.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT contactID, name, company FROM contact");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("contactID") + resultSet.getString("company") +
                resultSet.getString("name"));
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
        /*InitialContext initContext= null;
        try {
            initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/TestDB");
            ContactDAO contactDAO;
            if ((contactDAO = (ContactDAO) DAOFactory.getDAO(Contact.class)) != null) {
                contactDAO.delete(11);
            }

        } catch (NamingException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }*/
    }

}
