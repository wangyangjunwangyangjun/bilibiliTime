package com.example.time.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MainActivityDataBase extends SQLiteOpenHelper {
    
    public MainActivityDataBase(Context context) {
        super(context,"mainActivityDataBase",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建日程表格
        String sql = "CREATE TABLE schedules (\n" +
                "    name VARCHAR(20),\n" +
                //日程名称
                "    type VARCHAR(20),\n" +
                //日程类型
                "    level VARCHAR(20),\n" +
                //重要性
                "    message VARCHAR(20),\n" +
                //留言
                "    datetime DATETIME,\n"+
                //日程时间
                "    alarmClock DATETIME,\n"+
                "    alarmClockOpen DATETIME,\n"+
                //闹钟时间
                "    location VARCHAR(20),\n"+
                "    locationOpen VARCHAR(20),\n"+
                //日程地点
                "    photo blob)";
                //图片
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
