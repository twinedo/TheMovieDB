package com.android.themoviedb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by twin on 04/12/16.
 */

public class MovieResponse {

    @SerializedName("page")
    @Expose
    public final int page;
    @SerializedName("results")
    @Expose
    public final List<Movie> results;

    public MovieResponse(int page, List<Movie> results) {
        this.page = page;
        this.results = results;
    }

    public List<Movie> getResults(){
        return results;
    }
}
