package com.example.zhongjiti.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.zhongjiti.R;
import com.example.zhongjiti.adapter.Jingxuan_Adapter;
import com.example.zhongjiti.bean.DataDataBean3;
import com.example.zhongjiti.inter.JsonCallBack;
import com.example.zhongjiti.util.NetDataUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */

public class Fragment_jingxuan extends Fragment{
    private PullToRefreshScrollView refreshScrollView;
    private ListView listView;
    private List<DataDataBean3.ResultBean.ActSBean> list = new ArrayList<>();//记录当前展示的所有数据
    private int page_num = 1;
    private ILoadingLayout startLabels;
    private List<String> imageUrlList;
    private Jingxuan_Adapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_jingxuan,container,false);
        refreshScrollView = view.findViewById(R.id.refresh_scroll_view);
        listView = view.findViewById(R.id.mylistview1);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //失去焦点
        listView.setFocusable(false);


        //listView展示数据
        //1.获取网络数据,,,展示在listView上
        getDataFromNet();


        //2.设置刷新模式
        /*设置pullToRefreshListView的刷新模式，BOTH代表支持上拉和下拉，PULL_FROM_END代表上拉,PULL_FROM_START代表下拉 */
        refreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);


        //3.通过getLoadingLayoutProxy 方法来指定上拉和下拉时显示的状态的区别(也就是设置向下拉的时候头部里面显示的文字)
        //此时这里设置的是下拉刷新的时候显示的文字,所以第一个设置true表示现在是刷新,第二个设置为false
        startLabels = refreshScrollView.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新");
        startLabels.setRefreshingLabel("正在刷新...");
        startLabels.setReleaseLabel("放开刷新");


        ILoadingLayout endLabels = refreshScrollView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉刷新");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开刷新...");


        /**
         * 监听事件
         */
        refreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
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
    /**
     * 下拉刷新获取数据
     */
    private void getRefreshData() {
        NetDataUtil.getData(getActivity(),"http://op.juhe.cn/onebox/movie/video?key=90daf4317665b4ead0f7566e3cbb169d&q=%E5%BA%B7%E7%86%99%E7%8E%8B%E6%9C%9D",  new JsonCallBack() {




            @Override
            public void getJson(String json) {
                //解析
                Gson gson = new Gson();


                DataDataBean3 dataDataBean3 = gson.fromJson(json, DataDataBean3.class);
                //先清空一下数据
                list.clear();


                //添加到集合的最前边,,,,(0,,,,)
                list.addAll(0, dataDataBean3.getResult().getAct_s());



                //设置适配器
                setAdapter();


                //设置适配器之后停止刷新的操作
                refreshScrollView.onRefreshComplete();


                //可以设置刷新的时间....
                startLabels.setLastUpdatedLabel("上次更新时间:" + new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis())));//last最近的,最后一次update修改/更新
            }
        });
    }
    /**
     * 刚开始进入页面获取网络数据....还可以作为上拉加载获取数据的操作
     *
     *
     */
    private void getDataFromNet() {
        //第一个参数是接口,第二个上下文,第三个回调json数据
        NetDataUtil.getData(getActivity(),"http://op.juhe.cn/onebox/movie/video?key=90daf4317665b4ead0f7566e3cbb169d&q=%E5%BA%B7%E7%86%99%E7%8E%8B%E6%9C%9D" + page_num,  new JsonCallBack() {

            @Override
            public void getJson(String json) {
                //解析
                Gson gson = new Gson();


                DataDataBean3 dataDataBean3 = gson.fromJson(json, DataDataBean3.class);


                //往后面添加...
                list.addAll(dataDataBean3.getResult().getAct_s());

                //设置适配器
                setAdapter();

                //停止刷新
                refreshScrollView.onRefreshComplete();
            }
        });
    }
    private void setAdapter(){
        if(adapter==null){
            adapter = new Jingxuan_Adapter(getActivity(),list);
            listView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

}
