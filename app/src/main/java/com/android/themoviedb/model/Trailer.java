package com.android.themoviedb.model;

/*
 Created by twin on Dec 04, 2016
Updated by twin on May 10, 2019
*/

public class Trailer {
    public final String key;
    public final String name;
    public final String site;
    public final String type;

    public Trailer(String key, String name, String site, String type) {
        this.key = key;
        this.name = name;
        this.site = site;
        this.type = type;
    }
}
