package com.anglicisme.gregoryfournier.anglicismes.Data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gregoryfournier on 2017-07-15.
 */

public class DataHolder {
    public static JSONObject wordsJson;

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
}
