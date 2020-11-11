package com.example.bakingtime.RecipeClasses;

import java.io.Serializable;

public class Steps implements Serializable {

    private int mID;
    private String mShortDescription;
    private String mDescription;
    private String mVideoURL;
    private String mThumbnailURL;

    public Steps(){}
    public Steps(int mID, String shortDescription, String description, String mVideoURL, String mThumbnailURL) {
        this.mID = mID;
        mShortDescription = shortDescription;
        mDescription = description;
        this.mVideoURL = mVideoURL;
        this.mThumbnailURL = mThumbnailURL;
    }



    public int getID() {
        return mID;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getVideoURL() {
        return mVideoURL;
    }

    public String getThumbnailURL() {
        return mThumbnailURL;
    }
}