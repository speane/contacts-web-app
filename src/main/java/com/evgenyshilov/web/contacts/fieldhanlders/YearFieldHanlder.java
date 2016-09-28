package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Contact;
import org.joda.time.DateTime;

import java.sql.Date;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class YearFieldHanlder implements FieldHandler {
    @Override
    public void handleField(Contact contact, String value) {
        if (contact != null) {
            if (contact.getBirthday() == null) {
                contact.setBirthday(new Date(0));
            }
            contact.setBirthday(setDateYear(contact.getBirthday(), Integer.parseInt(value)));
        }
    }

    private Date setDateYear(Date date, int year) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.withYear(year);
        return new Date(dateTime.getMillis());
    }
}
