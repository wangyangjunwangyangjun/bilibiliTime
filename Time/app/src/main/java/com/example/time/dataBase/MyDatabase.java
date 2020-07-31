package com.example.time.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {

    public MyDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE yiji (\n" +
                "    name VARCHAR(20),\n" +
                "    type VARCHAR(20),\n" +
                "    time VARCHAR(20),\n" +
                "    costicon INT,\n" +
                "    cost DECIMAL(10 , 0 ),\n" +
                "    costdate DATE,\n" +
                "    place VARCHAR(20),\n" +
                "    note VARCHAR(50),\n" +
                "    image INT\n" + ")";
        sqLiteDatabase.execSQL(sql);

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
