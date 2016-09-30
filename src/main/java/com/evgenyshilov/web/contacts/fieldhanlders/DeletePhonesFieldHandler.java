package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.DBHelper;
import com.evgenyshilov.web.contacts.help.JSONObjectFactory;

import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 25.09.2016.
 */
public class DeletePhonesFieldHandler implements FieldHandler {
    @Override
    public void handleField(Contact contact, String value) throws CustomException {
        DBHelper dbHelper = new DBHelper();
        ArrayList<Integer> removedPhones = null;
        try {
            removedPhones = new JSONObjectFactory().getIntegerList(value);
            for (int id : removedPhones) {
                dbHelper.removePhone(id);
            }
        } catch (CustomException e) {
            throw new CustomException("Can't handle removed phones: ", e);
        }
    }
}
