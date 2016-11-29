package com.noxx.flickr;

import java.util.List;

/**
 * Created by HB on 28/11/2016.
 */

public interface FlickrResponseListner {

    void onPhotosReceived(List<Picture>pictureList);

}
