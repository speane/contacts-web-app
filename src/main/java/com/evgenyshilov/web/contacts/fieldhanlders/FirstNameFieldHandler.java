package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.BadInputException;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.validation.Validator;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class FirstNameFieldHandler implements FieldHandler {
    @Override
    public void handleField(Contact contact, String value) throws BadInputException, CustomException {
        Validator validator = new Validator();
        if (!StringUtils.isEmpty(value) && validator.containsOnlyLetters(value)) {
            contact.setFirstName(value);
        } else {
            throw new BadInputException("First name is not valid");
        }
    }
}
