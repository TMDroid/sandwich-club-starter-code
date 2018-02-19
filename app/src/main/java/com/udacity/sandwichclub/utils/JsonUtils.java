package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static final String KEY_NAME = "name";
    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_KNOWN_AS = "alsoKnownAs";
    public static final String KEY_PLACE_ORIGIN = "placeOfOrigin";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject o = new JSONObject(json);

        JSONObject name = o.getJSONObject(KEY_NAME);

        String mainName = name.getString(KEY_MAIN_NAME);

        List<String> alsoKnownAs = new ArrayList<>();
        JSONArray knownAsNames = name.getJSONArray(KEY_KNOWN_AS);
        for(int i = 0; i < knownAsNames.length(); i++) {
            String someName = knownAsNames.getString(i);

            alsoKnownAs.add(someName);
        }

        String origin = o.getString(KEY_PLACE_ORIGIN);

        String description = o.getString(KEY_DESCRIPTION);

        String image = o.getString(KEY_IMAGE);

        List<String> ingredients = new ArrayList<>();
        JSONArray ingredientsArray = o.getJSONArray(KEY_INGREDIENTS);
        for(int i = 0; i < ingredientsArray.length(); i++) {
            String ingredient = ingredientsArray.getString(i);

            ingredients.add(ingredient);
        }

        return new Sandwich(mainName, alsoKnownAs, origin, description, image, ingredients);
    }
}
