package com.example.time;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.example.time.GuidePage.GuideActivity;

import org.json.JSONObject;

import java.lang.reflect.Method;

import static com.example.time.MainActivity.REQUEST_CODE;

public class LoadingPage extends Activity {
    public static final String lodingPageSuccess = "lodingPageSuccess";
    private Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(mContext, "请同意相关权限,否则应用的一些功能会无法使用",Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_loading_page);
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
        new FetchData().execute();
        //不要标题
    }
    public class FetchData extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... strings) {
//            后台获取处理数据
//            链接云端数据库的数据
            try {
                if (!permission()) {
                    requestAlertWindowPermission();
                }
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
//            更新前端界面-跳转界面
            Intent mainIntent = new Intent(LoadingPage.this, GuideActivity.class);
            mainIntent.putExtra(lodingPageSuccess,"success");
            startActivity(mainIntent);
            finish();
        }
    }

    public boolean permission(){
        //判断权限
        Boolean result = true;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<Settings> clazz = Settings.class;
                Method canDrawOverlays;
                canDrawOverlays = clazz.getDeclaredMethod("canDrawOverlays", Context.class);
                result = (Boolean) canDrawOverlays.invoke(null, mContext);
            } catch (Exception e) {
                Log.e("TAG", Log.getStackTraceString(e));
            }
        }
        return result;
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
}
