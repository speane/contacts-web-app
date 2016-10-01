package com.evgenyshilov.web.contacts.help;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.resources.RussianEnglishTranslator;
import org.stringtemplate.v4.ST;

import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 29.09.2016.
 */
public class EmailTemplateElementsFactory {
    public final static String FIRST_NAME = "first_name";
    public final static String LAST_NAME = "last_name";
    public final static String PATRONYMIC = "patronymic";

    public ArrayList<String> getElements() {
        ArrayList<String> elements = new ArrayList<>();

        elements.add(FIRST_NAME);
        elements.add(LAST_NAME);
        elements.add(PATRONYMIC);

        return elements;
    }

    public String getTranslatedTemplateString(ST template) {
        for (String templateElement : getElements()) {
            try {
                template.add(templateElement, "<" + new RussianEnglishTranslator().getRussian(templateElement) + ">");
            } catch (IllegalArgumentException e) {}
        }
        return template.render();
    }

    public String buildTemplateWithContactFields(ST template, Contact contact) {
        setAttribute(template, FIRST_NAME, contact.getFirstName());
        setAttribute(template, LAST_NAME, contact.getLastName());
        setAttribute(template, PATRONYMIC, contact.getPatronymic());

        return template.render();
    }

    private void setAttribute(ST template, String fieldName, String value) {
        try {
            template.add(fieldName, value);
        } catch (IllegalArgumentException e) {}
    }
}
