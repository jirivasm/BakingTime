package com.example.bakingtime;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bakingtime.RecipeClasses.Recipe;
import com.example.bakingtime.RecipeClasses.Steps;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;

public class StepsDetailFragment extends Fragment {

    private static final String KEY_PLAYER_POSITION = "playerPosition";
    private static final String KEY_STEP_ID = "stepID";

    Steps mStepSelected;
    Recipe mRecipeSelected;
    int mStepID;

    Button mNextStepButton;
    Button mPrevStepButton;

    SimpleExoPlayer mExoPlayer;
    PlayerView mPlayerView;
    Long mExoPlayerPosition = 0L;


    public StepsDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.steps_detail_fragment, container, false);

        Gson gson = new Gson();
        mRecipeSelected = gson.fromJson(getActivity().getIntent().getStringExtra(getResources().getString(R.string.recipeSelected)), Recipe.class);
        if (savedInstanceState != null) {
            mExoPlayerPosition = savedInstanceState.getLong(KEY_PLAYER_POSITION);
            mStepID = savedInstanceState.getInt(KEY_STEP_ID);
            mStepSelected = mRecipeSelected.getSteps().get(mStepID);
        } else {
            if (mStepSelected == null) {
                //Need to get the recipe trough an intent.
                mStepSelected = gson.fromJson(getActivity().getIntent().getStringExtra(getResources().getString(R.string.stepSelected)), Steps.class);
            }

            mStepID = mStepSelected.getID();
        }


        final TextView stepDescription = rootView.findViewById(R.id.steps_long_description_detail);
        stepDescription.setText(mStepSelected.getDescription());

        mNextStepButton = rootView.findViewById(R.id.next_step_button);
        mPrevStepButton = rootView.findViewById(R.id.prev_step_button);
        mPlayerView = rootView.findViewById(R.id.exoplayer_view);
        mPlayerView.setShowPreviousButton(false);
        mPlayerView.setShowNextButton(false);
        mPlayerView.setShowFastForwardButton(false);
        mPlayerView.setShowRewindButton(false);


        SetButtonsVisibility();


        mNextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                releasePlayer();
                if (mStepID != mRecipeSelected.getSteps().size() - 1) {
                    mStepID++;
                    mStepSelected = mRecipeSelected.getSteps().get(mStepID);
                    stepDescription.setText(mStepSelected.getDescription());
                    mExoPlayerPosition = 0L;
                } else {
                    mNextStepButton.setVisibility(View.INVISIBLE);
                }
                SetButtonsVisibility();
                InitializePlayer();
            }
        });
        mPrevStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releasePlayer();

                if (mStepID != 0) {
                    mStepID--;
                    mStepSelected = mRecipeSelected.getSteps().get(mStepID);
                    stepDescription.setText(mStepSelected.getDescription());
                    mExoPlayerPosition = 0L;

                } else {
                    mNextStepButton.setVisibility(View.INVISIBLE);

                }
                SetButtonsVisibility();

                InitializePlayer();
            }

        });

        InitializePlayer();
        return rootView;
    }


    private void SetButtonsVisibility() {
        if (mStepID < mRecipeSelected.getSteps().size() - 1)
            mNextStepButton.setVisibility(View.VISIBLE);
        else
            mNextStepButton.setVisibility(View.INVISIBLE);
        if (mStepID > 0)
            mPrevStepButton.setVisibility(View.VISIBLE);
        else
            mPrevStepButton.setVisibility(View.INVISIBLE);
    }

    private void InitializePlayer() {

        mPlayerView.setVisibility(View.VISIBLE);
        Uri u;
        if (!mStepSelected.getVideoURL().isEmpty())
            u = Uri.parse(mStepSelected.getVideoURL());
        else if (!mStepSelected.getThumbnailURL().isEmpty())
            u = Uri.parse(mStepSelected.getThumbnailURL());
        else {
            mPlayerView.setVisibility(View.GONE);
            return;
        }

        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        mPlayerView.setPlayer(mExoPlayer);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA)
                .setContentType(C.CONTENT_TYPE_MOVIE)
                .build();

        // Prepare the MediaSource.
        String userAgent = Util.getUserAgent(getContext(), "Baking Time");
        MediaSource mediaSource = new ExtractorMediaSource(u, new DefaultDataSourceFactory(
                getContext(), userAgent), new DefaultExtractorsFactory(), null, null);

        mExoPlayer.prepare(mediaSource);
        mExoPlayer.seekTo(mExoPlayerPosition);
        mExoPlayer.setAudioAttributes(audioAttributes, true);
        mExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putLong(KEY_PLAYER_POSITION, mExoPlayerPosition);
        outState.putInt(KEY_STEP_ID, mStepID);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mExoPlayer != null) {
            mExoPlayerPosition = mExoPlayer.getContentPosition();
            releasePlayer();
            mStepSelected = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mExoPlayer == null)
            InitializePlayer();
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {

            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}
