package com.example.utsmobile;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DetailRecipesActivity extends AppCompatActivity {
    private ImageView imgRecipe;
    private TextView tvRecipeName, tvRecipeHealthScore, tvRecipeReadyInMinutes, tvRecipeServings, tvInstructions;
    private RecyclerView rvIngredients;
    private IngredientAdapter ingredientsAdapter;
    private ArrayList<String> ingredientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_recipes);

        rvIngredients = findViewById(R.id.detail_rv_ingredients);
        ingredientsList = new ArrayList<>();
        ingredientsAdapter = new IngredientAdapter(ingredientsList);
        rvIngredients.setLayoutManager(new LinearLayoutManager(this));
        rvIngredients.setAdapter(ingredientsAdapter);

        if (getIntent() != null) {
            ArrayList<String> ingredients = getIntent().getStringArrayListExtra("ingredients");
            if (ingredients != null) {
                ingredientsList.addAll(ingredients);
                ingredientsAdapter.notifyDataSetChanged();
            }
        }

        // Inisialisasi komponen UI
        imgRecipe = findViewById(R.id.detail_image);
        tvRecipeName = findViewById(R.id.detail_title);
        tvRecipeHealthScore = findViewById(R.id.detail_cal);
        tvRecipeReadyInMinutes = findViewById(R.id.detail_minutes); // TextView untuk readyInMinutes
        tvRecipeServings = findViewById(R.id.detail_servings); // TextView untuk servings
        tvInstructions = findViewById(R.id.detail_instructions);

        // Mendapatkan data yang dikirim dari ListMakananAdapter
        String recipeName = getIntent().getStringExtra("recipe_name");
        String recipeImageUrl = getIntent().getStringExtra("recipe_image_url");
        int recipeHealthScore = getIntent().getIntExtra("recipe_health_score", 0);  // Mendapatkan healthScore
        int recipeReadyInMinutes = getIntent().getIntExtra("recipe_ready_in_minutes", 0);  // Mendapatkan readyInMinutes
        int recipeServings = getIntent().getIntExtra("recipe_servings", 0);  // Mendapatkan servings
        int updatedHealthScore = recipeHealthScore * 10;
        String recipeInstructions = getIntent().getStringExtra("instructions");
        // Set data ke UI
        tvRecipeName.setText(recipeName);
        tvRecipeHealthScore.setText(updatedHealthScore + " Cal");
        tvRecipeReadyInMinutes.setText(String.valueOf(recipeReadyInMinutes));
        tvRecipeServings.setText(String.valueOf(recipeServings));
        tvInstructions.setText(Html.fromHtml(recipeInstructions, Html.FROM_HTML_MODE_LEGACY));

        // Memuat gambar dengan Glide
        Glide.with(this)
                .load(recipeImageUrl)
                .into(imgRecipe);
    }
}
