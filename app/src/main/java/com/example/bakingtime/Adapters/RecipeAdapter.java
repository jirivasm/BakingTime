package com.example.bakingtime.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingtime.R;
import com.example.bakingtime.RecipeClasses.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    List<Recipe> mRecipeData;
    public final RecipeAdapterOnClickHandler mClickHandler;

    public interface RecipeAdapterOnClickHandler{
        void onClick(Recipe recipeSelected);
    }
    public RecipeAdapter(RecipeAdapterOnClickHandler onClickHandler){
        mClickHandler = onClickHandler;
    }
    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{

        public final TextView mRecipeName;

        public RecipeAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mRecipeName = itemView.findViewById(R.id.recipe_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe recipeSelected = mRecipeData.get(adapterPosition);
            mClickHandler.onClick(recipeSelected);
        }
    }

    @NonNull
    @Override
    public RecipeAdapter.RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeAdapterViewHolder holder, int position) {

        Recipe recipe = mRecipeData.get(position);
       holder.mRecipeName.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        if(mRecipeData == null)
        return 0;

        return mRecipeData.size();
    }
    public void setRecipeData(List<Recipe> recipeData) {
        mRecipeData = recipeData;
        notifyDataSetChanged();
    }
}
