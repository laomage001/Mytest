package com.example.zhongjiti.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhongjiti.R;
import com.example.zhongjiti.bean.DataDataBean2;
import com.example.zhongjiti.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */

public class MyAdapter extends BaseAdapter{

    private Context context;
    private List<DataDataBean2.ResultBean.ListBean> list;

    public MyAdapter(Context context, List<DataDataBean2.ResultBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1   holder;
        if(convertView==null){
            holder= new ViewHolder1();
            convertView=View.inflate(context, R.layout.gridview_item,null);
            holder.imageview=convertView.findViewById(R.id.imageview);
            holder.textview=convertView.findViewById(R.id.textview);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder1) convertView.getTag();
        }
        holder.textview.setText(list.get(position).getTitle());
        if (list.get(position).getFirstImg() != null){
            //此时加载图片显示
            ImageLoader.getInstance().displayImage(list.get(position).getFirstImg(),holder.imageview, ImageLoaderUtil.getDefaultOption());
        }
        return convertView;
    }
    public class ViewHolder1{
        ImageView imageview;
        TextView textview;
    }
}
