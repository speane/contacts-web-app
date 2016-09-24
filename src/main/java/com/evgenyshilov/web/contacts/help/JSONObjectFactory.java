package com.evgenyshilov.web.contacts.help;

import com.evgenyshilov.web.contacts.database.model.Attachment;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class JSONObjectFactory {
    public ArrayList<Attachment> getAttachmentList(String JSONAttachmentListString) throws ParseException {
        /*JSONParser parser = new JSONParser();
        try {
            JSONArray array = (JSONArray) parser.parse(JSONAttachmentListString);
            for (JSONObject object : (Iterable<JSONObject>) array) {
                System.out.println(object.get("filename"));
                System.out.println(object.get("commentary"));
            }
        } finally {
            System.err.println("error");
        }*/
        return null;
    }
}
