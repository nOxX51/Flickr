package com.noxx.flickr;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by HB on 30/11/2016.
 */
@Database
(name = AppDataBase.NAME, version = AppDataBase.VERSION, foreignKeysSupported = true)

public class AppDataBase {

    public static final String NAME = "Photo";
    public static final int VERSION = 1;
}
