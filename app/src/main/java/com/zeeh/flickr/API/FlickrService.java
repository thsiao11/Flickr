package com.zeeh.flickr.API;

import com.zeeh.flickr.model.Flickrmodel;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface FlickrService {
    @GET("/services/rest/")
    // getPhoto(method, api_key, tags, format, per_page, media, nojsoncallback)
    Call<Flickrmodel> getFlickr(@Query("method") String method,@Query("api_key") String api_key,
                         @Query("tags") String tags, @Query("format") String format,
                         @Query("per_page") String per_page, @Query("media") String media,
                         @Query("nojsoncallback") String nojsoncallback);

}
