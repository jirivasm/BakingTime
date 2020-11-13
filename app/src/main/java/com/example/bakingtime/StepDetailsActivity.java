package com.example.bakingtime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StepDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this layout opens the steps detail activity fragment
        //all fragments need their own activity to survive
        setContentView(R.layout.activity_step_details);


        if(savedInstanceState == null) {

            //if the saved instance state is null we add the fragment
                StepsDetailFragment stepsDetail = new StepsDetailFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.step_description_container, stepsDetail).commit();
        }

    }
}