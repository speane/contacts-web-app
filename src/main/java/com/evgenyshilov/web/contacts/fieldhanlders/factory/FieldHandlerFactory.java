package com.evgenyshilov.web.contacts.fieldhanlders.factory;

import com.evgenyshilov.web.contacts.fieldhanlders.*;

import java.util.HashMap;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class FieldHandlerFactory {
    private HashMap<String, FieldHandler> fieldHandlerMap;

    public FieldHandlerFactory() {
        fieldHandlerMap = new HashMap<>();
        fieldHandlerMap.put("first-name", new FirstNameFieldHandler());
        fieldHandlerMap.put("last-name", new LastNameFieldHandler());
        fieldHandlerMap.put("patronymic", new PatronymicFieldHanlder());
        fieldHandlerMap.put("day", new DayFieldHandler());
        fieldHandlerMap.put("month", new MonthFieldHandler());
        fieldHandlerMap.put("year", new YearFieldHanlder());
        fieldHandlerMap.put("sex", new SexFieldHanlder());
        fieldHandlerMap.put("marital-status", new MaritalStatusFieldHandler());
        fieldHandlerMap.put("nationality", new NationalityFieldHandler());
        fieldHandlerMap.put("website", new WebsiteFieldHandler());
        fieldHandlerMap.put("email", new EmailFieldHandler());
        fieldHandlerMap.put("job", new JobFieldHandler());
        fieldHandlerMap.put("state", new StateFieldHandler());
        fieldHandlerMap.put("city", new CityFieldHandler());
        fieldHandlerMap.put("street", new StreetFieldHandler());
        fieldHandlerMap.put("house", new HouseFieldHandler());
        fieldHandlerMap.put("flat", new FlatFieldHandler());
        fieldHandlerMap.put("zipcode", new ZipCodeFieldHandler());
        fieldHandlerMap.put("created-phones", new CreatedPhonesFieldHandler());
        fieldHandlerMap.put("created-attachments", new CreatedAttachmentsFieldHandler());
        fieldHandlerMap.put("removed-phones", new DeletePhonesFieldHandler());
        fieldHandlerMap.put("removed-attachments", new DeleteAttachmentFieldHandler());
        fieldHandlerMap.put("updated-phones", new UpdatePhonesFieldHandler());
        fieldHandlerMap.put("updated-attachments", new UpdateAttachmentsFieldHandler());
    }

    public FieldHandler getFieldHandler(String fieldName) {
        return fieldHandlerMap.get(fieldName);
    }
}
