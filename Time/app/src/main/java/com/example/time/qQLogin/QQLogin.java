package com.example.time.qQLogin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.time.LoginActivity;
import com.example.time.MainActivity;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import static android.content.Context.MODE_PRIVATE;

public enum QQLogin {
    INSTANCE;
    private Tencent mTencent;
    private IUiListener loginListener;
    private BaseApiListener baseApiListener;
    private IUiListener userInfoListener;
    private String nickName;
    private UserInfo userInfo;
    private String logo;

    public void gotoQQLogout(Context context){
        if (mTencent != null) {
            //注销登录
            mTencent.logout(context);
            Toast.makeText(context, "你已注销~", Toast.LENGTH_LONG).show();
            saveLoginStatus(context,false);
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            ((Activity) context).finish();
        }
    }
    public void initQQ(final Context context){
        String APP_ID = "1110586725";
        mTencent = Tencent.createInstance(APP_ID, context);
        loginListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                try {
                    JSONObject jo = (JSONObject) o;
                    int ret = jo.getInt("ret");
                    System.out.println("json=" + String.valueOf(jo));
                    if (ret == 0) {
                        String openID = jo.getString("openid");
                        String accessToken = jo.getString("access_token");
                        String expires = jo.getString("expires_in");
                        mTencent.setOpenId(openID);
                        mTencent.setAccessToken(accessToken, expires);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(UiError uiError) {}
            @Override
            public void onCancel() {
            }
        };
        userInfoListener = new IUiListener() {
            @Override
            public void onError(UiError arg0) {
            }
            @Override
            public void onComplete(Object arg0) {
                if(arg0 == null){
                    return;
                }
                try {
                    JSONObject jo = (JSONObject) arg0;
                    int ret = jo.getInt("ret");
                    String gender = jo.getString("gender");
                    nickName = jo.getString("nickname");
                    logo = jo.getString("figureurl_qq_2");
                    //跳转至主界面
                    saveLoginStatus(context,true);
                    Intent intent1 = new Intent(context, MainActivity.class);
                    context.startActivity(intent1);
                    ((Activity) context).finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancel() {
                saveLoginStatus(context,false);
            }
        };
    }
    public String getNickName() {
        return nickName;
    }
    public String getLogo(){
        return logo;
    }
    public void loginByQQ(Context context){
        baseApiListener = new BaseApiListener();
        if (!mTencent.isSessionValid()) {
            //开始qq授权登录
            mTencent.login((Activity) context, "all", loginListener);
        }else {
            Toast.makeText(context, "你已登录！", Toast.LENGTH_LONG).show();
        }
    }
    public void saveLoginStatus(Context context,boolean key){
        //    使用sharepreference来保存登录状态的数据
        SharedPreferences.Editor editor = context.getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putBoolean("loginStatus",key);
        editor.apply();
    }
    public class BaseApiListener implements IRequestListener {
        @Override
        public void onComplete(JSONObject jsonObject) {}
        @Override
        public void onIOException(IOException e) {}
        @Override
        public void onMalformedURLException(MalformedURLException e) {}
        @Override
        public void onJSONException(JSONException e) {}
        @Override
        public void onConnectTimeoutException(ConnectTimeoutException e) {}
        @Override
        public void onSocketTimeoutException(SocketTimeoutException e) {}
        @Override
        public void onNetworkUnavailableException(HttpUtils.NetworkUnavailableException e) {}
        @Override
        public void onHttpStatusException(HttpUtils.HttpStatusException e) {}
        @Override
        public void onUnknowException(Exception e) {}
    }
    public void onActivityResultHandle(int requestCode, int resultCode, Intent data, Context context){
        Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.handleResultData(data, loginListener);
            userInfo = new UserInfo(context, mTencent.getQQToken());
            userInfo.getUserInfo(userInfoListener);
        }
    }
}
