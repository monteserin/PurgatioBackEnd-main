package com.purgatio.utils;

import java.util.Arrays;
import java.util.List;

public class WordsFilter {

    private static final List<String> bannedWords = Arrays.asList("nazi", "violar");
    
    public static boolean containsBannedWords(String text) {
        for (String word : bannedWords) {
            if (text.toLowerCase().contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
