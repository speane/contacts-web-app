package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.model.Attachment;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.JSONObjectFactory;

import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class CreatedAttachmentsFieldHandler implements FieldHandler {
    @Override
    public void handleField(Contact contact, String value) throws CustomException {
        ArrayList<Attachment> createdAttachments = null;
        try {
            createdAttachments = new JSONObjectFactory().getAttachmentList(value);
            contact.setAttachments(createdAttachments);
        } catch (CustomException e) {
            throw new CustomException("Can't handle created attachments request field: ", e);
        }
    }
}
