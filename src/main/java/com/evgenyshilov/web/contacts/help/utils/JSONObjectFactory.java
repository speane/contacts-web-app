package com.evgenyshilov.web.contacts.help.utils;

import com.evgenyshilov.web.contacts.database.model.Attachment;
import com.evgenyshilov.web.contacts.database.model.Phone;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
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
    public ArrayList<Attachment> getAttachmentList(String JSONAttachmentListString) throws CustomException {
        ArrayList<Attachment> attachments = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            JSONArray array = (JSONArray) parser.parse(JSONAttachmentListString);
            for (JSONObject object : (Iterable<JSONObject>) array) {
                Attachment attachment = new Attachment();
                attachment.setId(Long.parseLong(object.get("id").toString()));
                attachment.setFilename((String) object.get("filename"));
                attachment.setCommentary((String) object.get("commentary"));
                attachment.setUploadDate(new Date(DateTime.now().getMillis()));
                attachments.add(attachment);
            }
        } catch (ParseException e) {
            throw new CustomException("Can't get attachments list from json: ", e);
        }
        return attachments;
    }

    public ArrayList<Phone> getPhoneList(String JSONPhoneListString) throws CustomException {
        ArrayList<Phone> phones = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            JSONArray array = (JSONArray) parser.parse(JSONPhoneListString);
            for (JSONObject object : (Iterable<JSONObject>) array) {
                Phone phone = new Phone();
                phone.setId(Long.parseLong(object.get("id").toString()));
                phone.setCountryCode((String) object.get("countryCode"));
                phone.setOperatorCode((String) object.get("operatorCode"));
                phone.setNumber((String) object.get("number"));
                phone.setCommentary((String) object.get("commentary"));
                phone.setType((String) object.get("type"));
                phones.add(phone);
            }
        } catch (ParseException e) {
            throw new CustomException("Can't get phone list from json: ", e);
        }
        return phones;
    }

    public ArrayList<Integer> getIntegerList(String JSONIntegerListString) throws CustomException {
        ArrayList<Integer> list = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            JSONArray array = (JSONArray) parser.parse(JSONIntegerListString);
            for (Object number : array) {
                list.add(Integer.parseInt((String) number));
            }

            return list;
        } catch (ParseException e) {
            throw new CustomException("Can't get integer list from json: ", e);
        }
    }
}
