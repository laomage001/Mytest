package com.example.thirdweekexamdemo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class JsonDao {

    private MyHelper myHelper;

    public JsonDao(Context context) {

        myHelper = new MyHelper(context);
    }

   //插入
    public void insertJson(String json,String url){

        SQLiteDatabase database = myHelper.getWritableDatabase();
        //现根据url删除json
        database.delete("jsoncache","url = ?",new String[]{url});

        //再去添加
        ContentValues values = new ContentValues();
        values.put("json",json);
        values.put("url",url);

        database.insert("jsoncache",null,values);

        //关闭
        database.close();
    }


    //查询
    public String getJson(String url){

        SQLiteDatabase writableDatabase = myHelper.getWritableDatabase();

        Cursor cursor = writableDatabase.query("jsoncache", new String[]{"json"}, "url = ?", new String[]{url}, null, null, null);

        if (cursor.moveToNext()){
            String json = cursor.getString(cursor.getColumnIndex("json"));

            return json;
        }

        return null;
    }

}