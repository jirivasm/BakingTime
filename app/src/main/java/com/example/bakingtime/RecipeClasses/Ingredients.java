package com.example.bakingtime.RecipeClasses;

import java.io.Serializable;

public  class Ingredients implements Serializable {

    private double mQuantity;
    private String mMeasure;
    private String mIngredient;

    public Ingredients(double mQuantity, String mMeasure, String mIngredient) {
        this.mQuantity = mQuantity;
        this.mMeasure = mMeasure;
        this.mIngredient = mIngredient;
    }


    public double getQuantity() {
        return mQuantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public String getIngredient() {
        return mIngredient;
    }
}
