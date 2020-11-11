package com.example.bakingtime.NetworkUtil;

import android.net.Uri;

import com.example.bakingtime.RecipeClasses.Ingredients;
import com.example.bakingtime.RecipeClasses.Recipe;
import com.example.bakingtime.RecipeClasses.Steps;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NetworkUtil {

    private static final String RECIPE_JSON_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    NetworkUtil() {
    }

    public static URL buildUrl() {
        Uri buildUri = Uri.parse(RECIPE_JSON_URL);
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static List<Recipe> getRecipeData(String jsonResponse) {
        if (jsonResponse.isEmpty())
            return null;

        ArrayList<Recipe> recipes = new ArrayList<>();
        int RecipeId;
        String RecipeName;
        int RecipeServings;
        String RecipeImage;

        try {


            JSONArray array = new JSONArray( jsonResponse);

            for (int i = 0; i < array.length(); i++) {

                List<Ingredients> RecipeIngredients = new ArrayList<>();
                List<Steps> RecipeSteps = new ArrayList<>();
                JSONObject Recipe = array.getJSONObject(i);

                RecipeId = Recipe.getInt("id");
                RecipeName = Recipe.getString("name");
                JSONArray IngredientsArray = Recipe.getJSONArray("ingredients");

                for (int j = 0; j < IngredientsArray.length(); j++) {
                    JSONObject ingredientsArray = IngredientsArray.getJSONObject(j);
                    double quantity = ingredientsArray.getDouble("quantity");
                    String measure = ingredientsArray.getString("measure");
                    String ingredient = ingredientsArray.getString("ingredient");

                    RecipeIngredients.add(new Ingredients(quantity, measure, ingredient));
                }
                JSONArray StepsArray = Recipe.getJSONArray("steps");

                for (int j = 0; j < StepsArray.length(); j++) {

                    JSONObject stepsArray = StepsArray.getJSONObject(j);
                    int id = stepsArray.getInt("id");
                    String shortDescription = stepsArray.getString("shortDescription");
                    String description = stepsArray.getString("description");
                    String videoUrl = stepsArray.getString("videoURL");
                    String thumbnailUrl = stepsArray.getString("thumbnailURL");

                    RecipeSteps.add(new Steps(id,shortDescription,description,videoUrl,thumbnailUrl));
                }
                RecipeServings = Recipe.getInt("servings");
                RecipeImage = Recipe.getString("image");

                recipes.add(new Recipe(RecipeId,RecipeName,RecipeIngredients,RecipeSteps,RecipeServings,RecipeImage));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return recipes;
    }
}
