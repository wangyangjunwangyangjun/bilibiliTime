package com.example.time;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.daimajia.swipe.SwipeLayout;
import com.example.time.adapter.NewListAdapter;
import com.example.time.alarmClock.AlarmClockActivity;
import com.example.time.been.ScheduleRecord;
import com.example.time.budget.Activity_JiZhang;
import com.example.time.calendar.CalendarActivity;
import com.example.time.circleImageView.RoundImageView;
import com.example.time.dataBase.MainActivityDataBase;
//import com.example.time.monGoDBUtil.MySQLDB;
import com.example.time.mySQLDBUtil.MySQLDB;
import com.example.time.myGridView.GridImageAdapter;
import com.example.time.myGridView.MyGridView;
import com.example.time.personalSetting.SettingActivity;
import com.example.time.qQLogin.QQLogin;
import com.example.time.scollListView.ScollListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private NavigationView navigationView;
    private Resources resource;
    private ColorStateList csl;
    private FloatingActionButton addSchedule;
    private ImageView date_select;
    private RoundImageView navigationViewLogin;
    private ResideMenu resideMenu;
    private SharedPreferences preferences;
    private boolean loginStatus;
    public ScollListView schedules_morning;
    public ScollListView schedules_noon;
    public ScollListView schedules_afternoon;
    public ScollListView schedules_night;
    private NewListAdapter morningScheduleAdapter;
    private NewListAdapter noonScheduleAdapter;
    private NewListAdapter afternoonScheduleAdapter;
    private NewListAdapter nightScheduleAdapter;
    private List<ScheduleRecord> scheduleListMorning;
    private List<ScheduleRecord> scheduleListNoon;
    private List<ScheduleRecord> scheduleListAfternoon;
    private List<ScheduleRecord> scheduleListNight;
    private WindowUtils windowUtils;
    private MainActivityDataBase mainActivityDatabase;
    private TextView date;
    private TextView loginOut;
    private TextView loginState;
    private TextView textView1;
    public static String nowDateTime = "2020-09-09 00:00:00";
    public static Integer REQUEST_CODE = 1;
    final String[] list_English_name = {"team","personal","family","decorate","miscellaneous","fitness","television","game","study","others"};
    final String[] listname = {"团队","个人","家庭","装修","杂项","健身","影视","游戏","学习","其他"};
    final int[] position = new int[1];
    final int[] listicon = {R.drawable.lvzhi1, R.drawable.lvzhi2, R.drawable.lvzhi3, R.drawable.lvzhi4, R.drawable.lvzhi5, R.drawable.lvzhi6, R.drawable.lvzhi7, R.drawable.lvzhi8, R.drawable.lvzhi9, R.drawable.lvzhi10,};;
    final String[] list_English_name2 = {"Ignored","UIUU","UICU","UKnow","CIUU","CICU","CIVU","VICU","Immediately","UnFinish"};
    final String[] listname2 = {"忽略","不紧急、不重要","不紧急、较重要","未知","较重要、不紧急","较重要、较紧急","非常重要、较紧急","较重要、非常紧急","需立即开始","不可完成的"};
    final int[] position2 = new int[1];
    final int[] listicon2 = {R.drawable.level1, R.drawable.level2, R.drawable.level3, R.drawable.level4, R.drawable.level5, R.drawable.level6, R.drawable.level7, R.drawable.level8, R.drawable.level9, R.drawable.level10,};;
    private boolean mIsExit;
    private DrawerLayout drawer;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityDatabase = new MainActivityDataBase(MainActivity.this);
        this.bingView();
        this.initSchedules();
        this.setRightCeBianLan();
        this.addListener();
    }

    /**双击返回键退出*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                this.finish();
            } else {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mIsExit = true;
                new Handler().postDelayed(() -> mIsExit = false, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                }
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar,menu);//加载menu布局
        return true;
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.action_search:
                Toast.makeText(MainActivity.this,"action_search",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.beifen:
                MySQLDB.INSTANCE.upLoadData(MainActivity.this);
                Toast.makeText(MainActivity.this,"beifen",Toast.LENGTH_LONG).show();
                break;
            case R.id.recover:
                MySQLDB.INSTANCE.downLoadData(MainActivity.this);
                Toast.makeText(MainActivity.this,"已更新! 请重新选择日期",Toast.LENGTH_LONG).show();
                break;
            case R.id.download:
                Toast.makeText(MainActivity.this,"download",Toast.LENGTH_LONG).show();
                break;
            case R.id.close:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.setting:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawers();
        return true;
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.add_schedule:
                initFloatingWindow();
                break;
            case R.id.floatActionButtonLogin:
                if(loginStatus){
                    Toast.makeText(MainActivity.this,"已登录",Toast.LENGTH_LONG).show();
                }else{
                    Intent mainIntent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
                break;
            case R.id.date_select:
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(MainActivity.this,
                        (view1, year, monthOfYear, dayOfMonth) -> {
                            date.setText((dataformat(monthOfYear+1)) + "月" + dataformat(dayOfMonth)+"日");
                            nowDateTime = year + "-" + (dataformat(monthOfYear+1)) + "-" + dataformat(dayOfMonth);
                            scheduleListMorning.clear();
                            scheduleListMorning.addAll(query(1));
                            morningScheduleAdapter.notifyDataSetChanged();
                            scheduleListNoon.clear();
                            scheduleListNoon.addAll(query(2));
                            noonScheduleAdapter.notifyDataSetChanged();
                            scheduleListAfternoon.clear();
                            scheduleListAfternoon.addAll(query(3));
                            afternoonScheduleAdapter.notifyDataSetChanged();
                            scheduleListNight.clear();
                            scheduleListNight.addAll(query(4));
                            nightScheduleAdapter.notifyDataSetChanged();
                        },c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.loginOut:
                QQLogin.INSTANCE.gotoQQLogout(MainActivity.this);
                break;
        }
    }
    //创建数据库并插入一条数据
    private void bingView(){
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer_layout);
        addSchedule = findViewById(R.id.add_schedule);
        date_select = findViewById(R.id.date_select);
        date = findViewById(R.id.date);
        navigationViewLogin =  navigationView.getHeaderView(0).findViewById(R.id.floatActionButtonLogin);
        loginOut = navigationView.getHeaderView(0).findViewById(R.id.loginOut);
        loginState = navigationView.getHeaderView(0).findViewById(R.id.LoginStatue);
        textView1 = navigationView.getHeaderView(0).findViewById(R.id.textView1);
        //初始化未登录的登录情况
        preferences = getSharedPreferences("data",MODE_PRIVATE);
        loginStatus = preferences.getBoolean("loginStatus",false);
        navigationViewLogin.setImageBitmap(BitmapFactory.decodeFile("/storage/self/primary/Pictures/WeiXin/mmexport1593936099206.jpg"));
        if(loginStatus){
            loginState.setText(QQLogin.INSTANCE.getNickName());
            textView1.setText("");
            new Thread(() -> {
                Bitmap bitmap = getHttpBitmap("http://thirdqq.qlogo.cn/g?b=oidb&k=NSgvxXicLuHedhztS6gPhgA&s=100&t=1557060644");
                navigationViewLogin.setImageBitmap(bitmap);
            }).start();
        }
        //设置头部的工具栏
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //设置侧边栏
        navigationView.setCheckedItem(R.id.home);
        resource = getBaseContext().getResources();
        csl= resource.getColorStateList(R.color.navigation_menu_item_color);
        navigationView.setItemTextColor(csl);
        //日程表
        schedules_morning = findViewById(R.id.schedule_morning);
        schedules_noon = findViewById(R.id.schedule_noon);
        schedules_afternoon = findViewById(R.id.schedule_afternoon);
        schedules_night = findViewById(R.id.schedule_night);
        //获取数据库中的数据
        new initdatabase().execute();
    }
    public static Bitmap getHttpBitmap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    //查询数据from数据库
    public List<ScheduleRecord> query(int id){
        String begin=nowDateTime,end=nowDateTime;
        begin += " ";
        end += " ";
        if(id==1){
            begin += "00:00:00";
            end += "11:59:59";
        }else if(id == 2){
            begin += "12:00:00";
            end += "13:59:59";
        }else if(id == 3){
            begin += "14:00:00";
            end += "19:59:59";
        }else if (id == 4){
            //2020-01-01
            begin += "19:00:00";
            end += "23:59:59";
        }else{
            throw new RuntimeException("id不正确");
        }
        List<ScheduleRecord> list = new ArrayList<>();
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://com.example.time.contentProvider.scheduleProvider/schedules");

        @SuppressLint("Recycle") Cursor cursor = resolver.query(uri,null,"datetime between ? and ?",new String[]{begin,end},null);
        assert cursor != null;
        while(cursor.moveToNext()){
            String name = cursor.getString(0);
            String type = cursor.getString(1);
            Log.i("TEST", type);
            String level = cursor.getString(2);
            String message = cursor.getString(3);
            String datetime = cursor.getString(4);
            String alarmClock = cursor.getString(5);
            int alarmClockOpen = cursor.getInt(6);
            String location = cursor.getString(7);
            int locationOpen = cursor.getInt(8);
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
        return list;
    }
    private void setRightCeBianLan(){
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.main_activity_bg);
        resideMenu.attachToActivity(this);
        resideMenu.setScaleValue(0.67f);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
        resideMenu.setShadowVisible(false);
        // create menu items;
        String[] titles = {"Home", "calendar", "alarm", "budget"};
        int[] icon = {R.drawable.home, R.drawable.calendar, R.drawable.alarm_clock, R.drawable.budget};
        //home点击
        ResideMenuItem home = new ResideMenuItem(this, icon[0], titles[0]);
        ResideMenuItem calendar = new ResideMenuItem(this, icon[1], titles[1]);
        ResideMenuItem alarm_clock = new ResideMenuItem(this, icon[2], titles[2]);
        ResideMenuItem budget = new ResideMenuItem(this, icon[3], titles[3]);

        home.setOnClickListener(view -> Toast.makeText(MainActivity.this,"已在日程界面",Toast.LENGTH_LONG).show());
        calendar.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this,"1",Toast.LENGTH_LONG).show();
            Intent intent1 = new Intent(MainActivity.this, CalendarActivity.class);
            startActivity(intent1);
            finish();
        });
        alarm_clock.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this,"2",Toast.LENGTH_LONG).show();
            Intent intent1 = new Intent(MainActivity.this, AlarmClockActivity.class);
            startActivity(intent1);
            finish();
        });
        budget.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this,"3",Toast.LENGTH_LONG).show();
            Intent intent1 = new Intent(MainActivity.this, Activity_JiZhang.class);
            startActivity(intent1);
            finish();
        });
        resideMenu.addMenuItem(home,ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(calendar,ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(alarm_clock,ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(budget,ResideMenu.DIRECTION_RIGHT);
        resideMenu.setMenuListener(menuListener);
    }
    private void addListener(){
        toolbar.setOnMenuItemClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        addSchedule.setOnClickListener(this);
        navigationViewLogin.setOnClickListener(this);
        resideMenu.setOnClickListener(this);
        date_select.setOnClickListener(this);
        loginOut.setOnClickListener(this);
    }
    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            Toast.makeText(MainActivity.this, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void closeMenu() {
            Toast.makeText(MainActivity.this, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };
    private void initSchedules(){
        final SQLiteDatabase db = mainActivityDatabase.getWritableDatabase();
        morningScheduleAdapter = new NewListAdapter(MainActivity.this,R.layout.listview_item_morning,scheduleListMorning,db);
        noonScheduleAdapter = new NewListAdapter(MainActivity.this,R.layout.listview_item_noon,scheduleListNoon,db);
        afternoonScheduleAdapter = new NewListAdapter(MainActivity.this,R.layout.listview_item_afternoon,scheduleListAfternoon,db);
        nightScheduleAdapter = new NewListAdapter(MainActivity.this,R.layout.listview_item_night,scheduleListNight,db);
        schedules_morning.setAdapter(morningScheduleAdapter);
        schedules_noon.setAdapter(noonScheduleAdapter);
        schedules_afternoon.setAdapter(afternoonScheduleAdapter);
        schedules_night.setAdapter(nightScheduleAdapter);
        schedules_morning.setOnItemClickListener((parent, view, position, id) -> {
        ((SwipeLayout)(schedules_morning.getChildAt(position - schedules_morning.getFirstVisiblePosition()))).open(true);
        final TextView name = view.findViewById(R.id.text_data);
        view.findViewById(R.id.delete).setOnClickListener(view1 -> {
            Toast.makeText(MainActivity.this,"you click it !!!",Toast.LENGTH_LONG).show();
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are You Sure to Move This Record ?");
            builder.setNegativeButton("YES", (dialogInterface, i) -> {
                db.execSQL("delete from schedules where name = ?",new String[]{name.getText().toString()});
                scheduleListMorning.clear();
                scheduleListMorning.addAll(query(1));
                morningScheduleAdapter.notifyDataSetChanged();
            });
            builder.setPositiveButton("NO",null);
            builder.create().show();
        });
        });
        schedules_noon.setOnItemClickListener((parent, view, position, id) -> {
        ((SwipeLayout)(schedules_noon.getChildAt(position - schedules_noon.getFirstVisiblePosition()))).open(true);
        final TextView name = view.findViewById(R.id.text_data);
        view.findViewById(R.id.delete).setOnClickListener(view12 -> {
            Toast.makeText(MainActivity.this,"you click it !!!",Toast.LENGTH_LONG).show();
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are You Sure to Move This Record ?");
            builder.setNegativeButton("YES", (dialogInterface, i) -> {
                db.execSQL("delete from schedules where name = ?",new String[]{name.getText().toString()});
                scheduleListNoon.clear();
                scheduleListNoon.addAll(query(1));
                noonScheduleAdapter.notifyDataSetChanged();
            });
            builder.setPositiveButton("NO",null);
            builder.create().show();
        });
        });
        schedules_afternoon.setOnItemClickListener((parent, view, position, id) -> {
        ((SwipeLayout)(schedules_afternoon.getChildAt(position - schedules_afternoon.getFirstVisiblePosition()))).open(true);
        final TextView name = view.findViewById(R.id.text_data);
        view.findViewById(R.id.delete).setOnClickListener(view13 -> {
            Toast.makeText(MainActivity.this,"you click it !!!",Toast.LENGTH_LONG).show();
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are You Sure to Move This Record ?");
            builder.setNegativeButton("YES", (dialogInterface, i) -> {
                db.execSQL("delete from schedules where name = ?",new String[]{name.getText().toString()});
                scheduleListAfternoon.clear();
                scheduleListAfternoon.addAll(query(1));
                afternoonScheduleAdapter.notifyDataSetChanged();
            });
            builder.setPositiveButton("NO",null);
            builder.create().show();
        });
        });
        schedules_night.setOnItemClickListener((parent, view, position, id) -> {
        ((SwipeLayout)(schedules_night.getChildAt(position - schedules_night.getFirstVisiblePosition()))).open(true);
        final TextView name = view.findViewById(R.id.text_data);
        view.findViewById(R.id.delete).setOnClickListener(view14 -> {
            Toast.makeText(MainActivity.this,"you click it !!!",Toast.LENGTH_LONG).show();
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are You Sure to Move This Record ?");
            builder.setNegativeButton("YES", (dialogInterface, i) -> {
                db.execSQL("delete from schedules where name = ?",new String[]{name.getText().toString()});
                scheduleListNight.clear();
                scheduleListNight.addAll(query(1));
                nightScheduleAdapter.notifyDataSetChanged();
            });
            builder.setPositiveButton("NO",null);
            builder.create().show();
        });
        });
    }
    private void initFloatingWindow(){
        if (permission()) {
            windowUtils = new WindowUtils();
            windowUtils.showPopupWindow(MainActivity.this);
        } else {
            requestAlertWindowPermission();
        }
    }
    //申请权限，跳转到系统的权限申请界面
    private void requestAlertWindowPermission() {
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        }
        assert intent != null;
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_CODE);
    }
    public boolean permission(){
        //判断权限
        Boolean result = true;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<Settings> clazz = Settings.class;
                Method canDrawOverlays;
                canDrawOverlays = clazz.getDeclaredMethod("canDrawOverlays", Context.class);
                result = (Boolean) canDrawOverlays.invoke(null, MainActivity.this);
            } catch (Exception e) {
                Log.e("TAG", Log.getStackTraceString(e));
            }
        }
        return result;
    }

    class WindowUtils {
        private  View mView = null;
        private  WindowManager mWindowManager = null;
        private  Context mContext = null;
        Boolean isShown = false;
        void showPopupWindow(Context context) {
            if (isShown) {
                return;
            }
            isShown = true;
            mContext = context;
            mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            mView = setUpView(context);
            final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL;
            params.format = PixelFormat.TRANSLUCENT;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            params.gravity = Gravity.CENTER;
            mWindowManager.addView(mView, params);
        }
        void hidePopupWindow() {
            if (isShown && null != mView) {
                mWindowManager.removeView(mView);
                isShown = false;
            }
        }
        private  View setUpView(final Context context) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.popupwindow, null);
            ImageView dateValueBtn = (ImageView) view.findViewById(R.id.content6);
            ImageView timeValueBtn = (ImageView) view.findViewById(R.id.content7);
            Button positiveBtn = (Button) view.findViewById(R.id.positiveBtn);
            Button negativeBtn = (Button) view.findViewById(R.id.negativeBtn);
            final TextView dateValue = (TextView)view.findViewById(R.id.dateValue);
            final TextView timeValue = (TextView)view.findViewById(R.id.timeValue);
            final EditText edit1 = (EditText) view.findViewById(R.id.edit1);
            final MyGridView icon_group = (MyGridView) view.findViewById(R.id.icon_group);
            final EditText edit3 = (EditText) view.findViewById(R.id.edit3);
            final MyGridView level_group = (MyGridView) view.findViewById(R.id.level_group);

            icon_group.setNumColumns(5);
            icon_group.setAdapter(new GridImageAdapter(MainActivity.this,listicon,listname));
            icon_group.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
            icon_group.setItemChecked(0,true);
            icon_group.setSelection(0);
            position[0] = 0;
            icon_group.setOnItemClickListener((parent, view14, position1, id) -> {
                view14.setSelected(true);
                position[0] = position1;
            });

            level_group.setNumColumns(5);
            level_group.setAdapter(new GridImageAdapter(MainActivity.this,listicon2,list_English_name2));
            level_group.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
            level_group.setItemChecked(0,true);
            level_group.setSelection(0);
            position2[0] = 0;
            level_group.setOnItemClickListener((parent, view13, position1, id) -> {
                view13.setSelected(true);
                position2[0] = position1;
            });

            dateValueBtn.setOnClickListener(v -> {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(context,
                        (view1, year, monthOfYear, dayOfMonth) -> dateValue.setText(year + "-" + (dataformat(monthOfYear+1)) + "-" + dataformat(dayOfMonth)),c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            });
            timeValueBtn.setOnClickListener(v -> {
                Calendar c = Calendar.getInstance();
                new TimePickerDialog(context,
                        (view12, hourOfDay, minute) -> timeValue.setText(dataformat(hourOfDay) + ":" + dataformat(minute)+":00"),c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
            });
            positiveBtn.setOnClickListener(v -> {
            String name = edit1.getText().toString();
            String type = list_English_name[position[0]];
            String message = edit3.getText().toString();
            String level = list_English_name2[position2[0]];
            String dateTime = dateValue.getText().toString()+" "+timeValue.getText().toString();
            SQLiteDatabase db = mainActivityDatabase.getWritableDatabase();
            ContentValues values = new ContentValues();
            byte[] t = new byte[1];
            values.put("name",name);
            values.put("type", type);
            values.put("level",level);
            values.put("message",message);
            values.put("datetime",dateTime);
            values.put("alarmClock","");
            values.put("alarmClockOpen",0);
            values.put("location","");
            values.put("locationOpen",0);
            values.put("photo",t);
            db.insert("schedules", null, values);

            scheduleListMorning.clear();
            scheduleListNoon.clear();
            scheduleListAfternoon.clear();
            scheduleListNight.clear();

            scheduleListMorning.addAll(query(1));
            scheduleListNoon.addAll(query(2));
            scheduleListAfternoon.addAll(query(3));
            scheduleListNight.addAll(query(4));

            morningScheduleAdapter.notifyDataSetChanged();
            noonScheduleAdapter.notifyDataSetChanged();
            afternoonScheduleAdapter.notifyDataSetChanged();
            nightScheduleAdapter.notifyDataSetChanged();
            windowUtils.hidePopupWindow();
            });
            negativeBtn.setOnClickListener(v -> windowUtils.hidePopupWindow());
            final View popupWindowView = view.findViewById(R.id.popup_window);// 非透明的内容区域
            view.setOnTouchListener((v, event) -> {
                int x = (int) event.getX();
                int y = (int) event.getY();
                Rect rect = new Rect();
                popupWindowView.getGlobalVisibleRect(rect);
                if (!rect.contains(x, y)) {
                    windowUtils.hidePopupWindow();
                }
                return false;
            });
            view.setOnKeyListener((v, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    windowUtils.hidePopupWindow();
                    return true;
                }
                return false;
            });
            return view;
        }
    }

    public class initdatabase extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... strings) {
            scheduleListMorning = new ArrayList<>();
            scheduleListNoon = new ArrayList<>();
            scheduleListAfternoon = new ArrayList<>();
            scheduleListNight = new ArrayList<>();
            scheduleListMorning.addAll(query(1));
            scheduleListNoon.addAll(query(2));
            scheduleListAfternoon.addAll(query(3));
            scheduleListNight.addAll(query(4));
            return null;
        }
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
//            更新前端界面-跳转界面
        }
    }
    public String dataformat(int num){
        if(num<10){
            return "0"+num;
        }
        return num+"";
    }
}