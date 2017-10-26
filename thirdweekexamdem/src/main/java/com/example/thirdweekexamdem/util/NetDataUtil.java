package com.example.thirdweekexamdem.util;

import android.content.Context;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/10/24,0024.
 */
public class NetDataUtil {
    public static void getData(final String path, Context context, final JsonStringCallaBack callBack){
        if (NetWorkUtil.isConn(context)){
            AsyncTask< Void, Void, String > asyncTask = new AsyncTask< Void, Void, String >() {
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
                            String json = StringUtil.streamToString(inputStream,"utf-8");
                            return json;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
                @Override
                protected void onPostExecute(String s) {
                    //通过接口把json格式的字符串传递回去....
                    callBack.getJsonString(s);
                }
            };
            asyncTask.execute();
        }else {
            NetWorkUtil.showNoNetWorkDlg(context);
        }
    }
}
