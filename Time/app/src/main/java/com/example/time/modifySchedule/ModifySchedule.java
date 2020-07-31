package com.example.time.modifySchedule;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.time.AlarmClock;
import com.example.time.R;
import com.example.time.been.ScheduleRecord;
import com.example.time.circleImageView.RoundImageView;
import com.example.time.dataBase.MainActivityDataBase;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ModifySchedule extends AppCompatActivity implements View.OnClickListener {
    private Switch location;
    private ImageView photo;
    private ImageView ivSchedulePhoto;
    private TextView textView;
    private TextView moreDetails;
    private EditText alarm_clock_time_1;
    private EditText alarm_clock_time_2;
    private EditText alarm_clock_time_3;
    private RoundImageView submit;
    private String sname;
    private String sdatetime;
    private Switch b1;
    public static int BDLocation_CODE = 1;
    private byte[] newPhoto;
    private boolean getNewPhoto;
    private int isalarmClockOpen,islocationOpen;
    private String newAlarmClock = "未设置";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_schedule);
        bindView();
        getDataBaseData();
        setListener();
    }
    public void bindView(){
        location = findViewById(R.id.b2);
        photo = findViewById(R.id.photoButton);
        ivSchedulePhoto = findViewById(R.id.ivSchedulePhoto);
        submit = findViewById(R.id.go);
        textView = findViewById(R.id.textView);
        alarm_clock_time_1 = findViewById(R.id.alarm_clock_time_1);
        alarm_clock_time_2 = findViewById(R.id.alarm_clock_time_2);
        alarm_clock_time_3 = findViewById(R.id.alarm_clock_time_3);
        moreDetails = findViewById(R.id.moreDetails);
        b1 = findViewById(R.id.b1);
        getNewPhoto = false;
        moreDetails.setText(sname);
        sname = getIntent().getStringExtra("name");
        sdatetime = getIntent().getStringExtra("datetime");
    }
    private void getDataBaseData(){
        //访问数据库
        List<ScheduleRecord> list = new ArrayList<>();
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://com.example.time.contentProvider.scheduleProvider/schedules");
        @SuppressLint("Recycle") Cursor cursor = resolver.query(uri,null,"name = ? and datetime = ?",new String[]{sname, sdatetime},null);
        assert cursor != null;
        while(cursor.moveToNext()){
            String name = cursor.getString(0);
            String type = cursor.getString(1);
            String level = cursor.getString(2);
            String message = cursor.getString(3);
            String datetime = cursor.getString(4);
            String alarmClock = cursor.getString(5);
            int alarmClockOpen = cursor.getInt(6);
            isalarmClockOpen = alarmClockOpen;
            String location = cursor.getString(7);
            int locationOpen = cursor.getInt(8);
            islocationOpen = locationOpen;
            byte[] photo = cursor.getBlob(9);
            ScheduleRecord t = new ScheduleRecord.scheduleRecordBuilder()
                    .basicInfo(name,type)
                    .level(level)
                    .message(message)
                    .datetime(datetime)
                    .alarmClock(alarmClock)
                    .alarmClockOpen(alarmClockOpen)
                    .location(location)
                    .locationOpen(locationOpen)
                    .photo(photo)
                    .build();
            list.add(t);
        }
        if(list.get(0).isAlarmClockOpen()==0){
            b1.setChecked(false);
        }else{
            b1.setChecked(true);
            Log.i("TESTBUG",list.get(0).getAlarmClock());
            alarm_clock_time_1.setText(list.get(0).getAlarmClock().substring(1,3));
            alarm_clock_time_2.setText(list.get(0).getAlarmClock().substring(4,6));
            alarm_clock_time_3.setText(list.get(0).getAlarmClock().substring(7));
        }
        if(list.get(0).isLocationOpen()==0){
            location.setChecked(false);
        }else{
            location.setChecked(true);
            textView.setText(list.get(0).getLocation());
        }
        if(list.get(0).getPhoto()!=null){
            byte[] photo=list.get(0).getPhoto();
            Bitmap bmpout = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            ivSchedulePhoto.setImageBitmap(bmpout);
        }
    }
    public void setListener(){
        b1.setOnClickListener(this);
        location.setOnClickListener(this);
        photo.setOnClickListener(this);
        submit.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.photoButton:
                openPhoto();
                break;
            case R.id.b2:
                islocationOpen = islocationOpen==0?1:0;
                gotoBDLocationActivity();
                break;
            case  R.id.b1:
                isalarmClockOpen = isalarmClockOpen==0?1:0;
                break;
            case R.id.go:
                saveTodataBase();
                finish();
                break;
        }
    }
    private void gotoBDLocationActivity(){
        if(location.isChecked()){
            startActivityForResult(new Intent(this, BDLocationActivity.class), BDLocation_CODE);
        }
    }
    private void openPhoto(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {// 选取图片的返回值
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                cursor.moveToFirst();// 指向查询结果的第一个位置
                String imgPath = cursor.getString(1); // 图片文件路径
                String imgSize = cursor.getString(2); // 图片大小
                String imgName = cursor.getString(3); // 图片文件名
                BitmapFactory.Options options = new BitmapFactory.Options();
                // 此时把options.inJustDecodeBounds 设回true，即只读边不读内容
                options.inJustDecodeBounds = true;
                // 默认是Bitmap.Config.ARGB_8888
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                try {
                    //此时不会把图片读入内存，只会获取图片宽高等信息
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
                    //上面一句和下面的类似
                    //Bitmap bitmap = BitmapFactory.decodeFile(imgPath,options);
                    int heitht = options.outHeight;
                    // 根据需要设置压缩比例
                    int size = heitht / 800;
                    if (size <= 0) {
                        size = 2;
                    }
                   /*inSampleSize表示缩略图大小为原始图片大小的几分之一，
                      即如果这个值为2，则取出的缩略图的宽和高都是原始图片的1/2，
                      图片大小就为原始大小的1/4*/
                    options.inSampleSize = size;
                    // 设置options.inJustDecodeBounds重新设置为false
                    options.inPurgeable = true;// 同时设置才会有效
                    options.inInputShareable = true;//。当系统内存不够时候图片自动被回收
                    options.inJustDecodeBounds = false;
                    //此时图片会按比例压缩后被载入内存中
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
                    SaveSD.saveBitmap(imgName,bitmap);//saveBitmap这个是我定义保存到SDcard中的方法
                    ivSchedulePhoto.setImageBitmap(bitmap);
                    imgPath="/sdcard/"+imgName;
                    //将图片从Bitmap变为二进制流,保存到数据库中
                    newPhoto = BitmapToByte.saveBitmap(bitmap);
                    getNewPhoto = true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if(requestCode==BDLocation_CODE){
            if(resultCode== BDLocationActivity.BDLocation_FINISH){
                textView.setText(BDLocationActivity.location);
            }
        }
    }
    private void saveTodataBase(){
        String newLocation;
        MainActivityDataBase myDatabase = new MainActivityDataBase(ModifySchedule.this);
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        if (b1.isChecked()){
            if(alarm_clock_time_1.getText().toString().equals("")||alarm_clock_time_2.getText().toString().equals("")||alarm_clock_time_3.getText().toString().equals("")){
                Toast.makeText(ModifySchedule.this,"请先填写闹钟的时间!",Toast.LENGTH_LONG).show();
                b1.setChecked(false);
            }else{
                int hour = Integer.parseInt(alarm_clock_time_1.getText().toString());
                int minute = Integer.parseInt(alarm_clock_time_2.getText().toString());
                int second = Integer.parseInt(alarm_clock_time_3.getText().toString());
                AlarmClock.INSTANCE.createAlarm(ModifySchedule.this,sname,hour,minute,second);
                newAlarmClock = getAlarmClock(hour, minute, second);
                Log.i("TESTBUG",newAlarmClock);
                db.execSQL("update schedules set alarmClock = ?" +
                                "where name = ? and datetime = ?",
                        new Object[]{newAlarmClock,sname,sdatetime});
            }
        }
        if(location.isChecked()){
            newLocation = BDLocationActivity.location;
            db.execSQL("update schedules set location = ?" +
                            "where name = ? and datetime = ?",
                    new Object[]{newLocation,sname,sdatetime});
        }
        if(getNewPhoto){
            db.execSQL("update schedules set photo = ?" +
                            "where name = ? and datetime = ?",
                    new Object[]{newPhoto,sname,sdatetime});
        }
        db.execSQL("update schedules set alarmClockOpen = ?, locationOpen = ?" +
                        "where name = ? and datetime = ?",
                new Object[]{isalarmClockOpen,islocationOpen,sname,sdatetime});
        db.close();
    }
    private String getAlarmClock(int hour,int minute,int second){
        String s1 = hour<10?"#0"+hour:"#"+hour+"";
        String s2 = minute<10?"#0"+minute:"#"+minute+"";
        String s3 = second<10?"#0"+second:"#"+second+"";
        return s1+s2+s3;
    }
}
