package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
            closeOnError();
        }

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView tvDescription = findViewById(R.id.description_tv);
        TextView tvPlaceOfOrigin = findViewById(R.id.origin_tv);
        TextView tvAlsoKnownAs = findViewById(R.id.also_known_tv);
        TextView tvIngredients = findViewById(R.id.ingredients_tv);

        tvDescription.setText(sandwich.getDescription());
        tvPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin() + "\n");


        StringBuilder knownAsBuilder = new StringBuilder();
        List<String> knownAs = sandwich.getAlsoKnownAs();
        for (int i = 0; i < knownAs.size(); i++) {
            String name = knownAs.get(i);

            knownAsBuilder.append(" - ")
                    .append(name);
            if (i < knownAs.size() - 1)
                knownAsBuilder.append("\n");
        }
        tvAlsoKnownAs.setText(knownAsBuilder.toString());

        StringBuilder ingredientsBuilder = new StringBuilder();
        List<String> ingredients = sandwich.getIngredients();
        for (int i = 0; i < ingredients.size(); i++) {
            String ingredient = ingredients.get(i);

            ingredientsBuilder.append(" + ")
                    .append(ingredient);
            if (i < ingredients.size() - 1)
                ingredientsBuilder.append("\n");
        }
        tvIngredients.setText(ingredientsBuilder.toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
