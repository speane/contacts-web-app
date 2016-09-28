package com.evgenyshilov.web.contacts.help;

import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 29.09.2016.
 */
public class EmailTemplateElementsFactory {
    public final static String FIRST_NAME = "";
    public final static String LAST_NAME = "";
    public final static String PATRONYMIC = "";

    public ArrayList<String> getElements() {
        ArrayList<String> elements = new ArrayList<>();

        elements.add(FIRST_NAME);
        elements.add(LAST_NAME);
        elements.add(PATRONYMIC);

        return elements;
    }
}
