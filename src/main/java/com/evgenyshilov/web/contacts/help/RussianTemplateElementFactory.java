package com.evgenyshilov.web.contacts.help;

import java.util.HashMap;

/**
 * Created by Evgeny Shilov on 30.09.2016.
 */
public class RussianTemplateElementFactory {
    private HashMap<String, String> dictionary = new HashMap<>();

    public RussianTemplateElementFactory() {
        dictionary.put("first_name", "Имя");
    }
}
