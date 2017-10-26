package com.example.dell.mytest2;

import android.app.Application;

/**
 * Created by dell on 2017/10/14.
 */

public class Appuri extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLodelUtil.info(this);
    }
}
