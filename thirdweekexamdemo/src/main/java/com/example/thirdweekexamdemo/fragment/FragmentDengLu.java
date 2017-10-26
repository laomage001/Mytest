package com.example.thirdweekexamdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.thirdweekexamdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/17,0017.
 */
public class FragmentDengLu extends Fragment {

    private DrawerLayout dl;
    private ImageView iv;
    private ListView lv;
    private RelativeLayout rl;
    List<String> list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_denglu,container,false);
        dl = view.findViewById(R.id.dl);
        iv = view.findViewById(R.id.iv);
        lv = view.findViewById(R.id.lv);
        rl = view.findViewById(R.id.rl);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list.add("体育");
        list.add("音乐");
        list.add("娱乐");
        list.add("天气");
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl,new NewsFragment()).commit();
        //抽屉的监听事件
        dl.setDrawerListener(new DrawerLayout.DrawerListener() {
            // @param slideOffset 表示的抽屉拖出来的宽度,,像素值
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }
            // 抽屉打开了
            @Override
            public void onDrawerOpened(View drawerView) {
            }
            //抽屉关闭
            @Override
            public void onDrawerClosed(View drawerView) {

            }
            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dl.closeDrawer(rl);
                dl.openDrawer(rl);
            }
        });
//        //隐藏侧滑菜单
//        dl.closeDrawer(rl);
//        dl.openDrawer(rl);//打开侧滑菜单
    }
}
