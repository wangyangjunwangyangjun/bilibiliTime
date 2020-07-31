package com.example.time.personalSetting;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import com.example.time.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
//    private Switch s1,s2,s3;
//    SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
//    SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        this.bingView();
//        s1.setOnClickListener(this);
//        s2.setOnClickListener(this);
//        s3.setOnClickListener(this);

        Toolbar toolbar = (Toolbar)findViewById(R.id.shezhi_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.switch1:
//                editor.putBoolean("value1",s1.isChecked());
//                editor.apply();
                break;
            case R.id.switch2:
//                editor.putBoolean("value2",s2.isChecked());
//                editor.apply();
                break;
            case R.id.switch3:
//                editor.putBoolean("value3",s3.isChecked());
//                editor.apply();
                break;
        }
    }

    private void bingView(){
//        s1 = findViewById(R.id.switch1);
//        s2 = findViewById(R.id.switch2);
//        s3 = findViewById(R.id.switch3);
//        s1.setChecked(preferences.getBoolean("value1",false));
//        s2.setChecked(preferences.getBoolean("value2",false));
//        s3.setChecked(preferences.getBoolean("value3",false));
    }
}
