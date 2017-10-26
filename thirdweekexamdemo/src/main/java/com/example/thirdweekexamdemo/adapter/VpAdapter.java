package com.example.thirdweekexamdemo.adapter;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.thirdweekexamdemo.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/10/19,0019.
 */
public class VpAdapter extends PagerAdapter{
    Context context;
    List<String> imageUrlList;
    Handler handler;
    public VpAdapter(Context context, List<String> imageUrlList, Handler handler) {
        this.context = context;
        this.imageUrlList = imageUrlList;
        this.handler = handler;
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //1.创建imageView...添加到容器中
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //展示图片
        ImageLoader.getInstance().displayImage(imageUrlList.get(position%imageUrlList.size()),imageView, ImageLoaderUtil.getDefaultOption());

        //给imageView设置触摸的监听事件
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();//获取手指的动作
                switch (action) {
                    case MotionEvent.ACTION_DOWN://按下的动作…应该取消发送消息的操作
                        handler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_MOVE://移动的动作
                        handler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_CANCEL://取消
//重新发送
                        handler.sendEmptyMessageDelayed(0, 2000);
                        break;
                    case MotionEvent.ACTION_UP://抬起的动作
                        handler.sendEmptyMessageDelayed(0, 2000);
                        break;
                }
//返回true表示自己处理触摸事件
                return true;
            }
        });
        //添加
        container.addView(imageView);
        //2.返回当前展示的imageView控件
        return imageView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
