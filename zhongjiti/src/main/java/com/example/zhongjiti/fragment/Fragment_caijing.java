package com.example.zhongjiti.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ScrollView;

import com.example.zhongjiti.R;
import com.example.zhongjiti.adapter.MyAdapter;
import com.example.zhongjiti.adapter.ViewPagerAdapter;
import com.example.zhongjiti.bean.DataDataBean2;
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

public class Fragment_caijing extends Fragment{
    private PullToRefreshScrollView refreshScrollView;
    private ViewPager viewPager;
    private GridView gridview;
    private List<DataDataBean2.ResultBean.ListBean> list = new ArrayList<>();//记录当前展示的所有数据
    private int page_num = 1;
    private ILoadingLayout startLabels;
    private List<String> imageUrlList;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                //显示下一页....拿到当前页+1
                viewPager.setCurrentItem(viewPager.getCurrentItem() +1);


                //再次发送消息
                handler.sendEmptyMessageDelayed(0,2000);
            }
        }
    };
    private MyAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_caijing,container,false);
        refreshScrollView = view.findViewById(R.id.refreshscrollview);
        viewPager = view.findViewById(R.id.view_pager);
        gridview = view.findViewById(R.id.mylistview);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //失去焦点
        gridview.setFocusable(false);

        //轮播图
        lunBoTu();

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
                getDataFromNet();
            }
        });
    }
    /**
     * 轮播图的方法
     */
    private void lunBoTu() {
        NetDataUtil.getData(getActivity(),"http://v.juhe.cn/weixin/query?key=88f7bbc507e3ecacfaeab2b47dd8936f", new JsonCallBack() {
            @Override
            public void getJson(String json) {
                //这个结合记录轮播图的所有地址
                imageUrlList = new ArrayList<String>();


                //解析数据
                Gson gson = new Gson();


                DataDataBean2 lunBoBean = gson.fromJson(json, DataDataBean2.class);


                List<DataDataBean2.ResultBean.ListBean> essay = lunBoBean.getResult().getList();


                for (DataDataBean2.ResultBean.ListBean essayBean : essay) {
                    //essayBean.getAuthor().get(0).getWeb_url()
                    imageUrlList.add(essayBean.getFirstImg());
                }


                //此时应该根据图片的路径,加载图片,设置适配器
                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity(), imageUrlList);
                viewPager.setAdapter(viewPagerAdapter);


                //1.手动可以无限滑动....maxValue....把当前开始展示的位置放在足够大的某个位置
                viewPager.setCurrentItem(imageUrlList.size() * 100000);


                //2.自动轮播
                handler.sendEmptyMessageDelayed(0, 2000);
            }
        });
    }
    /**
     * 下拉刷新获取数据
     */
    private void getRefreshData() {
        NetDataUtil.getData(getActivity(),"http://v.juhe.cn/weixin/query?key=88f7bbc507e3ecacfaeab2b47dd8936f",  new JsonCallBack() {




            @Override
            public void getJson(String json) {
                //解析
                Gson gson = new Gson();


                DataDataBean2 dataDataBean4 = gson.fromJson(json, DataDataBean2.class);
                //先清空一下数据
                list.clear();


                //添加到集合的最前边,,,,(0,,,,)
                list.addAll(0, dataDataBean4.getResult().getList());


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
        NetDataUtil.getData(getActivity(),"http://v.juhe.cn/weixin/query?key=88f7bbc507e3ecacfaeab2b47dd8936f", new JsonCallBack() {




            @Override
            public void getJson(String json) {
                //解析
                Gson gson = new Gson();

                DataDataBean2 dataDataBean = gson.fromJson(json, DataDataBean2.class);

                //往后面添加...
                list.addAll(dataDataBean.getResult().getList());

                //设置适配器
                setAdapter();


                //停止刷新
                refreshScrollView.onRefreshComplete();
            }
        });
    }
    private void setAdapter(){
        if(adapter==null){
            adapter = new MyAdapter(getActivity(),list);
            gridview.setAdapter(adapter);


        }else{
            adapter.notifyDataSetChanged();
        }
    }
}