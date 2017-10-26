package com.example.thirdweekexamdem.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017/10/24,0024.
 */
public class StringUtil {
    static String streamToString(InputStream inputStream, String s) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,s);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String ss = null;
            StringBuilder builder = new StringBuilder();
            while ((ss = bufferedReader.readLine()) != null){
                builder.append(ss);
            }
            bufferedReader.close();
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
