package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Evgeny Shilov on 09.10.2016.
 */
public class ImageFilenameFieldHandler implements FieldHandler {
    @Override
    public void handleField(Contact contact, String value) throws CustomException {
        if (!StringUtils.isEmpty(value)) {
            contact.setImageFileName(value);
        }
    }
}
