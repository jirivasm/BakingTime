package com.example.bakingtime.RecipeClasses;


import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable {


    //Steps for the recipe with constructor, getters and setters
    private int mId;
    private String mName;
    private List<Ingredients> mIngredients;
    private List<Steps> mSteps;
    private int mServings;
    private String mImage;

    //Constrictor getters and setters for the recipe
    public Recipe() {
    }

    public Recipe(int mId, String mName, List<Ingredients> mIngredients, List<Steps> mSteps, int servings, String mImage) {
        this.mId = mId;
        this.mName = mName;
        this.mIngredients = mIngredients;
        this.mSteps = mSteps;
        mServings = servings;
        this.mImage = mImage;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public List<Ingredients> getIngredients() {
        return mIngredients;
    }

    public List<Steps> getSteps() {
        return mSteps;
    }

    public int getServings() {
        return mServings;
    }

    public String getImage() {
        return mImage;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setIngredients(List<Ingredients> mIngredients) {
        this.mIngredients = mIngredients;
    }

    public void setSteps(List<Steps> mSteps) {
        this.mSteps = mSteps;
    }

    public void setServings(int mServings) {
        this.mServings = mServings;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }
}

