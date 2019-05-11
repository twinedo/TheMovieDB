package com.android.themoviedb.model;

import com.google.gson.annotations.SerializedName;

/*
 Created by twin on Dec 04, 2016
Updated by twin on May 10, 2019
*/

public class Reviews {

    @SerializedName("id")
    public String mId;
    @SerializedName("author")
    public String mAuthor;
    @SerializedName("content")
    public String mContent;
    @SerializedName("url")
    public String mUrl;

    public String getContent() {
        return mContent;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getUrl() {
        return mUrl;
    }

    public Reviews(String mId, String mAuthor, String mContent, String mUrl) {
        this.mId = mId;
        this.mAuthor = mAuthor;
        this.mContent = mContent;
        this.mUrl = mUrl;
    }
}
