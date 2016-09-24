package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Contact;
import org.json.simple.parser.ParseException;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public interface FieldHandler {

    void setField(Contact contact, String value) throws ParseException;
}
