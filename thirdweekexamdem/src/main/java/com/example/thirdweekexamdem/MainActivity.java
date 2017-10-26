package com.example.thirdweekexamdem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.example.thirdweekexamdem.fragment.FragmentShiPin;
import com.example.thirdweekexamdem.fragment.FragmentShouYe;
import com.example.thirdweekexamdem.fragment.FragmentTouTiao;
import com.example.thirdweekexamdem.fragment.FragmentWoDe;


public class MainActivity extends AppCompatActivity {

    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找控件
        rg = (RadioGroup) findViewById(R.id.rg);
        //默认选中
        rg.check(R.id.rb1);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl,new FragmentShouYe()).commit();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl,new FragmentShouYe()).commit();
                        break;
                    case R.id.rb2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl,new FragmentShiPin()).commit();
                        break;
                    case R.id.rb3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl,new FragmentTouTiao()).commit();
                        break;
                    case R.id.rb4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl,new FragmentWoDe()).commit();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
