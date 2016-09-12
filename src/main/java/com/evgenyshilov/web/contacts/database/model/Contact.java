package com.evgenyshilov.web.contacts.database.model;

import java.io.Serializable;

/**
 * Created by Evgeny Shilov on 12.09.2016.
 */
public class Contact implements Serializable {

    public int id;
    public String name;
    public String company;

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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
