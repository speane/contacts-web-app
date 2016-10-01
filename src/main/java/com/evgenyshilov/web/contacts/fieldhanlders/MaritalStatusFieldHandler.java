package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class MaritalStatusFieldHandler implements FieldHandler {
    @Override
    public void handleField(Contact contact, String value) throws CustomException {
        try {
            long maritalStatus = Long.parseLong(value);
            if (maritalStatus != 0) {
                contact.setMaritalStatus(maritalStatus);
            }
            else {
                contact.setMaritalStatus(null);
            }
        } catch (NumberFormatException e) {
            throw new CustomException("Can't handle marital status field: ", e);
        }
    }
}
