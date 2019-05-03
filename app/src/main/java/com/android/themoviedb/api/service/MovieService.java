package com.android.themoviedb.api.service;

import com.android.themoviedb.model.MovieResponse;
import com.android.themoviedb.model.ReviewsResponse;
import com.android.themoviedb.model.TrailersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by twin on 04/12/16.
 */

public interface MovieService {

    @GET("movie/{sort}")
    Call<MovieResponse> listOfMovie(@Path("sort") String sort,
                                     @Query("api_key") String apiKey);

    //Trailers
    @GET("movie/{id}/videos")
    Call<TrailersResponse> trailerMovie(@Path("id") long id, @Query("api_key") String apiKey);

    //Reviews
    @GET("movie/{id}/reviews")
    Call<ReviewsResponse> reviewsMovie(@Path("id") long id, @Query("api_key") String apiKey);
}
