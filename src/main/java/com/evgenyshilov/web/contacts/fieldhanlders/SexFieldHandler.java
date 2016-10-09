package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Contact;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class SexFieldHandler implements FieldHandler {
    @Override
    public void handleField(Contact contact, String value) {
        String MALE_VALUE = "m";
        String FEMALE_VALUE = "f";
        if (StringUtils.equals(value, MALE_VALUE) || StringUtils.equals(value, FEMALE_VALUE)) {
            contact.setSex(value);
        } else {
            contact.setSex(null);
        }
    }
}
