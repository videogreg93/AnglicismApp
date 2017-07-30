package com.anglicisme.gregoryfournier.anglicismes.Data;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.paperdb.Paper;

/**
 * Created by gregoryfournier on 2017-07-15.
 */

public class DataHolder {
    public static JSONObject wordsJson;
    private static ArrayList<String> favoriteWords;

    public static String getDefinition(String word) {
        if (wordsJson.has(word)) {
            try {
                return wordsJson.getString(word);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "Word does not exist";

    }

    public static void loadFavoriteWords(Context context) {
        Paper.init(context);
        favoriteWords = Paper.book().read("favoriteWords", new ArrayList<String>());
    }

    public static void saveFavoriteWords() {
        Paper.book().write("favoriteWords", favoriteWords);
    }

    public static void addNewFavoriteWord(String word) {
        favoriteWords.add(word);
        saveFavoriteWords();
    }

    public static void removeFavoriteWord(String currentWord) {
        favoriteWords.remove(currentWord);
        saveFavoriteWords();
    }

    public static ArrayList<String> getFavoriteWords() {
        return favoriteWords;
    }


}
