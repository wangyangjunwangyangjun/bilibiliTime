package com.example.time.budget;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.time.R;
import com.example.time.dataBase.DataBaseItem;
import com.example.time.dataBase.MyDatabase;

public class ActivityJiYiBi extends AppCompatActivity {
    private static final int OPEN_CANMER = 1;
    private RecycleViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ji_yi_bi);
        //授予权限
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(ActivityJiYiBi.this, Manifest.permission.CAMERA);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(ActivityJiYiBi.this,new String[]{Manifest.permission.CAMERA},OPEN_CANMER);
                return;
            }
        }
        //设置标题栏
        Toolbar toolbar = (Toolbar)findViewById(R.id.jiyibi_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //设置recycleview
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecycleViewAdapter(ActivityJiYiBi.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //新增加一条记录
        Button button = findViewById(R.id.button_baocun);
        button.setOnClickListener(view -> {
            MyDatabase myDatabase = new MyDatabase(ActivityJiYiBi.this,"yiji_db",null,1);
            SQLiteDatabase db = myDatabase.getWritableDatabase();
            ContentValues values = new ContentValues();
            DataBaseItem dataBaseItem = adapter.getBaseItem();
            values.put("name",dataBaseItem.getName());//2
            values.put("type",dataBaseItem.getType());//3
            values.put("time",dataBaseItem.getTime());//6
            values.put("costicon",dataBaseItem.getCosticon());//1
            values.put("cost",dataBaseItem.getCost());//4
            values.put("costdate",dataBaseItem.getCostdate());//5
            values.put("place",dataBaseItem.getPlace());//7
            values.put("note",dataBaseItem.getNote());//9
            values.put("image",dataBaseItem.getImage());//8
            db.insert("yiji", null, values);

            Intent intent = new Intent();
            intent.putExtra("name",dataBaseItem.getName());//2
            intent.putExtra("type",dataBaseItem.getType());//3
            intent.putExtra("time",dataBaseItem.getTime());//6
            intent.putExtra("costicon",dataBaseItem.getCosticon());//1
            intent.putExtra("cost",dataBaseItem.getCost());//4
            intent.putExtra("costdate",dataBaseItem.getCostdate());//5
            intent.putExtra("place",dataBaseItem.getPlace());//7
            intent.putExtra("note",dataBaseItem.getNote());//9
            intent.putExtra("image",dataBaseItem.getImage());//8
            setResult(1, intent);
            finish();
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case OPEN_CANMER:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ActivityJiYiBi.this, "授权成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityJiYiBi.this, "相机权限禁用了, 请务必开启相机权", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
