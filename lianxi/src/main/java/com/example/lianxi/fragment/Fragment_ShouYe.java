package com.example.lianxi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lianxi.R;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by 张峰 on 2017/10/23.
 */

public class Fragment_ShouYe extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shouye_layout, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.tab);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list = new ArrayList<>();
        list.add("沪深");
        list.add("板块");
        list.add("指数");
        list.add("港股");
        list.add("新三板");
        list.add("商品");
        list.add("更多");
        //设置适配器
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public CharSequence getPageTitle(int position) {
                return list.get(position);
            }

            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new Fragment_HuSheng();
                        break;
                    case 1:
                        fragment = new Fragment_BanKuai();
                        break;
                    case 2:
                        fragment = new Fragment_ZhiShu();
                        break;
                    case 3:
                        fragment = new Fragment_GangGu();
                        break;
                    case 4:
                        fragment = new Fragment_XinSanBan();
                        break;
                    case 5:
                        fragment = new Fragment_ShangPin();
                        break;
                    case 6:
                        fragment = new Fragment_GengDuo();
                        break;

                }
                return fragment;
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        tabLayout.setupWithViewPager(viewPager);

    }
}
