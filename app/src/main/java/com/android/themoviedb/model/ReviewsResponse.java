package com.android.themoviedb.model;

import java.util.List;
/*
 Created by twin on Dec 04, 2016
Updated by twin on May 10, 2019
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
