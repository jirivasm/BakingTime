package com.example.bakingtime.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingtime.R;
import com.example.bakingtime.RecipeClasses.Steps;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder> {

    List<Steps> mStepsData;
    public final StepsAdapterOnClickHandler mClickHandler;

    public interface StepsAdapterOnClickHandler{
        void onClick(Steps StepsSelected);
    }
    public StepsAdapter(StepsAdapterOnClickHandler onClickHandler){
        mClickHandler = onClickHandler;
    }
    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        public final TextView mStepsName;

        public StepsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mStepsName = itemView.findViewById(R.id.steps_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Steps StepsSelected = mStepsData.get(adapterPosition);
            mClickHandler.onClick(StepsSelected);
        }
    }

    @NonNull
    @Override
    public StepsAdapter.StepsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.steps_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new StepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.StepsAdapterViewHolder holder, int position) {

        Steps Steps = mStepsData.get(position);
        holder.mStepsName.setText(Steps.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if(mStepsData == null)
            return 0;

        return mStepsData.size();
    }
    public void setStepsData(List<Steps> StepsData) {
        mStepsData = StepsData;
        notifyDataSetChanged();
    }
}
