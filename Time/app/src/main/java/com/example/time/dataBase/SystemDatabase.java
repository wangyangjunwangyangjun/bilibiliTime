package com.example.time.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SystemDatabase extends SQLiteOpenHelper {
    public SystemDatabase(Context context) {
        super(context, "systemDataBase", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //夜间模式
        //摇一摇
        //登录状态
        String sql = "CREATE TABLE system (\n" +
                "    night INT,\n" +
                "    yaoYiYao INT,\n" +
                "    loginStatus INT)";
        sqLiteDatabase.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
