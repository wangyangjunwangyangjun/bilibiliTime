package com.example.time.budget;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.time.R;
import com.example.time.dataBase.DataBaseItem;
import com.example.time.dataBase.MyDatabase;

public class ModifDataBaseItemActivity extends AppCompatActivity {
    M_RecycleViewAdapter adapter;
    DataBaseItem dataBaseItem_orign;
    DataBaseItem dataBaseItem_new;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ji_yi_bi);
        Toolbar toolbar = (Toolbar)findViewById(R.id.jiyibi_toolbar);
        //设置标题栏
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String type = intent.getStringExtra("type");
        String beizhu = intent.getStringExtra("note");
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");
        double cost = intent.getDoubleExtra("cost",0);
        String costdate = intent.getStringExtra("costdate");
        String place = intent.getStringExtra("place");
        String note = intent.getStringExtra("note");
        int image = intent.getIntExtra("image",0);
        int costicon = intent.getIntExtra("costicon",0);

        Log.i("WYJ","here");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        dataBaseItem_orign = new DataBaseItem(name,type,time,costicon,cost,costdate,place,note,image);
        dataBaseItem_new = new DataBaseItem(name,type,time,costicon,cost,costdate,place,note,image);
        adapter = new M_RecycleViewAdapter(ModifDataBaseItemActivity.this,dataBaseItem_new);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Button button = findViewById(R.id.button_baocun);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabase myDatabase = new MyDatabase(ModifDataBaseItemActivity.this,"yiji_db",null,1);
                SQLiteDatabase db = myDatabase.getWritableDatabase();
                db.execSQL("update yiji set note = ?,time = ?,cost = ?,costdate = ?,place = ?,name = ?" +
                                "where name = ? and type = ? and time = ? and cost = ? and costdate = ?",
                        new Object[]{dataBaseItem_new.getNote(),dataBaseItem_new.getTime(),dataBaseItem_new.getCost(),dataBaseItem_new.getCostdate(),dataBaseItem_new.getPlace(),dataBaseItem_new.getName(),
                                dataBaseItem_orign.getName(),dataBaseItem_orign.getType(),dataBaseItem_orign.getTime(),dataBaseItem_orign.getCost(),dataBaseItem_orign.getCostdate()});
                db.close();
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
