package com.evgenyshilov.web.contacts.help;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Evgeny Shilov on 30.09.2016.
 */
public class RussianEnglishTranslator {
    private static BidiMap<String, String> enToRuDictionary = new DualHashBidiMap<>();

    public static void loadDictionary(HashMap<String, String> dictionary) {
        RussianEnglishTranslator.enToRuDictionary = new DualHashBidiMap<>(dictionary);
    }

    public String getRussian(String enWord) {
        return enToRuDictionary.get(enWord);
    }

    public String getEnglish(String ruWord) {
        return enToRuDictionary.getKey(ruWord);
    }

    public ArrayList<String> getRussianList(ArrayList<String> enWords) {
        ArrayList<String> ruWords = new ArrayList<>();
        for (String word : enWords) {
            ruWords.add(getRussian(word));
        }
        return ruWords;
    }
}
