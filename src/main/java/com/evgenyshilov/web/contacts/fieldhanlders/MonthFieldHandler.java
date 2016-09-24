package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Contact;
import org.joda.time.DateTime;

import java.sql.Date;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class MonthFieldHandler implements FieldHandler {
    @Override
    public void setField(Contact contact, String value) {
        if (contact != null) {
            if (contact.getBirthday() == null) {
                contact.setBirthday(new Date(0));
            }
            contact.setBirthday(setDateMonth(contact.getBirthday(), Integer.parseInt(value)));
        }
    }

    private Date setDateMonth(Date date, int month) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.withMonthOfYear(month);
        return new Date(dateTime.getMillis());
    }
}
