package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.help.JSONObjectFactory;
import org.json.simple.parser.ParseException;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class CreatedPhonesFieldHandler implements FieldHandler {
    @Override
    public void setField(Contact contact, String value) throws ParseException {
        new JSONObjectFactory().getAttachmentList(value);
    }
}
