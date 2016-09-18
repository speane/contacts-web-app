package com.evgenyshilov.web.contacts.database.model;

import java.io.Serializable;

/**
 * Created by Evgeny Shilov on 18.09.2016.
 */
public class Phone implements Serializable {

    private int countryCode;
    private int operatorCode;
    private int number;

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public int getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(int operatorCode) {
        this.operatorCode = operatorCode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
