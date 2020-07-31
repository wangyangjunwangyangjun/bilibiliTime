package com.example.time.contentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.time.dataBase.MainActivityDataBase;
import com.example.time.dataBase.SystemDatabase;

public class scheduleProvider extends ContentProvider {

    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        matcher.addURI("com.example.time.contentProvider.scheduleProvider","/schedules",1);
        matcher.addURI("com.example.time.contentProvider.scheduleProvider","/schedules/#",2);
    }
    private MainActivityDataBase mainActivityDataBase;

    @Override
    public boolean onCreate() {
        Log.e("TAG","scheduleProvider onCreate()");
        mainActivityDataBase = new MainActivityDataBase(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.e("TAG","scheduleProvider query()");
        //得到链接对象
        SQLiteDatabase database = mainActivityDataBase.getReadableDatabase();
        int code = matcher.match(uri);
        if(code==1){
            //不跟据id进行查询
            Cursor cursor = database.query("schedules",projection,selection,selectionArgs,null,null,sortOrder);
            return cursor;
        }else if(code==2){
            //根据id进行查询
            long id = ContentUris.parseId(uri);
            Cursor cursor;
            cursor = database.query("schedules",projection,null,null,null,null,null);;
            return cursor;
        }else{
            throw new RuntimeException("查询的uri不合法");
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.e("TAG","scheduleProvider getType()");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.e("TAG","scheduleProvider insert()");
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        Log.e("TAG","scheduleProvider delete()");
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        Log.e("TAG","scheduleProvider update()");
        return 0;
    }
}
