package com.example.time.budget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.time.LoginActivity;
import com.example.time.MainActivity;
import com.example.time.R;
import com.example.time.alarmClock.AlarmClockActivity;
import com.example.time.calendar.CalendarActivity;
import com.example.time.circleImageView.RoundImageView;
import com.example.time.qQLogin.QQLogin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.time.MainActivity.getHttpBitmap;

public class Activity_JiZhang extends AppCompatActivity implements View.OnClickListener, Toolbar.OnMenuItemClickListener, NavigationView.OnNavigationItemSelectedListener {
    private FloatingActionButton add_record;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private RoundImageView navigationViewLogin;
    private TextView loginOut;
    private TextView loginState;
    private TextView textView1;
    private SharedPreferences preferences;
    private boolean loginStatus;
    private ActionBar actionBar;
    private Resources resource;
    private ColorStateList csl;
    private DrawerLayout drawer;
    private ResideMenu resideMenu;
    private boolean mIsExit;
    private List<JiZhang_ShouRu_ZhiZhu_listview_Item_Message> ShouRuList = new ArrayList<>();
    private List<JiZhang_ShouRu_ZhiZhu_listview_Item_Message> ZhiChuList = new ArrayList<>();
    private JiZhang_ShouRu_ZhiZhu_listView_Item_Adapter adapter1,adapter2;
    private ListView listView1,listView2;
    private TextView textview4;
    private DecimalFormat df;
    public static double allMoney = 0.00;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        //界面绑定
        bingView();
        initListViewItem();
        setRightCeBianLan();
        addListener();
//        报告与线程及虚拟机相关的策略违例
        StrictMode.ThreadPolicy policy= null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(policy);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }
        if(ContextCompat.checkSelfPermission(Activity_JiZhang.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Activity_JiZhang.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        listView1 = findViewById(R.id.list_view1);
        listView2 = findViewById(R.id.list_view2);
        adapter1 = new JiZhang_ShouRu_ZhiZhu_listView_Item_Adapter(Activity_JiZhang.this,R.layout.shouru_listview_message_item,ShouRuList);
        adapter2 = new JiZhang_ShouRu_ZhiZhu_listView_Item_Adapter(Activity_JiZhang.this,R.layout.zhichu_listview_message_item,ZhiChuList);
        listView1.setAdapter(adapter1);
        listView2.setAdapter(adapter2);
        //设置ListView1和ListView2每项点击触发事件
        AdapterView.OnItemClickListener mMessageClickedHandlerListView1 = (parent, v, position, id) -> {
            if (id == 0) {
                Intent intent1 = new Intent(Activity_JiZhang.this, GongZuoShouRu.class);
                startActivity(intent1);
            }
        };
        listView1.setOnItemClickListener(mMessageClickedHandlerListView1);
        AdapterView.OnItemClickListener mMessageClickedHandlerListView2 = (parent, v, position, id) -> {
            if (id == 0) {
                Intent intent1 = new Intent(Activity_JiZhang.this, GongZuoShouRu.class);
                startActivity(intent1);
            }
        };
        listView2.setOnItemClickListener(mMessageClickedHandlerListView2);

        df = new DecimalFormat("#.00");
        textview4 = findViewById(R.id.textview4);
        textview4.setText(df.format(allMoney));
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_record:
                Intent intent1 = new Intent(Activity_JiZhang.this, ActivityJiYiBi.class);
                startActivityForResult(intent1,1);
                break;
            case R.id.loginOut:
                QQLogin.INSTANCE.gotoQQLogout(Activity_JiZhang.this);
                break;
            case R.id.floatActionButtonLogin:
                if(loginStatus){
                    Toast.makeText(Activity_JiZhang.this,"已登录",Toast.LENGTH_LONG).show();
                }else{
                    Intent mainIntent = new Intent(Activity_JiZhang.this,LoginActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            if (requestCode == 1) {
                if (data != null) {
                    String name = data.getStringExtra("name");//2
                    String type = data.getStringExtra("type");//3
                    data.getStringExtra("time");//6
                    int costicon = data.getIntExtra("costicon",0);//int
                    double cost = data.getDoubleExtra("cost",0);//double
                    String costdate = data.getStringExtra("costdate");//5
                    data.getStringExtra("place");//7
                    data.getStringExtra("note");//9
                    data.getIntExtra("image",0);//int
                    JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message(name,costicon,cost>0?("+"+cost):("-"+cost),costdate);
                    for(int i=0;i<ShouRuList.size();i++){
                        if (ShouRuList.get(i).getName().equals(type)){
                            double t = calculateMoney(ShouRuList.get(i))+calculateMoney(lm);
                            String money = t>0?"+"+t:"-"+t;
                            ShouRuList.get(i).setMoney(money);
                        }
                    }
                    adapter1.notifyDataSetChanged();
                    allMoney+=calculateMoney(lm);
                    textview4.setText(df.format(allMoney));
                }
            }
        }
    }
    private void bingView(){
        toolbar = findViewById(R.id.toolbar);
        add_record = findViewById(R.id.add_record);
        navigationView = findViewById(R.id.navigationView);
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
        //侧边栏的按钮事件
        drawer = findViewById(R.id.drawer_layout);
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

        home.setOnClickListener(view -> {
            Toast.makeText(Activity_JiZhang.this,"刷新成功",Toast.LENGTH_LONG).show();
            Intent intent1 = new Intent(Activity_JiZhang.this, MainActivity.class);
            startActivity(intent1);
            finish();
        });
        calendar.setOnClickListener(view -> {
            Toast.makeText(Activity_JiZhang.this,"1",Toast.LENGTH_LONG).show();
            Intent intent1 = new Intent(Activity_JiZhang.this, CalendarActivity.class);
            startActivity(intent1);
            finish();
        });
        alarm_clock.setOnClickListener(view -> {
            Toast.makeText(Activity_JiZhang.this,"2",Toast.LENGTH_LONG).show();
            Intent intent1 = new Intent(Activity_JiZhang.this, AlarmClockActivity.class);
            startActivity(intent1);
            finish();
        });
        budget.setOnClickListener(view -> Toast.makeText(Activity_JiZhang.this,"已在收支页面",Toast.LENGTH_LONG).show());
        resideMenu.addMenuItem(home,ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(calendar,ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(alarm_clock,ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(budget,ResideMenu.DIRECTION_RIGHT);
        resideMenu.setMenuListener(menuListener);
    }
    private void addListener(){
        toolbar.setOnMenuItemClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        navigationViewLogin.setOnClickListener(this);
        resideMenu.setOnClickListener(this);
        loginOut.setOnClickListener(this);
        add_record.setOnClickListener(this);
    }
    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            Toast.makeText(Activity_JiZhang.this, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void closeMenu() {
            Toast.makeText(Activity_JiZhang.this, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.action_search:
                Toast.makeText(Activity_JiZhang.this,"action_search",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
    /**双击返回键退出*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                this.finish();
            } else {
                Toast.makeText(Activity_JiZhang.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mIsExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.beifen:
                Toast.makeText(Activity_JiZhang.this,"beifen",Toast.LENGTH_LONG).show();
                break;
            case R.id.recover:
                Toast.makeText(Activity_JiZhang.this,"recover",Toast.LENGTH_LONG).show();
                break;
            case R.id.download:
                Toast.makeText(Activity_JiZhang.this,"download",Toast.LENGTH_LONG).show();
                break;
            case R.id.close:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
        drawerLayout.closeDrawers();
        return true;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar,menu);
        return true;
    }
    private void initListViewItem(){
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm1 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("工作收入",R.drawable.work,"+4800.00","2019.10.21");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm2 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("存款利息",R.drawable.bank,"+317.56","2019.10.24");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm3 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("业绩奖金",R.drawable.award,"+700.00","2019.10.28");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm4 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("长辈给与",R.drawable.family,"+3000.00","2019.10.29");
        ShouRuList.add(lm1);
        ShouRuList.add(lm2);
        ShouRuList.add(lm3);
        ShouRuList.add(lm4);
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm5 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("餐饮食品",R.drawable.food,"-200.70","2019.10.21");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm6 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("衣服饰品",R.drawable.clothes,"-1020.50","2019.10.22");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm7 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("行车交通",R.drawable.subway,"-213.40","2019.10.22");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm8 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("电子设备",R.drawable.electric,"-1230.10","2019.10.24");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm9 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("女友消费",R.drawable.girlfriend,"-1230.00","2019.10.25");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm10 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("住宅装修",R.drawable.building,"-1210.50","2019.10.26");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm11 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("身体健康",R.drawable.hospital,"-310.90","2019.10.27");
        ZhiChuList.add(lm5);
        ZhiChuList.add(lm6);
        ZhiChuList.add(lm7);
        ZhiChuList.add(lm8);
        ZhiChuList.add(lm9);
        ZhiChuList.add(lm10);
        ZhiChuList.add(lm11);

        allMoney+=calculateMoney(lm1);
        allMoney+=calculateMoney(lm2);
        allMoney+=calculateMoney(lm3);
        allMoney+=calculateMoney(lm4);
        allMoney+=calculateMoney(lm5);
        allMoney+=calculateMoney(lm6);
        allMoney+=calculateMoney(lm7);
        allMoney+=calculateMoney(lm8);
        allMoney+=calculateMoney(lm9);
        allMoney+=calculateMoney(lm10);
        allMoney+=calculateMoney(lm11);
    }
    private double calculateMoney(JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm){
        double t = 0;
        if(lm.getMoney().substring(0,1).equals("+")){
            t+=(Double.valueOf(lm.getMoney().substring(1)));
        }else{
            t-=(Double.valueOf(lm.getMoney().substring(1)));
        }
        return t;
    }
}
