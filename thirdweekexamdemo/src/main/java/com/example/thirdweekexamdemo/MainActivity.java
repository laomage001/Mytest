package com.example.thirdweekexamdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Logo页面
 */
public class MainActivity extends FragmentActivity {
    //handler倒计时
    Handler handler = new Handler();
    //定义时间
    int time = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消标题
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消通知栏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                time -- ;
                //判断时间
                if(time == 0){
                    //跳转到主页面
                    Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                handler.postDelayed(this,1000);
            }

        },1000);
    }
}
