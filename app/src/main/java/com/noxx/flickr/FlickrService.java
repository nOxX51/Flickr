package com.noxx.flickr;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.noxx.flickr.dtopackage.FlickrResponseDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FlickrService extends Service {


    private RetrofitService service;
    private final IBinder flickr = new ServiceBinder();
    private FlickrResponseListner flickrResponseListner;


    public void setFlickrResponseListner(FlickrResponseListner flickrResponseListner) {
        this.flickrResponseListner = flickrResponseListner;
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(RetrofitService.class);

        return flickr;
    }

    public class ServiceBinder extends Binder {
        FlickrService getService() {
            return FlickrService.this;
        }
    }

    public void getPhotos(String query, String pages){

        final Call<FlickrResponseDto> flickrPhotosResponseCall = service.getPhotos(query, getResources().getString(R.string.api_flicker_key), pages);
        flickrPhotosResponseCall.enqueue(new Callback<FlickrResponseDto>(){

            @Override
            public void onResponse(Call<FlickrResponseDto> call,
            Response<FlickrResponseDto> response) {
                if (response.isSuccessful()) {

                    List<Picture> myList = Converter.convert(response.body());
                    flickrResponseListner.onPhotosReceived(myList);
                    Log.e("URL", myList.toString());

                } else {
                    Log.e("ERROR", "OnResponse not successful");
                }
            }

            @Override
            public void onFailure(Call<FlickrResponseDto> call,
            Throwable t) {
                Log.e("Fail","KabOOm !!");
                }

            });

    }
}
