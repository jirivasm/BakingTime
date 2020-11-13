package com.example.bakingtime;

import android.app.Instrumentation;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.espresso.contrib.RecyclerViewActions;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeSelectBasicTest {

    private RecipesRecyclerViewIdlingResource idlingResource;

    @Rule
    public IntentsTestRule<MainActivity> mainActivityIntentsTestRule = new IntentsTestRule<MainActivity>(MainActivity.class);

    @Before
    public void registerIntentServiceIdlingResource()
    {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        idlingResource = new RecipesRecyclerViewIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }
    @Test
    public void ClickGridItem_OpenRecipeDetailsActivity()
    {
        onView(withId(R.id.recipe_list_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        intended(hasComponent(RecipeDetails.class.getName()));

    }
    @After
    public void unregisterIntentServiceIdlingResource() {
        Espresso.unregisterIdlingResources(idlingResource);
    }
}
