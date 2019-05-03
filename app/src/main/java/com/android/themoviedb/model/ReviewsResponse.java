package com.android.themoviedb.model;

import java.util.List;

/**
 * Created by twin on 07/12/16.
 */

public class ReviewsResponse {

    public List<Reviews> results;

    public ReviewsResponse(List<Reviews> results) {
        this.results = results;
    }


    public List<Reviews> getReviews() {
        return results;
    }
}
