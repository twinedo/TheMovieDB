package com.android.themoviedb.utils;

import android.net.Uri;

import com.android.themoviedb.api.service.ServiceConfig;

/**
 * Created by twin on 04/12/16.
 */

public class ImageUtils {
    public static Uri movieUrl(String size, String posterPath) {
        posterPath = posterPath.replace("/", "");
        return Uri.parse(ServiceConfig.BASE_IMAGE_URL).buildUpon()
                .appendPath(size)
                .appendPath(posterPath)
                .build();
    }
}