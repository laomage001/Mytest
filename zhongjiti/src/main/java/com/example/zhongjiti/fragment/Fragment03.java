package com.example.zhongjiti.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.zhongjiti.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */

public class Fragment03 extends Fragment{
    private TabLayout tablayout;
    private ViewPager viewpager;
    private List<String> list;
    private LayoutInflater mInflater;
    private WeakReference<View> mRootView = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (inflater != null)
            mInflater = inflater;
        else
            mInflater = LayoutInflater.from(getActivity());
        View rootView = mRootView == null ? null : mRootView.get();
        if (rootView != null) {
            final ViewParent parent = rootView.getParent();
            if (parent != null && parent instanceof ViewGroup)
                ((ViewGroup) parent).removeView(rootView);
        }
        else {
            rootView = mInflater.inflate(R.layout.fragment03, null, false);
            mRootView = new WeakReference<View>(rootView);
        }
        tablayout=rootView.findViewById(R.id.tablayout);
        viewpager=rootView.findViewById(R.id.viewpager);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list=new ArrayList<>();
        list.add("精选");
        list.add("英伦风");
        list.add("直播");
        list.add("订阅");
        list.add("视频购");
        list.add("问答");

        viewpager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public CharSequence getPageTitle(int position) {
                return list.get(position);
            }

            @Override
            public Fragment getItem(int position) {
                Fragment fragment=null;
                switch (position){
                    case 0:
                        fragment= new Fragment_jingxuan();
                        break;
                    case 1:
                        fragment=new Fragmrnt_yinglunfeng();
                        break;
                    case 2:
                        fragment=new Fragmrnt_zhibo();
                        break;
                    case 3:
                        fragment=new Fragment_dingyue();
                        break;
                    case 4:
                        fragment=new Fragment_shipingou();
                        break;
                    case 5:
                        fragment=new Fragmrnt_wenda();
                        break;
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        //tab和ViewPager关联
        tablayout.setupWithViewPager(viewpager);
    }

}
