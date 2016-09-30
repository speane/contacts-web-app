package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.database.model.Phone;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.JSONObjectFactory;

import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class CreatedPhonesFieldHandler implements FieldHandler {
    @Override
    public void handleField(Contact contact, String value) throws CustomException {
        ArrayList<Phone> phones = null;
        try {
            phones = new JSONObjectFactory().getPhoneList(value);
            contact.setPhones(phones);
        } catch (CustomException e) {
            throw new CustomException("Can't handle created phones: ", e);
        }
    }
}
