package com.example.zhongjiti.application;

import android.app.Application;

import com.example.zhongjiti.util.ImageLoaderUtil;


/**
 * Created by Administrator on 2017/10/25.
 */

public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderUtil.init(this);
    }
}
