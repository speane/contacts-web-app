package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.help.JSONObjectFactory;
import org.json.simple.parser.ParseException;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * Created by Evgeny Shilov on 25.09.2016.
 */
public class DeletePhonesFieldHandler implements FieldHandler {
    @Override
    public void setField(Contact contact, String value) throws ParseException, SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        System.out.println("LIST: " + new JSONObjectFactory().getIntegerList(value));
    }
}
