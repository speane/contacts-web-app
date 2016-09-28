package com.evgenyshilov.web.contacts.help;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by Evgeny Shilov on 11.09.2016.
 */
public class PropertyFileParser {

    public HashMap<String, String> parse(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        HashMap<String, String> resultPairs = new HashMap<>();

        String SEVERAL_WHITESPACE_REGEX = "\\s+";
        String tempLine;
        while ((tempLine = reader.readLine()) != null) {
            String[] pair = tempLine.split(SEVERAL_WHITESPACE_REGEX);
            if (pair.length >= 2) {
                resultPairs.put(pair[0], pair[1]);
            } else {

            }
        }

        return resultPairs;
    }
}
