package com.example.bakingtime;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingtime.RecipeClasses.Ingredients;
import com.example.bakingtime.RecipeClasses.Recipe;
import com.google.gson.Gson;



public class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    Recipe mRecipeSelected = new Recipe();


    public GridRemoteViewsFactory(Context applicationContext) {

        mContext = applicationContext;

    }
    @Override
    public void onCreate() {

        getIngredients();

    }
    @Override
    public void onDataSetChanged() {
        getIngredients();
    }
    @Override
    public void onDestroy() {
    }
    @Override
    public int getCount() {
        //need the size of the ingredients.
        return mRecipeSelected.getIngredients().size();
    }
    @Override
    public RemoteViews getViewAt(int position) {

        //fill in the individual item of the gridView of the widget

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
        //getting the recipe selected info to put on the textView of the item
        String ingredient = mRecipeSelected.getIngredients().get(position).getIngredient();
        double quantity = mRecipeSelected.getIngredients().get(position).getQuantity();
        String measure = mRecipeSelected.getIngredients().get(position).getMeasure();
        //filling in the item
        String ingredientString = quantity + measure +" "+ mContext.getString(R.string.of) +" "+ ingredient;
        views.setTextViewText(R.id.widget_ingredient_item, ingredientString);
        //Setting up FillIn Intent here for the gridview - To fill in the PendingIntent Template
        Intent fillInIntent = new Intent();
        //filling the item
        views.setOnClickFillInIntent(R.id.widget_ingredient_item, fillInIntent);
        return views;
    }
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }
    @Override
    public int getViewTypeCount() {
        return 1;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }
    private void getIngredients() {

        //getting the recipe from the shared preferences saved back on the Recipe Details
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.myPrefs), Context.MODE_PRIVATE);
        String recipe = sharedPreferences.getString(mContext.getString(R.string.preferencesRecipeSelected), "");
        Gson gson = new Gson();
        mRecipeSelected = gson.fromJson(recipe, Recipe.class);

    }
}