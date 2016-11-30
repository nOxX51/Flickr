package com.noxx.flickr;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * Created by HB on 25/11/2016.
 */

@Table(database = AppDataBase.class)
public class Picture extends BaseModel implements Serializable {

    @Column
    @PrimaryKey(autoincrement = true)
    private long id;
    @Column
    private int color;
    @Column
    private String title;
    @Column
    private String url;
    @Column
    private int ressources;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRessources() {
        return ressources;
    }

    public void setRessources(int ressources) {
        this.ressources = ressources;
    }

    public Picture(int color, String title, String url, int ressources) {
        this.color = color;
        this.title = title;
        this.url = url;
        this.ressources = ressources;
    }

    public Picture(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public Picture() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Picture picture = (Picture) o;

        if (title != null ? !title.equals(picture.title) : picture.title != null) return false;
        return url != null ? url.equals(picture.url) : picture.url == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
