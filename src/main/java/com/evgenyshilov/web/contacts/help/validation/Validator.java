package com.evgenyshilov.web.contacts.help.validation;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Evgeny Shilov on 09.10.2016.
 */
public class Validator {
    private static final String DIGITS = "0123456789";
    private static final String EN_ALPHABET_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String  RU_ALPHABET_LOWER = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    public boolean isNumeric(String value) {
        return StringUtils.containsOnly(value, DIGITS);
    }

    public boolean containsOnlyLetters(String value) {
        return StringUtils.containsOnly(value.toLowerCase(), EN_ALPHABET_LOWER + RU_ALPHABET_LOWER);
    }

    public boolean containsOnlyLettersAndSymbols(String value, String symbols) {
        return StringUtils.containsOnly(value.toLowerCase(), RU_ALPHABET_LOWER + EN_ALPHABET_LOWER + symbols);
    }

    public boolean containsOnlyLettersDigitsAndSymbols(String value, String symbols) {
        return StringUtils.containsOnly(value.toLowerCase(), RU_ALPHABET_LOWER + EN_ALPHABET_LOWER + DIGITS + symbols);
    }

    public boolean isEmail(String value) {
        int atSymbolIndex = value.lastIndexOf('@');
        return atSymbolIndex > 0 && atSymbolIndex < (value.length() - 1);
    }

    public boolean isWebsite(String value) {
        int dotSymbolIndex = value.lastIndexOf('.');
        return dotSymbolIndex > 0 && dotSymbolIndex < (value.length() - 1);
    }
}
