package com.example.bakingtime;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingtime.Adapters.IngredientAdapter;
import com.example.bakingtime.Adapters.StepsAdapter;
import com.example.bakingtime.RecipeClasses.Recipe;
import com.example.bakingtime.RecipeClasses.Steps;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class RecipeDetailsMasterListFragment extends Fragment
        implements StepsAdapter.StepsAdapterOnClickHandler {


    private IngredientAdapter mIngredientAdapter;
    private RecyclerView mIngredientRecyclerView;

    private StepsAdapter mStepsAdapter;
    private RecyclerView mStepsRecyclerView;


    private Recipe mSelectedRecipe = new Recipe();

    OnStepClickListener mCallback;

    Gson mGson = new Gson();

    @Override
    public void onClick(Steps StepsSelected) {

        mCallback.onStepSelected(StepsSelected, mSelectedRecipe);
    }

    public interface OnStepClickListener {
        void onStepSelected(Steps step, Recipe recipe);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnStepClickListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RecipeDetailsMasterListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_details_master_list, container, false);

        mIngredientRecyclerView = rootView.findViewById(R.id.ingredients_list);
        mStepsRecyclerView = rootView.findViewById(R.id.steps_list);


        Gson gson = new Gson();

        mSelectedRecipe = mGson.fromJson(getActivity().getIntent().getStringExtra(getResources().getString(R.string.recipeSelected)), Recipe.class);

        SharedPreferences mSharedPreferences = this.getActivity().getSharedPreferences(getString(R.string.myPrefs), MODE_PRIVATE);

        if (mSelectedRecipe == null) {
            String recipesList = mSharedPreferences.getString(getString(R.string.preferencesRecipeSelected), "");
            mSelectedRecipe = gson.fromJson(recipesList, Recipe.class);
        }
        else{
            String recipeString = gson.toJson(mSelectedRecipe);
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(getString(R.string.preferencesRecipeSelected), recipeString);
            editor.apply();
        }

        LinearLayoutManager IngredientsLinearLayoutManager = new LinearLayoutManager(rootView.getContext(), RecyclerView.VERTICAL, false);
        LinearLayoutManager StepsLinearLayoutManager = new LinearLayoutManager(rootView.getContext(), RecyclerView.VERTICAL, false);

        mIngredientRecyclerView.setLayoutManager(IngredientsLinearLayoutManager);
        mIngredientRecyclerView.setHasFixedSize(true);

        mStepsRecyclerView.setLayoutManager(StepsLinearLayoutManager);
        mStepsRecyclerView.setHasFixedSize(true);

        mIngredientAdapter = new IngredientAdapter();
        mStepsAdapter = new StepsAdapter(this);

        mIngredientAdapter.setIngredientData(mSelectedRecipe.getIngredients());
        mIngredientRecyclerView.setAdapter(mIngredientAdapter);

        mStepsAdapter.setStepsData(mSelectedRecipe.getSteps());
        mStepsRecyclerView.setAdapter(mStepsAdapter);



        return rootView;
    }
}
