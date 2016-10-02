package com.evgenyshilov.web.contacts.help.files;

import com.evgenyshilov.web.contacts.exceptions.CustomException;

import java.io.*;
import java.util.HashMap;

/**
 * Created by Evgeny Shilov on 11.09.2016.
 */
public class PropertyFileParser {

    public HashMap<String, String> parse(String fileName) throws CustomException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            HashMap<String, String> resultPairs = new HashMap<>();

            String SEVERAL_WHITESPACE_REGEX = "\\s+";
            String tempLine;
            while ((tempLine = reader.readLine()) != null) {
                String[] pair = tempLine.split(SEVERAL_WHITESPACE_REGEX);
                if (pair.length >= 2) {
                    resultPairs.put(pair[0], pair[1]);
                }
            }
            return resultPairs;
        } catch (IOException e) {
            throw new CustomException("Can't parse property file: ", e);
        }
    }
}
