package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.Contact;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * Created by Evgeny Shilov on 12.09.2016.
 */
public class DAOFactory {

    private static HashMap<Class<?>, Class<? extends GenericDAO>> DAOClassMap;
    private DataSource dataSource;

    public DAOFactory(DataSource dataSource) {
        this.dataSource = dataSource;
        DAOClassMapInit();
    }

    private void DAOClassMapInit() {
        DAOClassMap = new HashMap<>();
        DAOClassMap.put(Contact.class, ContactDAO.class);
    }

    public static GenericDAO getDAO(Class elementClass) throws IllegalAccessException,
            InstantiationException {
        return DAOClassMap.get(elementClass).newInstance();
    }
}
