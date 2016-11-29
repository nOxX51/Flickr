package com.noxx.flickr;

import com.noxx.flickr.dtopackage.FlickrResponseDto;
import com.noxx.flickr.dtopackage.PhotoDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HB on 25/11/2016.
 */

public class Converter {


    public static List<Picture> convert(FlickrResponseDto flickrResponseDto) {

        List<Picture> pictureList = new ArrayList<>();

            for (int index = 0;index<flickrResponseDto.getPhotos().getPhoto().size();index ++) {

                PhotoDto photo = flickrResponseDto.getPhotos().getPhoto().get(index);
                String title=photo.getTitle();
                String url="https://farm"+photo.getFarm()+".static.flickr.com/"+photo.getServer()+"/"+photo.getId()+"_"+photo.getSecret()+".jpg";

            pictureList.add(new Picture(title, url));
        }


        return pictureList;
    }


}
