package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.sql.Date;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class YearFieldHandler implements FieldHandler {
    @Override
    public void handleField(Contact contact, String value) throws CustomException {
        if (!StringUtils.isEmpty(value)) {
            try {
                int year = Integer.parseInt(value);
                if (contact.getBirthday() == null) {
                    contact.setBirthday(new Date(0));
                }
                contact.setBirthday(setDateYear(contact.getBirthday(), year));
            } catch (NumberFormatException e) {
                throw new CustomException("Can't handle year field: ", e);
            }
        } else {
            contact.setBirthday(null);
        }
    }

    private Date setDateYear(Date date, int year) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.withYear(year);
        return new Date(dateTime.getMillis());
    }
}
