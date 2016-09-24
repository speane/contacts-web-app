package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Contact;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class MaritalStatusFieldHandler implements FieldHandler {
    @Override
    public void setField(Contact contact, String value) {
        contact.setMaritalStatus(value);
    }
}
