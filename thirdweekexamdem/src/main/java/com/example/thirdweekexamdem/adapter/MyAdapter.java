package com.example.thirdweekexamdem.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thirdweekexamdem.R;
import com.example.thirdweekexamdem.bean.DataDataBean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/24,0024.
 */
public class MyAdapter extends BaseAdapter{
    private int Image_LEFT = 0;
    private int Image_RIGHT = 1;
    Context context;
    List<DataDataBean.ResultsBean> list;
    public MyAdapter(Context context, List<DataDataBean.ResultsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //重写两个方法
    @Override
    public int getViewTypeCount() {
        return 2;
    }
    @Override
    public int getItemViewType(int position) {
        if(position%2 == 0){
            return Image_LEFT;
        }
        return Image_RIGHT;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //先判断getItemViewType是用哪个视图
        if(getItemViewType(i) ==Image_LEFT){
            LeftHolder holder;
            if(view == null){
                holder = new LeftHolder();
                view = View.inflate(context, R.layout.list_item_left,null);
                holder.iv = view.findViewById(R.id.iv);
                holder.tv = view.findViewById(R.id.tv);
                view.setTag(holder);
            }else{
                holder = (LeftHolder) view.getTag();
            }
            holder.tv.setText(list.get(i).getDesc());
        //    ImageLoader.getInstance().displayImage(list.get(i).getImages().get(i),holder.iv, ImageLoaderUtil.getDefaultOption());
        }else if(getItemViewType(i) == Image_RIGHT){
            RightHolder holder;
            if(view == null){
                holder = new RightHolder();
                view = View.inflate(context, R.layout.list_item_right,null);
                holder.iv = view.findViewById(R.id.iv);
                holder.tv = view.findViewById(R.id.tv);
                view.setTag(holder);
            }else{
                holder = (RightHolder) view.getTag();
            }
            holder.tv.setText(list.get(i).getDesc());
//ImageLoader找图片 写一个ImageLoaderUtil的类，即ImageLoader的控制类 ImageLoader.getInstance().displayImage(list.get(i).getImg(),holder.iv, ImageLoaderUtil.getDefaultOption());
        }
        return view;
    }
    //有几种排列方式就写几个ViewHolder(就比如下面的两个方法，也有几个XML)
    public class LeftHolder{
        ImageView iv;
        TextView tv;
    }
    public class RightHolder{
        ImageView iv;
        TextView tv;
    }
}
