package com.example.bakingtime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.bakingtime.Adapters.RecipeAdapter;
import com.example.bakingtime.NetworkUtil.NetworkUtil;
import com.example.bakingtime.RecipeClasses.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements RecipeAdapter.RecipeAdapterOnClickHandler, Serializable {

    private RecipeAdapter mRecipeAdapter;
    private RecyclerView mRecyclerView;
    private SharedPreferences mSharedPreferences;
    Gson mGson = new Gson();

    public static boolean backgroundWorkDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recipe_list_view);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecipeAdapter = new RecipeAdapter(this);
        mRecyclerView.setAdapter(mRecipeAdapter);


        mSharedPreferences = getPreferences(MODE_PRIVATE);




        loadRecipeData();
    }

    //------------------------------------------------------------------
    private void loadRecipeData() {
        if(mSharedPreferences.contains(getResources().getString(R.string.myRecipes)))
        {
            //if the recipes are already saved no need to fetch them from the internet
            //get them form shared preferences that are saved as a String with JSON
            Gson gson = new Gson();
            String recipesList = mSharedPreferences.getString(getResources().getString(R.string.myRecipes),"");
            Type type = new TypeToken<List<Recipe>>(){}.getType();
            List<Recipe> recipes = gson.fromJson(recipesList,type);
            mRecipeAdapter.setRecipeData(recipes);
            //for test purposes
            backgroundWorkDone = true;
        }
        else
            new FetchRecipeTask().execute();
    }

    @Override
    public void onClick(Recipe recipeSelected) {

        //getting the recipe selected putting it on gson to pss it to the RecipeSelected class
        Gson gson = new Gson();
        String object = gson.toJson(recipeSelected);
        Intent intent = new Intent(this, RecipeDetails.class);
        intent.putExtra(getResources().getString(R.string.recipeSelected),object);
        startActivity(intent);


    }

    public class FetchRecipeTask extends AsyncTask<String, Void, List<Recipe>> {

        @Override
        protected void onPreExecute() {
            ProgressBar pb = findViewById(R.id.pb_loading_indicator);
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {

            ProgressBar pb = findViewById(R.id.pb_loading_indicator);
            pb.setVisibility(View.GONE);
            if (recipes != null) {
                //getting the recipe and save it on shared preferences
                // so there is no need to connect to the web again once the recipes are downloaded
                String recipeString = mGson.toJson(recipes);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString(getResources().getString(R.string.myRecipes),recipeString);
                editor.commit();
                //setting the adapter
                mRecipeAdapter.setRecipeData(recipes);
                //for testing purposes
                backgroundWorkDone = true;
            }
        }

        @Override
        protected List<Recipe> doInBackground(String... url) {

            URL recipeRequestURL = NetworkUtil.buildUrl();

            try {
                String JsonResponse = NetworkUtil.getResponseFromHttpUrl(recipeRequestURL);

                List<Recipe> recipeData = NetworkUtil.getRecipeData(JsonResponse);

                return recipeData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
    }
}