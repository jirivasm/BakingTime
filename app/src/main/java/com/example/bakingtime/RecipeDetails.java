package com.example.bakingtime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bakingtime.Adapters.IngredientAdapter;
import com.example.bakingtime.RecipeClasses.Recipe;
import com.example.bakingtime.RecipeClasses.Steps;
import com.google.gson.Gson;

public class RecipeDetails extends AppCompatActivity
        implements RecipeDetailsMasterListFragment.OnStepClickListener {


    boolean mIsTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);


        if (findViewById(R.id.two_pane_linear_layout) != null) {

            mIsTwoPane = true;
            if (savedInstanceState == null) {
                if (findViewById(R.id.select_step_text_view).getVisibility() == View.INVISIBLE) {
                    StepsDetailFragment stepsDetail = new StepsDetailFragment();

                    FragmentManager fragmentManager = getSupportFragmentManager();

                    fragmentManager.beginTransaction().add(R.id.step_description_container, stepsDetail).commit();
                }
            }
        } else
            mIsTwoPane = false;

        Intent widgetIntent = new Intent(this, IngredientsDisplayWidget.class);
        widgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), IngredientsDisplayWidget.class));
        widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        AppWidgetManager manager = AppWidgetManager.getInstance(getApplication());

        manager.notifyAppWidgetViewDataChanged(ids, R.id.ingredients_grid_view);

        sendBroadcast(widgetIntent);
    }

    @Override
    public void onStepSelected(Steps step, Recipe recipe) {
        Intent intentToStepDetails = new Intent(this, StepDetailsActivity.class);

        if (mIsTwoPane) {
            findViewById(R.id.select_step_text_view).setVisibility(View.INVISIBLE);

            StepsDetailFragment stepsDetail = new StepsDetailFragment();

            stepsDetail.mStepSelected = step;
            stepsDetail.mRecipeSelected = recipe;

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction().replace(R.id.step_description_container, stepsDetail).commit();

        } else {
            Gson gson = new Gson();
            String stepSelected = gson.toJson(step);
            String recipeSelected = gson.toJson(recipe);
            intentToStepDetails.putExtra(getResources().getString(R.string.stepSelected), stepSelected);
            intentToStepDetails.putExtra(getResources().getString(R.string.recipeSelected), recipeSelected);

            startActivity(intentToStepDetails);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}