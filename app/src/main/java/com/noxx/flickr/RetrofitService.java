package com.noxx.flickr;

import com.noxx.flickr.dtopackage.FlickrResponseDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by HB on 28/11/2016.
 */

public interface RetrofitService {

    @GET("services/rest/?method=flickr.photos.search&safe_search=1&format=json&nojsoncallback=1")
    Call<FlickrResponseDto> getPhotos(@Query("tags") String query, @Query("api_key")String apiKey, @Query("per_page")String pages);

}
