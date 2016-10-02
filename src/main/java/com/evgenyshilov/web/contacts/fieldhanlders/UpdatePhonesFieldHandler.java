package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.database.model.Phone;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.database.DBHelper;
import com.evgenyshilov.web.contacts.help.utils.JSONObjectFactory;

import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 25.09.2016.
 */
public class UpdatePhonesFieldHandler implements FieldHandler {
    @Override
    public void handleField(Contact contact, String value) throws CustomException {
        DBHelper dbHelper = new DBHelper();
        ArrayList<Phone> updatedPhones = null;
        try {
            updatedPhones = new JSONObjectFactory().getPhoneList(value);
            for (Phone phone : updatedPhones) {
                phone.setContactId(contact.getId());
                dbHelper.updatePhone(phone.getId(), phone);
            }
        } catch (CustomException e) {
            throw new CustomException("Can't handle updated phones: ", e);
        }
    }
}
