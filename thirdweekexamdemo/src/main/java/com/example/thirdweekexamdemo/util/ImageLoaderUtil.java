package com.example.thirdweekexamdemo.util;

import android.content.Context;
import android.os.Environment;

import com.example.thirdweekexamdemo.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;

/**
 * Created by Administrator on 2017/10/16,0016.
 */
public class ImageLoaderUtil {
    public static void init(Context context){
//自己创建一个文件夹作为缓存的路径
        File file = new File(Environment.getExternalStorageDirectory(),"image");
        if(!file.exists()){
            file.mkdirs();
        }
//创建一个图片加载器的配置对象
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(3) //线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2 ) //设置当前线程的优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
//LruMemoryCache,,lru最近最少使用算法…内部维护的是LinkedHashMap,,当一张图片最近很少使用的时候会从mao集合里面移除
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024)) //指定内存缓存的大小,,2M,,可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
                .memoryCacheSizePercentage(13) // default
//指定硬盘/磁盘缓存的路径
                .diskCache(new UnlimitedDiskCache(file)) // default 可以自定义缓存路径
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb sd卡(本地)缓存的最大值
                .diskCacheFileCount(100) // 可以缓存的文件数量
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs() // 打印debug log
                .build(); //开始构建
//对上面的配置进行初始化
        ImageLoader.getInstance().init(config);
    }
    //(没网的时候)图片的显示方法 在适配器找图片时调用此方法
    public static DisplayImageOptions getDefaultOption() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher) // 设置图片Uri为空或是错误的时候显示的图片 .showImageOnFail(R.mipmap.ic_launcher) // 设置图片加载或解码过程中发生错误显示的图片
                .resetViewBeforeLoading(true) // default 设置图片在加载前是否重置、复位
                .cacheInMemory(true) // default 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // default 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // default .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default 设置图片以如何的编码方式显示 .bitmapConfig(Bitmap.Config.RGB_565) // default 设置图片的解码类型
                .displayer(new RoundedBitmapDisplayer(10)) // default 还可以设置圆角图片new RoundedBitmapDisplayer(20)

                .build();
        return options;
    }
}
