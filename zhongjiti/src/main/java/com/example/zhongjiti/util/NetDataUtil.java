package com.example.zhongjiti.util;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.zhongjiti.inter.JsonCallBack;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;





public class NetDataUtil {
    /**
     * 获取数据之前先判断网络状态
     *
     */
    public static void getData(Context context,final String path, final JsonCallBack callaBack) {
        if (NetWorkUtil.isConn(context)){
            Toast.makeText(context,"网络可用",Toast.LENGTH_SHORT).show();

            AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... voids) {
                    try {
                        URL url = new URL(path);

                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                        connection.setRequestMethod("GET");
                        connection.setReadTimeout(5000);
                        connection.setConnectTimeout(5000);

                        int responseCode = connection.getResponseCode();
                        if (responseCode == 200){
                            InputStream inputStream = connection.getInputStream();

                            String json = streamToString(inputStream,"utf-8");

                            return json;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(String json) {
                    //异步拿回这个json串,交给具体解析的那个类
                    callaBack.getJson(json);
                }
            };

            asyncTask.execute();
        }else {
            NetWorkUtil.showNoNetWorkDlg(context);
        }


    }


    private static String streamToString(InputStream inputStream, String charset) {
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
