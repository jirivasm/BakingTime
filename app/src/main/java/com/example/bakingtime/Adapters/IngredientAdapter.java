package com.example.bakingtime.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingtime.RecipeClasses.Ingredients;
import com.example.bakingtime.R;

import java.io.Serializable;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientAdapterViewHolder>
implements Serializable {

    List<Ingredients> mIngredientData;
    Context mContext;

    public IngredientAdapter(){


    }
    public class IngredientAdapterViewHolder extends RecyclerView.ViewHolder {

        public final TextView mIngredientName;

        public IngredientAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mIngredientName = itemView.findViewById(R.id.ingredient_item);

        }

    }

    @NonNull
    @Override
    public IngredientAdapter.IngredientAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        mContext = context;
        int layoutIdForListItem = R.layout.ingredients_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new IngredientAdapter.IngredientAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.IngredientAdapterViewHolder holder, int position) {

        Ingredients Ingredient = mIngredientData.get(position);
        String ingredientString = Ingredient.getQuantity() +" "+Ingredient.getMeasure()+" "+mContext.getResources().getString(R.string.of)+" "+Ingredient.getIngredient();
        holder.mIngredientName.setText(ingredientString);
    }

    @Override
    public int getItemCount() {
        if(mIngredientData == null)
            return 0;

        return mIngredientData.size();
    }
    public void setIngredientData(List<Ingredients> IngredientData) {
        mIngredientData = IngredientData;
        notifyDataSetChanged();
    }
}
