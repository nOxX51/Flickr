package com.noxx.flicker.dtopackage;

import java.util.List;

/**
 * Created by HB on 25/11/2016.
 */

public class FlickrPhotosDto {

    private String page;
    private String pages;
    private String perpage;
    private String total;
    private List<PhotoDto>photo;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPerpage() {
        return perpage;
    }

    public void setPerpage(String perpage) {
        this.perpage = perpage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<PhotoDto> getPhoto() {
        return photo;
    }

    public void setPhoto(List<PhotoDto> photo) {
        this.photo = photo;
    }

    // construteur vide juste pour respecter la convention JavaBean
    public FlickrPhotosDto() {
    }

    public FlickrPhotosDto(String page, String pages, String perpage, String total, List<PhotoDto> photo) {
        this.page = page;
        this.pages = pages;
        this.perpage = perpage;
        this.total = total;
        this.photo = photo;
    }
}
