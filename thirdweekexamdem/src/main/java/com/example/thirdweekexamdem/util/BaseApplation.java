package com.example.thirdweekexamdem.util;

import android.app.Application;

/**
 * Created by Administrator on 2017/10/24,0024.
 */
public class BaseApplation extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//在应用创建的时候,,,对imageLoader进行全局配置
        ImageLoaderUtil.init(this);//写一个工具类对imageLoader进行初始化…应用也可以作为上下文
    }
}
