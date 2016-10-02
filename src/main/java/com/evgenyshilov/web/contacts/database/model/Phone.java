package com.evgenyshilov.web.contacts.database.model;

import java.io.Serializable;

/**
 * Created by Evgeny Shilov on 18.09.2016.
 */
public class Phone implements Serializable {

    private Long id;
    private String countryCode;
    private String operatorCode;
    private String number;
    private String type;
    private String commentary;
    private Long contactId;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    @Override
    public String toString() {
        return String.format("+%s(%s)%s commentary: %s", countryCode, operatorCode, number, commentary);
    }
}
