package com.example.thirdweekexamdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.RadioGroup;

import com.example.thirdweekexamdemo.fragment.FragmentDengLu;
import com.example.thirdweekexamdemo.fragment.FragmentShouYe;
import com.example.thirdweekexamdemo.fragment.FragmentYaoWen;


/**
 * Created by Administrator on 2017/10/17,0017.
 */
public class HomeActivity extends FragmentActivity {

    private RadioGroup rg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        //找控件
        rg = (RadioGroup) findViewById(R.id.rg);
        //默认选中rb1
        rg.check(R.id.rb1);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl,new FragmentShouYe()).commit();
        //rg的点击事件
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl,new FragmentShouYe()).commit();
                        break;
                    case R.id.rb2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl,new FragmentYaoWen()).commit();
                        break;
                    case R.id.rb3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl,new FragmentDengLu()).commit();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
