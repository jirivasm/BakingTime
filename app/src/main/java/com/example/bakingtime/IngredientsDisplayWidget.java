package com.example.bakingtime;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.example.bakingtime.RecipeClasses.Recipe;
import com.google.gson.Gson;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsDisplayWidget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {



        RemoteViews rv = getIngredientsGridView(context);

        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static RemoteViews getIngredientsGridView(Context context) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_display_widget);


        Intent intent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.ingredients_grid_view, intent);

        //intent to open the recipe details when clicked
        Intent appIntent = new Intent(context, RecipeDetails.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.ingredients_grid_view, pendingIntent);

        views.setEmptyView(R.id.ingredients_grid_view, R.id.empty_view);

        return views;
    }
}


