package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Contact;
import org.joda.time.DateTime;

import java.sql.Date;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class DayFieldHandler implements FieldHandler {
    @Override
    public void setField(Contact contact, String value) {
        if (contact != null) {
            if (contact.getBirthday() == null) {
                contact.setBirthday(new Date(0));
            }
            contact.setBirthday(setDateDay(contact.getBirthday(), Integer.parseInt(value)));
        }
    }

    private Date setDateDay(Date date, int day) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.withDayOfMonth(day);
        return new Date(dateTime.getMillis());
    }
}
