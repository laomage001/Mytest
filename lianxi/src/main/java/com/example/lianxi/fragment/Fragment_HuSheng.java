package com.example.lianxi.fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.lianxi.R;
import com.example.lianxi.adapter.ListViewAdapter;
import com.example.lianxi.bean.HuShengBean;
import com.example.lianxi.inter.JsonStringCallaBack;
import com.example.lianxi.util.NetDataUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class Fragment_HuSheng extends Fragment{

    private PullToRefreshScrollView pullToRefreshScrollView;
    private List<HuShengBean.DataBean> list = new ArrayList<>();
    private ListView listView;
    private ListViewAdapter listViewAdapter;
    private ILoadingLayout startLabels;
    private int page_num = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.husheng_layout, container, false);
        pullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.refresh_scroll_view);
        listView = (ListView) view.findViewById(R.id.scroll_list_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //失去焦点
        listView.setFocusable(false);
        //获取数据....解析的方法
        getDataFromNet();
        //设置刷新模式
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);

        //3.通过getLoadingLayoutProxy 方法来指定上拉和下拉时显示的状态的区别(也就是设置向下拉的时候头部里面显示的文字)
        //此时这里设置的是下拉刷新的时候显示的文字,所以第一个设置true表示现在是刷新,第二个设置为false
        startLabels = pullToRefreshScrollView.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新");
        startLabels.setRefreshingLabel("正在刷新...");
        startLabels.setReleaseLabel("放开刷新");

        ILoadingLayout endLabels = pullToRefreshScrollView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉刷新");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开刷新...");

        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getRefreshData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page_num++;
                getDataFromNet();
            }
        });
    }

    private void getRefreshData() {
        NetDataUtil.getData(getActivity(), "http://mnews.gw.com.cn/wap/data/news/xbsjxw/page_1.json",new JsonStringCallaBack() {
            @Override
            public void getJsonString(String json) {
                Gson gson = new Gson();
                try {
                    JSONArray js = new JSONArray(json);
                    HuShengBean huShengBean = gson.fromJson(js.get(0).toString(), HuShengBean.class);
                    List<HuShengBean.DataBean> data = huShengBean.getData();
                    Log.i("=============",data.toString());

                    //先清空一下数据
                    list.clear();
                    //添加到集合的最前边,,,,(0,,,,)
                    list.addAll(0,huShengBean.getData());
                    list.addAll(data);
                    //设置适配器
                    setAdater();
                    //设置适配器之后停止刷新的操作
                    pullToRefreshScrollView.onRefreshComplete();

                    //可以设置刷新的时间....
                    startLabels.setLastUpdatedLabel("上次更新时间:"+new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis())));//last最近的,最后一次update修改/更新


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void getDataFromNet() {
        NetDataUtil.getData(getActivity(), "http://mnews.gw.com.cn/wap/data/news/xbsjxw/page_1.json",new JsonStringCallaBack() {
            @Override
            public void getJsonString(String json) {

                // Log.i("=============",json);
                Gson gson = new Gson();
                try {
                    JSONArray js = new JSONArray(json);
                    HuShengBean huShengBean = gson.fromJson(js.get(0).toString(), HuShengBean.class);
                    List<HuShengBean.DataBean> data = huShengBean.getData();

                    list.addAll(data);
                    //设置适配器
                    setAdater();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setAdater() {
        if (listViewAdapter == null){

            listViewAdapter = new ListViewAdapter(getActivity(),list);
            listView.setAdapter(listViewAdapter);
        }else {
            listViewAdapter.notifyDataSetChanged();
        }
        pullToRefreshScrollView.onRefreshComplete();
    }
}
