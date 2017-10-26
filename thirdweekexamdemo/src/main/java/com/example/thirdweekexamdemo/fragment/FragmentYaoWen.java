package com.example.thirdweekexamdemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.thirdweekexamdemo.R;
import com.example.thirdweekexamdemo.adapter.ListViewAdapter;
import com.example.thirdweekexamdemo.adapter.VpAdapter;
import com.example.thirdweekexamdemo.bean.DataDataBean;
import com.example.thirdweekexamdemo.bean.LunBo;
import com.example.thirdweekexamdemo.util.JsonStringCallaBack;
import com.example.thirdweekexamdemo.util.NetDataUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FragmentYaoWen extends Fragment   {
    ViewPager vp;
    private List<String> imageUrlList;
    private List<DataDataBean.ResultsBean> list = new ArrayList<>();
    //一个handler机制
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                //viewPager显示下一页
                vp.setCurrentItem(vp.getCurrentItem() +1);
                //再次发送延时消息
                handler.sendEmptyMessageDelayed(0,2000);
            }
        }
    };
    private PullToRefreshScrollView pull_lv;
    private ListView lv;
    private int page_num;
    private ILoadingLayout startLabels;
    private ListViewAdapter listViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_yaowen,container,false);
        vp = view.findViewById(R.id.vp);
        pull_lv = view.findViewById(R.id.pull_lv);
        lv = view.findViewById(R.id.scroll_list);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //失去焦点
        lv.setFocusable(false);
        //轮播图
        getLunBoFromNet();
        //1.获取网络数据
        getDataFromNet();
        //2.设置刷新模式
        pull_lv.setMode(PullToRefreshBase.Mode.BOTH);
        //3.通过getLoadingLayoutProxy 方法来指定上拉和下拉时显示的状态的区别(也就是设置向下拉的时候头部里面显示的文字)
        startLabels = pull_lv.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新");
        startLabels.setRefreshingLabel("正在刷新...");
        startLabels.setReleaseLabel("放开刷新");

        ILoadingLayout endLabels = pull_lv.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉刷新");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开刷新...");

        pull_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //下拉刷新的方法，，可以借鉴地址：            //(http://blog.csdn.net/biggrand/article/details/78206922)
                getRefreshData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //上拉加载的方法，，可以借鉴地址：            //(http://blog.csdn.net/biggrand/article/details/78206922)
                page_num++;
                getDataFromNet();
            }
        });

        vp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
    }

    private void getLunBoFromNet() {
        NetDataUtil.getData(getActivity(), "http://v3.wufazhuce.com:8000/api/reading/index/?version=3.5.0&platform=android", new JsonStringCallaBack() {
            @Override
            public void getJsonString(String json) {
                //这个结合记录轮播图的所有地址
                imageUrlList = new ArrayList<>();
                Gson gson = new Gson();
                LunBo lunBoBean = gson.fromJson(json, LunBo.class);
                List<LunBo.DataBean.EssayBean> essay = lunBoBean.getData().getEssay();
                for (LunBo.DataBean.EssayBean essayBean: essay) {
                    //essayBean.getAuthor().get(0).getWeb_url()
                    imageUrlList.add(essayBean.getAuthor().get(0).getWeb_url());
                }
                //此时应该根据图片的路径,加载图片,设置适配器
                VpAdapter adapter = new VpAdapter(getActivity(), imageUrlList,handler);
                vp.setAdapter(adapter);
                //1.手动可以无限滑动....maxValue....把当前开始展示的位置放在足够大的某个位置
                vp.setCurrentItem(imageUrlList.size()*100000);
                //2.自动轮播
                handler.sendEmptyMessageDelayed(0,2000);
            }

        });
    }
    //下拉刷新
    private void getRefreshData() {
        NetDataUtil.getData( getActivity(),"http://gank.io/api/data/Android/10/1", new JsonStringCallaBack() {
            @Override
            public void getJsonString(String json) {
                Gson gson = new Gson();
                DataDataBean dataDataBean = gson.fromJson(json, DataDataBean.class);
                //先清空一下数据
                list.clear();
                //添加到集合的最前边,,,,(0,,,,)
                list.addAll(0, dataDataBean.getResults());
                //设置适配器
                setAdapter();
                //设置适配器之后停止刷新的操作
                pull_lv.onRefreshComplete();
                //可以设置刷新的时间....
                startLabels.setLastUpdatedLabel("上次更新时间:" + new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis())));//last最近的,最后一次update修改/更新
            }
        });
    }
    private void getDataFromNet() {
        //第一个参数是接口,第二个上下文,第三个回调json数据
        NetDataUtil.getData( getActivity(),"http://gank.io/api/data/Android/10/"+page_num, new JsonStringCallaBack() {
            @Override
            public void getJsonString(String json) {
                //解析
                Gson gson = new Gson();
                DataDataBean dataDataBean = gson.fromJson(json, DataDataBean.class);
                //往后面添加...
                list.addAll(dataDataBean.getResults());
                //设置适配器
                setAdapter();
                //停止刷新
                pull_lv.onRefreshComplete();
            }
        });
    }
    private void setAdapter() {
        //条目的点击事件

        if (listViewAdapter == null){
            listViewAdapter = new ListViewAdapter(getActivity(),list);
            lv.setAdapter(listViewAdapter);
        }else {
            listViewAdapter.notifyDataSetChanged();
        }
    }
}
