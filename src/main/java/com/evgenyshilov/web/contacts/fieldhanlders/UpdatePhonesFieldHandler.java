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
public class UpdatePhonesFieldHandler implements FieldHandler {
    @Override
    public void setField(Contact contact, String value) throws ParseException, SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ArrayList<Phone> updatedPhones = new JSONObjectFactory().getPhoneList(value);
        PhoneDAO phoneDAO = (PhoneDAO) DAOFactory.getDAO(Phone.class);
        for (Phone phone : updatedPhones) {
            phone.setContactId(contact.getId());
            phoneDAO.update(phone.getId(), phone);
        }
        phoneDAO.close();
    }
}
