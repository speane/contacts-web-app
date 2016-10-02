package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Attachment;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.database.DBHelper;
import com.evgenyshilov.web.contacts.help.utils.JSONObjectFactory;

import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 25.09.2016.
 */
public class UpdateAttachmentsFieldHandler implements FieldHandler {
    @Override
    public void handleField(Contact contact, String value) throws CustomException {
        DBHelper dbHelper = new DBHelper();
        ArrayList<Attachment> updatedAttachments = null;
        try {
            updatedAttachments = new JSONObjectFactory().getAttachmentList(value);
            for (Attachment attachment : updatedAttachments) {
                dbHelper.updateAttachment(attachment.getId(), attachment);
            }
        } catch (CustomException e) {
            throw new CustomException("Can't handle updated attachments: ", e);
        }
    }
}
