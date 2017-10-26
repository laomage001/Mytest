package com.example.thirdweekexamdem.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thirdweekexamdem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/24,0024.
 */
public class FragmentShouYe extends Fragment {

    private TabLayout tab;
    private ViewPager vp;
    private List<String> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_shouye,container,false);
        tab = view.findViewById(R.id.tab);
        vp = view.findViewById(R.id.vp);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //向tab添加数据
        list = new ArrayList<>();
        list.add("关注");
        list.add("推荐");
        list.add("十九大");
        list.add("视频");
        list.add("热点");
        list.add("科技");
        list.add("数码");
        vp.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            //2.重写这个方法getPageTitle,,,得到当前页面的标题
            @Override
            public CharSequence getPageTitle(int position) {
                return list.get(position);
            }
            @Override
            public Fragment getItem(int position) {
                FragmentTuiJian fragment = new FragmentTuiJian();

                return fragment;
            }
            @Override
            public int getCount() {
                return list.size();
            }
        });
        //3.将tabLayout和viewPager关联起来
        tab.setupWithViewPager(vp);
    }
}
