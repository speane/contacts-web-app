package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Contact;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class FlatFieldHandler implements FieldHandler {
    @Override
    public void handleField(Contact contact, String value) {
        if (contact != null) {
            contact.setFlat(value);
        }
    }
}
