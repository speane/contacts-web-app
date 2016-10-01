package com.evgenyshilov.web.contacts.help;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;

/**
 * Created by Evgeny Shilov on 01.10.2016.
 */
public class FileNamingUtils {
    public static String getUniqueFilePath(String path) {
        int RANDOM_DIGITS_NUMBER = 10;
        String randomPath = path + RandomStringUtils.randomNumeric(RANDOM_DIGITS_NUMBER);
        while (new File(randomPath).exists()) {
            randomPath = path + RandomStringUtils.randomNumeric(RANDOM_DIGITS_NUMBER);
        }
        return randomPath;
    }
}
