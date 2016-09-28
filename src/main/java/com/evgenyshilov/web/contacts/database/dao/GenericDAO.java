package com.evgenyshilov.web.contacts.database.dao;

import com.evgenyshilov.web.contacts.exceptions.CustomException;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 12.09.2016.
 */
public abstract class GenericDAO<PK, T> {

    protected Connection connection;

    public GenericDAO() {

    }

    public GenericDAO(Connection connection) {
        this.connection = connection;
    }

    public abstract ArrayList<T> getAll() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, CustomException;

    public abstract T get(PK key) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, CustomException;

    public abstract void update(PK key, T value) throws SQLException;

    public abstract void delete(PK key) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    public abstract void insert(T value) throws SQLException;

    public void close() throws SQLException {
        connection.close();
    }
}
