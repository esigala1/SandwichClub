package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    /** Tag for the log messages. */
    private static final String LOG_TAG = "DEBUGGING " + DetailActivity.class.getSimpleName();

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mOriginTextView;
    private TextView mDescriptionTextView;
    private TextView mIngredientsTextView;
    private TextView mAlsoKnownTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mOriginTextView = findViewById(R.id.origin_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        mAlsoKnownTextView = findViewById(R.id.also_known_tv);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            Log.e(LOG_TAG, "The object \"intent\" is null.");
            closeOnError();
            return;
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);

        if (position == DEFAULT_POSITION) {
            Log.e(LOG_TAG, "The key \"extra_position\" not found in intent.");
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException ex) {
            Log.e(LOG_TAG, "Exception caught while parsing the JSON object: " + ex);
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        if (sandwich == null) {
            Log.e(LOG_TAG, "The object \"sandwich\" is null.");
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.ic_sandwich)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Update the screen to display information from the given {@link Sandwich}.
     */
    private void populateUI(Sandwich sandwich) {
        /* If the object "sandwich" is null, then display a Toast message and return early */
        if (sandwich == null){
            Log.e(LOG_TAG, "The object \"sandwich\" is null.");
            Toast.makeText(this, R.string.populate_ui_error_message, Toast.LENGTH_SHORT).show();
            return;
        }
        /* Populate the UI */
        mOriginTextView.setText(sandwich.getPlaceOfOrigin());
        mDescriptionTextView.setText(sandwich.getDescription());
        mIngredientsTextView.setText(convertListToString(sandwich.getIngredients()));
        mAlsoKnownTextView.setText(convertListToString(sandwich.getAlsoKnownAs()));
    }

    /**
     * Method to convert a list of Strings to a single String, separating the words with a comma.
     *
     * @param listOfStrings the list of Strings to convert to a single String.
     * @return a String, containing a list of Strings separated by commas.
     */
    private String convertListToString(List<String> listOfStrings){
        StringBuilder sb = new StringBuilder();
        /* Do not append the comma in the first String */
        String prefix = "";
        for (String str : listOfStrings) {
            /* Append the prefix */
            sb.append(prefix);
            /* Set the prefix */
            prefix = ", ";
            /* Append the String */
            sb.append(str);
        }
        /* If the StringBuilder contains at least one letter then append a period at the end */
        if (sb.length() > 0) {
            sb.append(".");
        }
        return sb.toString();
    }
}
