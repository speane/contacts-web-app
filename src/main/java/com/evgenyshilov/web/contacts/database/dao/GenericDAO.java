package com.evgenyshilov.web.contacts.database.dao;

import java.sql.Connection;
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

    public abstract ArrayList<T> getAll();
    public abstract T get(PK key);
    public abstract void update(PK key, T value);
    public abstract void delete(PK key);
    public abstract void insert(T value);
}
