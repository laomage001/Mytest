package com.example.lianxi;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.lianxi.fragment.Fragment_HangQing;
import com.example.lianxi.fragment.Fragment_JiaoYi;
import com.example.lianxi.fragment.Fragment_ShouYe;
import com.example.lianxi.fragment.Fragment_ZiXuan;
import com.example.lianxi.fragment.Fragment_ZiXun;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private DrawerLayout drawerLayout;
    private RelativeLayout relativeLayout;
    private RadioGroup radioGroup;
    private ListView listView;
    private List<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找控件
        imageView = (ImageView) findViewById(R.id.image_touxiang);
        //抽屉布局
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //抽屉布局
        relativeLayout = (RelativeLayout) findViewById(R.id.drawer_relative);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        listView = (ListView) findViewById(R.id.list_view);

        //给listView设置适配器
        titles = new ArrayList<>();
        titles.add("客服热线");
        titles.add("营业部网点");
        titles.add("系统设置");
        titles.add("换肤");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, titles);
        listView.setAdapter(adapter);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                switch (i){
                    case R.id.radio_01:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new Fragment_ShouYe()).commit();
                    break;
                    case R.id.radio_02:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new Fragment_ZiXuan()).commit();
                        break;
                    case R.id.radio_03:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new Fragment_HangQing()).commit();
                        break;
                    case R.id.radio_04:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new Fragment_ZiXun()).commit();
                        break;
                    case R.id.radio_05:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new Fragment_JiaoYi()).commit();
                        break;

                    default:
                        break;
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(relativeLayout);
            }
        });
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }

        });


    }
}
