package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.Address;
import com.evgenyshilov.web.contacts.database.model.Contact;

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
        DAOClassMap.put(Address.class, AddressDAO.class);
    }

    public static GenericDAO getDAO(Class elementClass) throws IllegalAccessException,
            InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException {
        return DAOClassMap.get(elementClass).getConstructor(Connection.class).newInstance(
                dataSource.getConnection());
    }
}