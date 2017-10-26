package com.example.thirdweekexamdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thirdweekexamdemo.R;
import com.example.thirdweekexamdemo.adapter.MyAdapter;
import com.example.thirdweekexamdemo.bean.DataDataBean;
import com.example.thirdweekexamdemo.dao.JsonDao;
import com.example.thirdweekexamdemo.util.JsonStringCallaBack;
import com.example.thirdweekexamdemo.util.NetDataUtil;
import com.example.thirdweekexamdemo.view.XListView;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/17,0017.
 */
public class FragmentTouTiao extends Fragment implements XListView.IXListViewListener {
    private XListView xlv;
    private List<DataDataBean.ResultsBean> list = new ArrayList<>();
    private MyAdapter adapter;
    private int page_num = 1;
    JsonDao jsonDao;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_toutiao,container,false);
        xlv = view.findViewById(R.id.xlv);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        jsonDao = new JsonDao(getContext());
        //1.先读取数据库中存的数据....有数据,解析展示....无数据,网络获取数据
        String json = jsonDao.getJson("http://gank.io/api/data/Android/10/" + page_num);
        if (json != null){
            //解析 显示
            Gson gson = new Gson();
            DataDataBean dataDataBean = gson.fromJson(json, DataDataBean.class);
            //往后面添加...
            list.addAll(dataDataBean.getResults());
            //设置适配器
            setAdapter();
        }else {
            //1.设置listView的适配器
            getDataFromNet();
        }
        xlv.setPullLoadEnable(true);
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(this);

        //获取数据....解析
        getDataFromNet();
    }
    private void getDataFromNet() {

        NetDataUtil.getData(getActivity(), "http://gank.io/api/data/Android/10/"+page_num, new JsonStringCallaBack() {
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
    private void setAdapter() {
        if (adapter == null){
            adapter = new MyAdapter(getActivity(), list);
            xlv.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onRefresh() {
        getDataRefresh();
    }
    private void getDataRefresh() {
        //当列表下拉刷新时，接口的页数设置为1，清除原来的列表数据，请求网络
        NetDataUtil.getData(getActivity(), "http://gank.io/api/data/Android/10/1", new JsonStringCallaBack() {
            @Override
            public void getJsonString(String json) {
                //在这里解析
                Gson gson = new Gson();
                DataDataBean dataDataBean = gson.fromJson(json, DataDataBean.class);
                //先清除所有
                list.clear();
                //再添加
                list.addAll(0,dataDataBean.getResults());
                //设置适配器
                setAdapter();
                //停止刷新
                xlv.stopRefresh();
                //设置刷新时间
                String format = new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis()));
                xlv.setRefreshTime(format);
            }
        });
    }
    @Override
    public void onLoadMore() {
        page_num ++;
        getDataFromNet();
    }
}
