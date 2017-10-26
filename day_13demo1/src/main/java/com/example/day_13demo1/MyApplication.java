package com.example.day_13demo1;

import android.app.Application;

/**
 * Created by dell on 2017/10/14.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderUtil.init(this);
    }
}
