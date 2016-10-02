package com.evgenyshilov.web.contacts.help.email;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.help.utils.RussianEnglishTranslator;
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

    public String getRussianTemplateString(ST template) {
        for (String templateElement : getElements()) {
            try {
                template.add(templateElement, "<" + new RussianEnglishTranslator().getRussian(templateElement) + ">");
            } catch (IllegalArgumentException e) {}
        }
        return template.render();
    }

    public String translateTemplateToEnglish(String template) {
        RussianEnglishTranslator translator = new RussianEnglishTranslator();
        template = template.replaceAll("<" + translator.getRussian(FIRST_NAME) + ">", "<" + FIRST_NAME + ">");
        template = template.replaceAll("<" + translator.getRussian(LAST_NAME) + ">", "<" + LAST_NAME + ">");
        template = template.replaceAll("<" + translator.getRussian(PATRONYMIC) + ">", "<" + PATRONYMIC + ">");

        return template;
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
