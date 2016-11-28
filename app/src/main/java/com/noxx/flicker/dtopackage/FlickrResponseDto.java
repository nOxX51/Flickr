package com.noxx.flicker.dtopackage;

import com.noxx.flicker.dtopackage.FlickrPhotosDto;

/**
 * Created by HB on 25/11/2016.
 */

public class FlickrResponseDto {

    private FlickrPhotosDto photos;
    private String stat;

    public FlickrPhotosDto getPhotos() {
        return photos;
    }

    public void setPhotos(FlickrPhotosDto photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    // construteur vide juste pour respecter la convention JavaBean
    public FlickrResponseDto() {
    }

    public FlickrResponseDto(FlickrPhotosDto photos, String stat) {
        this.photos = photos;
        this.stat = stat;
    }
}
