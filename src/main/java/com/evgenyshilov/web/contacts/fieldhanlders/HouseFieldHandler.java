package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.BadInputException;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.validation.Validator;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class HouseFieldHandler implements FieldHandler {
    @Override
    public void handleField(Contact contact, String value) throws CustomException, BadInputException {
        Validator validator = new Validator();
        if (!StringUtils.isEmpty(value)) {
            if (validator.containsOnlyLettersDigitsAndSymbols(value, "-")) {
                contact.setHouse(value);
            } else {
                throw new BadInputException("House value is not valid");
            }
        } else {
            contact.setHouse(null);
        }
    }
}
