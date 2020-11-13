package com.example.bakingtime;

import androidx.test.espresso.IdlingResource;

public class RecipesRecyclerViewIdlingResource implements IdlingResource {

    private ResourceCallback resourceCallback;
    @Override
    public String getName() {
        return RecipesRecyclerViewIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        boolean isIdle = MainActivity.backgroundWorkDone;

        if(isIdle && resourceCallback != null)
        {
            resourceCallback.onTransitionToIdle();
        }
        return isIdle;

    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
this.resourceCallback = callback;
    }
}
