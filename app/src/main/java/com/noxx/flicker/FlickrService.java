package com.noxx.flicker;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FlickrService extends Service {

    private final IBinder flickr = new ServiceBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return flickr;
    }

    public class ServiceBinder extends Binder {
        FlickrService getService() {
            return FlickrService.this;
        }
    }

    public List<Picture> getPhotos(){

        List<Picture> pictureList = new ArrayList<>();

        pictureList.add(new Picture(Color.MAGENTA,"Donatello", "http://donatello.pic.com", R.drawable.donatello_2));
        pictureList.add(new Picture (Color.BLUE,"Leonardo", "http://leonardo.pic.com", R.drawable.leonardo_2));
        pictureList.add(new Picture (Color.RED,"Raphael", "http://Raphael.pic.com", R.drawable.raphael_sc3a9rie_tv_2003_61));
        pictureList.add(new Picture (Color.YELLOW,"Michelangelo", "http://Michelangelo.pic.com", R.drawable.mikey_5));


        return pictureList;

    }
}
