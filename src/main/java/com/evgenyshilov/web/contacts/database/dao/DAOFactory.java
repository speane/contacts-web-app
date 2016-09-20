package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.Attachment;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.database.model.MaritalStatus;
import com.evgenyshilov.web.contacts.database.model.Phone;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Evgeny Shilov on 12.09.2016.
 */
public class DAOFactory {

    private static HashMap<Class<?>, Class<? extends GenericDAO>> DAOClassMap;
    private static DataSource dataSource;

    public static void init(DataSource dataSource) {
        DAOFactory.dataSource = dataSource;
        DAOClassMapInit();
    }

    private static void DAOClassMapInit() {
        DAOClassMap = new HashMap<>();
        DAOClassMap.put(Contact.class, ContactDAO.class);
        DAOClassMap.put(Phone.class, PhoneDAO.class);
        DAOClassMap.put(Attachment.class, AttachmentDAO.class);
        DAOClassMap.put(MaritalStatus.class, MaritalStatusDAO.class);
    }

    public static GenericDAO getDAO(Class elementClass) throws IllegalAccessException,
            InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException {
        return DAOClassMap.get(elementClass).getConstructor(Connection.class).newInstance(
                dataSource.getConnection());
    }
}