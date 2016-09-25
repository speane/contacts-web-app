package com.evgenyshilov.web.contacts.help;

import com.evgenyshilov.web.contacts.database.model.Attachment;
import com.evgenyshilov.web.contacts.database.model.Phone;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 24.09.2016.
 */
public class JSONObjectFactory {
    public ArrayList<Attachment> getAttachmentList(String JSONAttachmentListString) throws ParseException {
        ArrayList<Attachment> attachments = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            JSONArray array = (JSONArray) parser.parse(JSONAttachmentListString);
            for (JSONObject object : (Iterable<JSONObject>) array) {
                Attachment attachment = new Attachment();
                attachment.setId(Integer.parseInt(object.get("id").toString()));
                attachment.setFilename((String) object.get("filename"));
                attachment.setCommentary((String) object.get("commentary"));
                attachment.setUploadDate(new Date(DateTime.now().getMillis()));
                attachments.add(attachment);
            }
        } finally {
            System.err.println("error");
        }
        return attachments;
    }

    public ArrayList<Phone> getPhoneList(String JSONPhoneListString) throws ParseException {
        ArrayList<Phone> phones = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            JSONArray array = (JSONArray) parser.parse(JSONPhoneListString);
            for (JSONObject object : (Iterable<JSONObject>) array) {
                Phone phone = new Phone();
                phone.setId(Integer.parseInt(object.get("id").toString()));
                phone.setCountryCode(Integer.parseInt((String) object.get("countryCode")));
                phone.setOperatorCode(Integer.parseInt((String) object.get("operatorCode")));
                phone.setNumber(Integer.parseInt((String) object.get("number")));
                phone.setCommentary((String) object.get("commentary"));
                phone.setType((String) object.get("type"));
                phones.add(phone);
            }
        } finally {
            System.out.println("finally");
        }
        return phones;
    }

    public ArrayList<Integer> getIntegerList(String JSONIntegerListString) throws ParseException {
        ArrayList<Integer> list = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONArray array = (JSONArray) parser.parse(JSONIntegerListString);
        for (Object number : array) {
            list.add(Integer.parseInt((String) number));
        }

        return list;
    }
}
