package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.sql.Date;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class DayFieldHandler implements FieldHandler {
    @Override
    public void handleField(Contact contact, String value) throws CustomException {
        if (contact != null) {
            try {
                if (!StringUtils.isEmpty(value)) {
                    int day = Integer.parseInt(value);
                    if (contact.getBirthday() == null) {
                        contact.setBirthday(new Date(0));
                    }
                    contact.setBirthday(setDateDay(contact.getBirthday(), day));
                } else {
                    contact.setBirthday(null);
                }
            } catch (NumberFormatException e) {
                throw new CustomException("Can't handle day field: ", e);
            }
        }
    }

    private Date setDateDay(Date date, int day) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.withDayOfMonth(day);
        return new Date(dateTime.getMillis());
    }
}
