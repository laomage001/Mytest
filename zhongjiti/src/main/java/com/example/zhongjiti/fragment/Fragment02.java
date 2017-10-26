package com.example.zhongjiti.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.zhongjiti.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */

public class Fragment02 extends Fragment{
    private ListView lv;
    private FrameLayout framlayout;
    private List<String> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment02,container,false);
        lv=view.findViewById(R.id.lv);
        framlayout=view.findViewById(R.id.framlayout);
        list=new ArrayList<String>();

        list.add("头条");
        list.add("社会");
        list.add("国内");
        list.add("国际");
        list.add("娱乐");
        list.add("体育");
        list.add("军事");
        list.add("科技");
        list.add("财经");
        list.add("时尚");
        lv.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,android.R.id.text1,list));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){

                    case 0:
                        getChildFragmentManager().beginTransaction().replace(R.id.framlayout,new Fragment_toutiao()).commit();
                        break;
                    case 1:
                        getChildFragmentManager().beginTransaction().replace(R.id.framlayout,new Fragment_shehui()).commit();
                        break;
                    case 2:
                        getChildFragmentManager().beginTransaction().replace(R.id.framlayout,new Fragment_guoji()).commit();
                        break;
                    case 3:
                        getChildFragmentManager().beginTransaction().replace(R.id.framlayout,new Fragment_guoji2()).commit();
                        break;
                    case 4:
                        getChildFragmentManager().beginTransaction().replace(R.id.framlayout,new Fragment_yule()).commit();
                        break;
                    case 5:
                        getChildFragmentManager().beginTransaction().replace(R.id.framlayout,new Fragment_tiyu()).commit();
                        break;
                    case 6:
                        getChildFragmentManager().beginTransaction().replace(R.id.framlayout,new Fragment_junshi()).commit();
                        break;
                    case 7:
                        getChildFragmentManager().beginTransaction().replace(R.id.framlayout,new Fragment_keji()).commit();
                        break;
                    case 8:
                        getChildFragmentManager().beginTransaction().replace(R.id.framlayout,new Fragment_caijing()).commit();
                        break;
                    case 9:
                        getChildFragmentManager().beginTransaction().replace(R.id.framlayout,new Fragment_shishang()).commit();
                        break;
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
