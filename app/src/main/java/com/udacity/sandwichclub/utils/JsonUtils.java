package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        /* Get the JSON object representing the parent node */
        JSONObject sandwichJson = new JSONObject(json);

        final String SANDWICH_NAME = "name";
        final String SANDWICH_NAME_MAIN = "mainName";
        final String SANDWICH_NAME_KNOWN = "alsoKnownAs";
        final String SANDWICH_ORIGIN = "placeOfOrigin";
        final String SANDWICH_DESCRIPTION = "description";
        final String SANDWICH_IMAGE = "image";
        final String SANDWICH_INGREDIENTS = "ingredients";

        /* If the json key "name" exists, then do the following... */
        if (sandwichJson.has(SANDWICH_NAME)) {

            String mainName;
            List<String> alsoKnownAs = new ArrayList<>();
            String placeOfOrigin;
            String description;
            String image;
            List<String> ingredients = new ArrayList<>();

            /* Get the JSON object representing the node "name" */
            JSONObject nodeName = sandwichJson.getJSONObject(SANDWICH_NAME);

            /* Extract out the main name of this sandwich */
            mainName = nodeName.optString(SANDWICH_NAME_MAIN);

            /* Extract out an array containing other names regarding this sandwich */
            JSONArray otherNameArray = nodeName.optJSONArray(SANDWICH_NAME_KNOWN);

            for (int i = 0; i < otherNameArray.length(); i++) {
                /* Add the alternative name in the list of Strings */
                alsoKnownAs.add(otherNameArray.getString(i));
            }

            /* Extract out the place of origin */
            placeOfOrigin = sandwichJson.optString(SANDWICH_ORIGIN);

            /* Extract out the description */
            description = sandwichJson.optString(SANDWICH_DESCRIPTION);

            /* Extract out the image */
            image = sandwichJson.optString(SANDWICH_IMAGE);

            /* Extract out an array containing the ingredients of this sandwich */
            JSONArray ingredientArray = sandwichJson.optJSONArray(SANDWICH_INGREDIENTS);

            for (int i = 0; i < ingredientArray.length(); i++) {
                /* Add the ingredient in the list of Strings */
                ingredients.add(ingredientArray.getString(i));
            }

            /* Create a new {@link Sandwich} object */
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        }

        /* The json key "name" does not exist, so return null */
        return null;
    }
}