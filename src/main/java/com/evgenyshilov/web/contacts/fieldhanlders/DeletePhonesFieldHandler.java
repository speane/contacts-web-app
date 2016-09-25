package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.dao.DAOFactory;
import com.evgenyshilov.web.contacts.database.dao.PhoneDAO;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.database.model.Phone;
import com.evgenyshilov.web.contacts.help.JSONObjectFactory;
import org.json.simple.parser.ParseException;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 25.09.2016.
 */
public class DeletePhonesFieldHandler implements FieldHandler {
    @Override
    public void setField(Contact contact, String value) throws ParseException, SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ArrayList<Integer> removedPhones = new JSONObjectFactory().getIntegerList(value);
        PhoneDAO phoneDAO = (PhoneDAO) DAOFactory.getDAO(Phone.class);
        for (int id : removedPhones) {
            phoneDAO.delete(id);
        }
        phoneDAO.close();
    }
}
