package com.hugo.pagerplayer;


import android.os.Environment;

public class Config {
    public static boolean IS_DEBUGGING = true;

    public static final String URL = "URL";
    public static final String POSITION = "POSITION";


    public static final String EVENT_PAUSE = "EVENT_PAUSE";
    public static final String EVENT_START = "EVENT_START";

    public static final String EVENT_FRONT_START = "EVENT_FRONT_START";
    public static final String EVENT_FRONT_PAUSE = "EVENT_FRONT_PAUSE";
    public static final String SDCARD_DIR = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String DEFAULT_CACHE_DIR = SDCARD_DIR + "/PLDroidPlayer";


}
