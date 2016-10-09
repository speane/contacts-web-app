package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.BadInputException;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.sql.Date;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class MonthFieldHandler implements FieldHandler {
    @Override
    public void handleField(Contact contact, String value) throws CustomException, BadInputException {
        if (!StringUtils.isEmpty(value)) {
            try {
                int month = Integer.parseInt(value);
                if (month != 0) {
                    if (contact.getBirthday() == null) {
                        contact.setBirthday(new Date(0));
                    }
                    contact.setBirthday(setDateMonth(contact.getBirthday(), month));
                } else {
                    contact.setBirthday(null);
                }
            } catch (NumberFormatException e) {
                throw new CustomException("Can't handle month field: ", e);
            }
        } else {
            throw new BadInputException("Empty month input");
        }
    }

    private Date setDateMonth(Date date, int month) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.withMonthOfYear(month);
        return new Date(dateTime.getMillis());
    }
}
