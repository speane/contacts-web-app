package com.evgenyshilov.web.contacts.fieldhanlders;

import com.evgenyshilov.web.contacts.database.dao.AttachmentDAO;
import com.evgenyshilov.web.contacts.database.dao.DAOFactory;
import com.evgenyshilov.web.contacts.database.model.Attachment;
import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.help.JSONObjectFactory;
import org.json.simple.parser.ParseException;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 25.09.2016.
 */
public class UpdateAttachmentsFieldHandler implements FieldHandler {
    @Override
    public void setField(Contact contact, String value) throws ParseException, SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ArrayList<Attachment> updatedAttachments = new JSONObjectFactory().getAttachmentList(value);
        AttachmentDAO attachmentDAO = (AttachmentDAO) DAOFactory.getDAO(Attachment.class);
        for (Attachment attachment : updatedAttachments) {
            attachment.setContactId(contact.getId());
            attachmentDAO.update(attachment.getId(), attachment);
        }
        attachmentDAO.close();
    }
}
