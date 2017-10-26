package com.example.zhongjiti;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.zhongjiti.fragment.Fragment01;
import com.example.zhongjiti.fragment.Fragment02;
import com.example.zhongjiti.fragment.Fragment03;
import com.example.zhongjiti.fragment.Fragment04;
import com.example.zhongjiti.fragment.Fragment05;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private DrawerLayout drawerLayout;
    private FrameLayout frameLayout;
    private RelativeLayout relativeLayout;
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image_touxiang);
        //抽屉布局
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        //主内容区域
        frameLayout = (FrameLayout) findViewById(R.id.fragment_layout);

        //抽屉布局
        relativeLayout = (RelativeLayout) findViewById(R.id.drawer_relative);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        ListView listView = (ListView) findViewById(R.id.list_view);
        //给listView设置适配器
        List<String> titles = new ArrayList<>();
        titles.add("个人设置");
        titles.add("缓存");
        titles.add("夜间模式");
        titles.add("配置");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, titles);
        listView.setAdapter(adapter);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.radio_01:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new Fragment01()).commit();
                        break;
                    case R.id.radio_02:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new Fragment02()).commit();
                        break;
                    case R.id.radio_03:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new Fragment03()).commit();
                        break;
                    case R.id.radio_04:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new Fragment04()).commit();
                        break;
                    case R.id.radio_05:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new Fragment05()).commit();
                        break;
                    default:
                        break;
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击上面的 头像 关闭侧拉
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
