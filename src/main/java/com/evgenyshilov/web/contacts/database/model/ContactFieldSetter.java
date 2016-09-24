package com.evgenyshilov.web.contacts.database.model;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.sql.Date;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class ContactFieldSetter {

    private Contact contact;

    public ContactFieldSetter() {
        contact = new Contact();
    }

    public void setField(String fieldName, String value) {
        switch (fieldName) {
            case "first-name":
                contact.setFirstName(value);
                break;
            case "last-name":
                contact.setLastName(value);
                break;
            case "patronymic":
                contact.setPatronymic(value);
                break;
            case "day":
                if (contact.getBirthday() == null) {
                    contact.setBirthday(new Date(new DateTime().toDate().getTime()));
                }
                contact.setBirthday(new Date(LocalDate.fromDateFields(
                        contact.getBirthday()).withDayOfMonth(Integer.parseInt(value)).toDate().getTime()));
                break;
            case "month":
                if (contact.getBirthday() == null) {
                    contact.setBirthday(new Date(new DateTime().toDate().getTime()));
                }
                contact.setBirthday(new Date(LocalDate.fromDateFields(
                        contact.getBirthday()).withMonthOfYear(Integer.parseInt(value)).toDate().getTime()));
                break;
            case "year":
                if (contact.getBirthday() == null) {
                    contact.setBirthday(new Date(new DateTime().toDate().getTime()));
                }
                contact.setBirthday(new Date(LocalDate.fromDateFields(
                        contact.getBirthday()).withYear(Integer.parseInt(value)).toDate().getTime()));
                break;
            case "marital-status":
                contact.setMaritalStatus(value);
                break;
            case "website":
                contact.setWebsite(value);
                break;
            case "job":
                contact.setJob(value);
                break;
            case "state":
                contact.setState(value);
                break;
            case "city":
                contact.setCity(value);
                break;
            case "house":
                contact.setHouse(value);
                break;
            case "flat":
                contact.setFlat(value);
                break;
            case "zipcode":
                contact.setZipCode(value);
                break;
            case "created-phones":

                break;
            case "created-attachments":

                break;
            case "removed-phones":

                break;
            case "removed-attachments":

                break;
            case "updated-phones":

                break;
            case "updated-attachments":

                break;
        }
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
