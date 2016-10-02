package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.database.DBHelper;
import com.evgenyshilov.web.contacts.help.utils.JSONObjectFactory;

import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 25.09.2016.
 */
public class DeleteAttachmentFieldHandler implements FieldHandler {
    @Override
    public void handleField(Contact contact, String value) throws CustomException {
        DBHelper dbHelper = new DBHelper();
        try {
            ArrayList<Integer> removedAttachments = new JSONObjectFactory().getIntegerList(value);
            for (int id : removedAttachments) {
                dbHelper.removeAttachment(id);
            }
        } catch (CustomException e) {
            throw new CustomException("Can't handle removed attachments: ", e);
        }
    }
}
