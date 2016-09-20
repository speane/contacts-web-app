package com.evgenyshilov.web.contacts.database.model;

import java.io.Serializable;

/**
 * Created by Evgeny Shilov on 21.09.2016.
 */
public class MaritalStatus implements Serializable {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
