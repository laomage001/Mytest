package com.example.zhongjiti.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.zhongjiti.R;
import com.example.zhongjiti.adapter.GridView_Adapter;
import com.example.zhongjiti.bean.DataDataBean;
import com.example.zhongjiti.inter.JsonCallBack;
import com.example.zhongjiti.util.ImageLoaderUtil;
import com.example.zhongjiti.util.NetDataUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */

public class Fragment01 extends Fragment{
    private PullToRefreshScrollView refreshScrollView;
    private ILoadingLayout startLabels;
    private int page_num = 1;
    private ImageView imageview;
    private GridView gridview;
    private GridView_Adapter adapter;
    private List<DataDataBean.ResultBean.DataBean> list = new ArrayList<>();
    private List<String > blist = new ArrayList<>();
    private int index = 0;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                //接到消息之后,,,切换图片显示
                index ++;
                ImageLoader.getInstance().displayImage(String.valueOf(list.get(index %list.size())),imageview, ImageLoaderUtil.getDefaultOption());
                //再次发送
                handler.sendEmptyMessageDelayed(0,3000);
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment01,container,false);
        refreshScrollView=view.findViewById(R.id.refresh_scroll_view);
        imageview=view.findViewById(R.id.imageview);
        gridview=view.findViewById(R.id.gridview);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        gridview.setFocusable(false);

        getDataFromNet();

        getFrom();

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

    public void getFrom() {
        NetDataUtil.getData(getActivity(), "http://apis.juhe.cn/cook/query?key=65481ae0fb17703c15a794aebaec93c5&menu=%E8%A5%BF%E7%BA%A2%E6%9F%BF&rn=11&pn=4", new JsonCallBack() {
            @Override
            public void getJson(String s) {
                //解析
                Gson gson = new Gson();
                DataDataBean dataDataBean = gson.fromJson(s, DataDataBean.class);

                List<DataDataBean.ResultBean.DataBean.StepsBean> steps = dataDataBean.getResult().getData().get(0).getSteps();

                for (DataDataBean.ResultBean.DataBean.StepsBean st: steps) {
                    blist.add(st.getImg());
                }

                //现在图片集合有数据,,,,展示图片,,,展示第一张图片
                ImageLoader.getInstance().displayImage(blist.get(0),imageview,ImageLoaderUtil.getDefaultOption());

                //发送延时消息
                handler.sendEmptyMessageDelayed(0,3000);
                    
                }
        });
    }
    /**
     * 下拉刷新获取数据
     */
    private void getRefreshData() {
        NetDataUtil.getData(getActivity(),"http://apis.juhe.cn/cook/query?key=65481ae0fb17703c15a794aebaec93c5&menu=%E8%A5%BF%E7%BA%A2%E6%9F%BF&rn=11&pn=4",  new JsonCallBack() {

            @Override
            public void getJson(String json) {
                //解析
                Gson gson = new Gson();

                DataDataBean dataDataBean4 = gson.fromJson(json, DataDataBean.class);
                //先清空一下数据
                list.clear();

                //添加到集合的最前边,,,,(0,,,,)
                list.addAll(0, dataDataBean4.getResult().getData());

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
     */
    private void getDataFromNet() {
        //第一个参数是接口,第二个上下文,第三个回调json数据
        NetDataUtil.getData(getActivity(),"http://apis.juhe.cn/cook/query?key=65481ae0fb17703c15a794aebaec93c5&menu=%E8%A5%BF%E7%BA%A2%E6%9F%BF&rn=11&pn=4" + page_num,  new JsonCallBack() {

            @Override
            public void getJson(String json) {
                //解析
                Gson gson = new Gson();


                DataDataBean dataDataBean = gson.fromJson(json, DataDataBean.class);


                //往后面添加...
                list.addAll(dataDataBean.getResult().getData());


                //设置适配器
                setAdapter();


                //停止刷新
                refreshScrollView.onRefreshComplete();
            }
        });
    }
    private void setAdapter(){
        if(adapter==null){
            adapter = new GridView_Adapter(getActivity(),list);
            gridview.setAdapter(adapter);


        }else{
            adapter.notifyDataSetChanged();
        }
    }
}