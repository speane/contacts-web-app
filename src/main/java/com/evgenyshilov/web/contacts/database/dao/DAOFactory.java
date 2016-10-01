package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.database.model.*;
import com.evgenyshilov.web.contacts.exceptions.CustomException;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Evgeny Shilov on 12.09.2016.
 */
public class DAOFactory {

    private static HashMap<Class<?>, Class<? extends BaseDAO>> DAOClassMap;
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
        DAOClassMap.put(PhoneType.class, PhoneTypeDAO.class);
    }

    public static BaseDAO getDAO(Class elementClass) throws CustomException {
        try {
            return DAOClassMap.get(elementClass).getConstructor(Connection.class).newInstance(
                    dataSource.getConnection());
        } catch (InstantiationException e) {
            throw new CustomException("Can't create DAO object: ", e);
        } catch (IllegalAccessException e) {
            throw new CustomException("Can't access DAO constructor: ", e);
        } catch (InvocationTargetException e) {
            throw new CustomException("Can't invoke DAO constructor: ", e);
        } catch (NoSuchMethodException e) {
            throw new CustomException("Can't find constructor: ", e);
        } catch (SQLException e) {
            throw new CustomException("Can't get connection to create DAO: ", e);
        }
    }
}