package com.example.time;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.time.qQLogin.QQLogin;
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
public class LoginActivity extends Activity implements View.OnClickListener {
    private Context context;
    private Button loginButton;
    private ImageView tenXun;
    private EditText inputAccount;
    private EditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        context = this;
        //绑定界面
        bindView();
        //初始化QQ第三方登录
        QQLogin.INSTANCE.initQQ(LoginActivity.this);
        //监听事件
        this.addListener();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginButton:
                loginByPassword();
                break;
            case R.id.tenXun:
                QQLogin.INSTANCE.loginByQQ(LoginActivity.this);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        QQLogin.INSTANCE.onActivityResultHandle(requestCode,resultCode,data, LoginActivity.this);
    }

    private void addListener(){
        loginButton.setOnClickListener(this);
        tenXun.setOnClickListener(this);
    }
    private void loginByPassword(){
        String inputAccountStr = inputAccount.getText().toString();
        String inputPasswordStr = inputPassword.getText().toString();
        if(inputAccountStr.equals("admin")&&inputPasswordStr.equals("admin")){
            Intent intent = new Intent();
            intent.putExtra("user","login");
            setResult(RESULT_OK, intent);
            Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent1);
            finish();
        }else{
            Toast t = Toast.makeText(context,"password error !",Toast.LENGTH_LONG);
            t.show();
        }
    }
    private void bindView(){
        loginButton = findViewById(R.id.loginButton);
        tenXun = findViewById(R.id.tenXun);
        inputAccount = findViewById(R.id.inputAccount);
        inputPassword =findViewById(R.id.inputPassword);
    }
}
