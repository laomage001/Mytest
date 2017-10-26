package com.example.thirdweekexamdem.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thirdweekexamdem.R;
import com.example.thirdweekexamdem.adapter.MyAdapter;
import com.example.thirdweekexamdem.bean.DataDataBean;
import com.example.thirdweekexamdem.util.JsonStringCallaBack;
import com.example.thirdweekexamdem.util.NetDataUtil;
import com.example.thirdweekexamdem.view.XListView;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/24,0024.
 */
public class FragmentTuiJian extends Fragment implements XListView.IXListViewListener {
    private List< DataDataBean.ResultsBean> list = new ArrayList<>();//定义一个int值记录第几页
    private int page_num = 1;
    private XListView xlv;
    private MyAdapter myAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_tuijian,container,false);
        xlv = view.findViewById(R.id.xlv);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        xlv.setPullRefreshEnable(true);//支持下拉刷新
        xlv.setPullLoadEnable(true);//支持上拉加载更多
        xlv.setXListViewListener(this);//设置xlv的监听事件
        getDataFromNet();
    }

    private void getDataFromNet() {
        NetDataUtil.getData("http://gank.io/api/data/Android/10/"+page_num,getActivity(), new JsonStringCallaBack() {
            @Override
            public void getJsonString(String json) {
                //在这里解析
                Gson gson = new Gson();
                DataDataBean dataDataBean = gson.fromJson(json, DataDataBean.class);
                list.addAll(dataDataBean.getResults());
                //设置适配器
                setAdapter();
                //停止加载
                xlv.stopLoadMore();
            }
        });
    }

    @Override
    public void onRefresh() {
        NetDataUtil.getData("http://gank.io/api/data/Android/10/"+page_num,getActivity(), new JsonStringCallaBack() {
            @Override
            public void getJsonString(String json) {
                //在这里解析
                Gson gson = new Gson();
                DataDataBean dataDataBean = gson.fromJson(json, DataDataBean.class);
                list.addAll(0,dataDataBean.getResults());
                //设置适配器
                setAdapter();
                //停止加载
                xlv.stopRefresh();
                //System.currentTimeMillis()….当前时间的long类型的值
                Date date = new Date(System.currentTimeMillis());
                //格式化….yyyy-MM-dd HH:mm
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                //设置本次刷新的时间
                xlv.setRefreshTime(simpleDateFormat.format(date));
            }
        });
    }

    @Override
    public void onLoadMore() {
       // page_num++;
        getDataFromNet();
    }
    private void setAdapter() {
        if (myAdapter == null){

            myAdapter = new MyAdapter(getActivity(), list);
            xlv.setAdapter(myAdapter);
        }else {
            myAdapter.notifyDataSetChanged();
        }
    }
}
