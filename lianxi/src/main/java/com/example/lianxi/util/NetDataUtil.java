package com.example.lianxi.util;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.lianxi.inter.JsonStringCallaBack;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;






public class NetDataUtil {
    private static int page_num = 1;

    /**
     * 获取数据之前先判断网络状态
     *
     */
    public static void getData(Context context,final String path, final JsonStringCallaBack callaBack) {
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

                            /*//根据分类标识为不同的数据在sd卡目录下创建文件夹，用来存储不同类别的json文件

                            File folder = new File(Environment.getExternalStorageDirectory().getPath()+category);

                            if(!folder.exists()){
                                folder.mkdirs();
                            }

                            //根据当前展示的页数来创建一个json文件，因为这个接口对应的是一个json文件，所以我的思路是先把它读到内存中，然后再读成字符串解析

                            File file = new File(folder, "/" + page_num + ".json");
                            if(!file.exists()){
                                file.createNewFile();
                                BufferedInputStream input = new BufferedInputStream(inputStream);
                                BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
                                byte[] bytes = new byte[1024<<2];
                                int len = 0;
                                while((len = input.read(bytes)) != -1){
                                    output.write(bytes, 0, len);
                                }
                                input.close();
                                output.flush();
                                output.close();
                            }*/
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                            StringBuffer buffer = new StringBuffer();
                            String readline = null;
                            while((readline = bufferedReader.readLine()) != null){
                                buffer.append(readline);
                            }
                            bufferedReader.close();
                            return  buffer.toString();


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(String json) {
                    //异步拿回这个json串,交给具体解析的那个类
                    callaBack.getJsonString(json);
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
