package com.example.thirdweekexamdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thirdweekexamdemo.R;
import com.example.thirdweekexamdemo.bean.DataDataBean;
import com.example.thirdweekexamdemo.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/10/16,0016.
 */
public class MyAdapter extends BaseAdapter{
    private Context context;
    private List<DataDataBean.ResultsBean> list;
    private int TEXT_ONLY = 0;
    private int IMAGE_TEXT = 1;
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
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position%2 == 0){
            return IMAGE_TEXT;
        }
        return TEXT_ONLY;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (getItemViewType(i) == IMAGE_TEXT){
            ImageHolder holder;
            if (view == null){
                view = View.inflate(context, R.layout.item_iamge,null);
                holder = new ImageHolder();
                holder.tv = view.findViewById(R.id.tv);
                holder.iv = view.findViewById(R.id.iv);
                view.setTag(holder);
            }else {
                holder = (ImageHolder) view.getTag();
            }
            holder.tv.setText(list.get(i).getDesc());
            //图片
//            Log.i("----",list.get(i).getImages().get(0)+"?imageView2/0/w/100");
            ImageLoader.getInstance().displayImage(list.get(i).getUrl(),holder.iv, ImageLoaderUtil.getDefaultOption());
            //Picasso.with(context).load(list.get(0).getImages().get(i)).into(holder.iv);
        }else if (getItemViewType(i)==TEXT_ONLY){

            TextHolder holder;
            if (view == null){
                view = View.inflate(context, R.layout.item_text,null);
                holder = new TextHolder();
                holder.tv = view.findViewById(R.id.tv);
                view.setTag(holder);
            }else {
                holder = (TextHolder) view.getTag();
            }
            holder.tv.setText(list.get(i).getDesc());
           }
        return view;
    }

    private class TextHolder{
        TextView tv;
    }

    private class ImageHolder{
        TextView tv;
        ImageView iv;
    }
}
