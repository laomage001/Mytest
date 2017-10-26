package com.example.day_13demo1;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.day_13demo1.view.XListView;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements XListView.IXListViewListener {

    private DrawerLayout drawerLayout;
    private XListView xListView;
    private RelativeLayout relativeLayout;
    private ImageView imageView;
    private ListView listView;
    private String pathBody = "http://www.yulin520.com/a2a/impressApi/news/mergeList?pageSize=10&page=";
    private int page_num;
    private List<DataDataBean.DataBean> list = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();

        xListView.setPullRefreshEnable(true);
        xListView.setPullLoadEnable(true);
        xListView.setXListViewListener(this);

        relativeLayout.setBackgroundColor(Color.WHITE);
        List<String> drawerList = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            drawerList.add("Item"+(i+1));
        }

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, drawerList));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(relativeLayout);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                drawerLayout.closeDrawer(relativeLayout);
            }
        });

        page_num = 1;
        getDataFromNet(pathBody + page_num, "refresh");
    }

    private void getDataFromNet(final String path, final String operate) {
        new AsyncTask<Void, Void, String>(){

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);

                    int responseCode = connection.getResponseCode();
                    if(responseCode == 200){
                        InputStream inputStream = connection.getInputStream();
                        String json = streamToString(inputStream, "utf-8");
                        return json;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                DataDataBean dataDataBean = gson.fromJson(s, DataDataBean.class);
                List<DataDataBean.DataBean> data = dataDataBean.getData();
                if(operate.equals("refresh")){
                    list.addAll(0, data);
                    setAdapter();
                    xListView.stopRefresh();
                    Date date = new Date(System.currentTimeMillis());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                    xListView.setRefreshTime(simpleDateFormat.format(date));
                }else{
                    list.addAll(data);
                    setAdapter();
                    xListView.stopLoadMore();
                }
            }
        }.execute();
    }

    private void setAdapter() {
        if(adapter == null){
            adapter = new MyAdapter(MainActivity.this, list);
            xListView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }


    private void findView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        xListView = (XListView) findViewById(R.id.x_list_view);
        relativeLayout = (RelativeLayout) findViewById(R.id.drawer_relative);
        imageView = (ImageView) findViewById(R.id.drawer_image_view);
        listView = (ListView) findViewById(R.id.drawer_list_view);
    }

    @Override
    public void onRefresh() {
        page_num++;
        getDataFromNet(pathBody + page_num, "refresh");
    }

    @Override
    public void onLoadMore() {
        getDataFromNet(pathBody + 1, "load");
    }

    private String streamToString(InputStream inputStream,String charset) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,charset);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String s = null;
            StringBuilder builder = new StringBuilder();
            while ((s = bufferedReader.readLine()) != null){
                builder.append(s);
            }
            bufferedReader.close();
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

}
