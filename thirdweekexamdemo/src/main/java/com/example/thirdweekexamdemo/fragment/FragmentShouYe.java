package com.example.thirdweekexamdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.thirdweekexamdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/17,0017.
 */
public class FragmentShouYe extends Fragment {
    ViewPager vp;
    private TabLayout tab;
    private EditText et;
    List< String> list  = new ArrayList< >();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_shouye,container,false);
        tab = view.findViewById(R.id.tab);
        et = view.findViewById(R.id.et);
        vp = view.findViewById(R.id.vp);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //添加(横向滑动的条目)tab的显示项
        list.add("头条");
        list.add("军事");
        list.add("娱乐");
        list.add("新闻");
        list.add("财经");
        list.add("体育");
        list.add("科技");
        list.add("游戏");
        //设置适配器
        vp.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public CharSequence getPageTitle(int position) {
                return list.get(position);
            }
            @Override
            public Fragment getItem(int position) {
                Fragment fragment =null;
                switch (position){
                    case 0:
                        fragment = new FragmentTouTiao();
                        break;
                    case 1:
                        fragment = new FragmentTouTiao();
                        break;
                    case 2:
                        fragment = new FragmentTouTiao();
                        break;
                    case 3:
                        fragment = new FragmentTouTiao();
                        break;
                    case 4:
                        fragment = new FragmentTouTiao();
                        break;
                    case 5:
                        fragment = new FragmentTouTiao();
                        break;
                    case 6:
                        fragment = new FragmentTouTiao();
                        break;
                    case 7:
                        fragment = new FragmentTouTiao();
                        break;
                    default:
                        break;
                }
                return fragment;
            }
            @Override
            public int getCount() {
                return list.size();
            }
        });
        //关联
        tab.setupWithViewPager(vp);
        vp.setOffscreenPageLimit(list.size());
    }
}
