package com.android.themoviedb.api.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 Created by twin on Dec 04, 2016
Updated by twin on May 10, 2019
*/

public class RestApi {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(ServiceConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
