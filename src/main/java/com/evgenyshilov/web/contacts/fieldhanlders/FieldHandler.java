package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.BadInputException;
import com.evgenyshilov.web.contacts.exceptions.CustomException;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public interface FieldHandler {

    void handleField(Contact contact, String value) throws CustomException, BadInputException;
}
