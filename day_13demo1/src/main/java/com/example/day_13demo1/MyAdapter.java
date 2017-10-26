package com.example.day_13demo1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2017/10/14.
 */


public class MyAdapter extends BaseAdapter {

    private Context context;
    private List<DataDataBean.DataBean> list;
    private final int NORMAL = 0;
    private final int ONLY_TEXT = 1;

    public MyAdapter(Context context, List<DataDataBean.DataBean> list) {
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
        if(position%2==1){
            return NORMAL;
        }
        return ONLY_TEXT;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int type = getItemViewType(i);
        if(type == NORMAL){
            NormalViewHolder holder;
            if(view == null){
                view = View.inflate(context, R.layout.xlistview_normal_item, null);
                holder = new NormalViewHolder();
                holder.imageView = view.findViewById(R.id.x_list_view_image_view);
                holder.textView1 = view.findViewById(R.id.x_list_view_text_view_1);
                holder.textView2 = view.findViewById(R.id.x_list_view_text_view_2);
                view.setTag(holder);
            }else{
                holder = (NormalViewHolder) view.getTag();
            }

            ImageLoader.getInstance().displayImage(list.get(i).getUserImg(), holder.imageView, ImageLoaderUtil.display());
            holder.textView1.setText(list.get(i).getTitle());
            String s = formatDate(list.get(i).getTopTime());
            holder.textView2.setText(s);

        }else{
            OnlyTextViewHolder holder;
            if(view == null){
                view = View.inflate(context, R.layout.xlistview_onlytext_item, null);
                holder = new OnlyTextViewHolder();
                holder.textView1 = view.findViewById(R.id.x_list_view_text_view_1);
                holder.textView2 = view.findViewById(R.id.x_list_view_text_view_2);
                view.setTag(holder);
            }else{
                holder = (OnlyTextViewHolder) view.getTag();
            }

            holder.textView1.setText(list.get(i).getTitle());
            String s = formatDate(list.get(i).getTopTime());
            holder.textView2.setText(s);

        }
        return view;
    }

    private class NormalViewHolder{
        ImageView imageView;
        TextView textView1;
        TextView textView2;
    }

    private class OnlyTextViewHolder{
        TextView textView1;
        TextView textView2;
    }

    private String formatDate(Long time){
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        return format;
    }

}
