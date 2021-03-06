package com.evgenyshilov.web.contacts.help.email;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 29.09.2016.
 */
public class EmailTemplateFactory {
    private STGroup groupFile;

    public EmailTemplateFactory(String STGroupFilename) {
        groupFile = new STGroupFile(STGroupFilename);
    }

    public ArrayList<String> getAllEmailTemplates() {
        ArrayList<String> templates = new ArrayList<>();
        EmailTemplateElementsFactory elementsFactory = new EmailTemplateElementsFactory();
        ST tempST;
        int i = 1;
        while ((tempST = groupFile.getInstanceOf("t" + i++)) != null) {
            templates.add(elementsFactory.getRussianTemplateString(tempST));
        }
        return templates;
    }
}
