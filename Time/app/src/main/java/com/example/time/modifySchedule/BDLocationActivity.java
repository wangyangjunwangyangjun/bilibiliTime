package com.example.time.modifySchedule;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.time.R;
import java.util.ArrayList;
import java.util.List;

public class BDLocationActivity extends AppCompatActivity implements View.OnClickListener{
    public static int BDLocation_CANCEL = 1;
    public static int BDLocation_FINISH = 4;
    public static String location;
    private TextView tvPostion;
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private boolean  isFirstLocate = true;
    private TextView tvCancel;
    private TextView tvFinish;

    StringBuilder  currentPosition;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_bdlocation);
        mMapView = (MapView) findViewById(R.id.mMapView);
        tvPostion = (TextView) findViewById(R.id.tvPostion);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvFinish = (TextView) findViewById(R.id.tvFinish);
        tvCancel.setOnClickListener(this);
        tvFinish.setOnClickListener(this);

        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
        initLocation();
        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(BDLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[] permissions= permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(BDLocationActivity.this,permissions,1);
        }else{
            requestLocation();
        }
    }
    private void initLocation() {
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.requestLocation();
        mLocationClient.registerNotifyLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(final BDLocation bdLocation) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        currentPosition =  new StringBuilder();
                        currentPosition.append("当前位置为").append(bdLocation.getCountry()).append(bdLocation.getProvince()).append(bdLocation.getCity()).append(bdLocation.getDistrict()).append(bdLocation.getStreet());
                        if(bdLocation.getLocType() == BDLocation.TypeGpsLocation ||bdLocation.getLocType() == BDLocation.TypeNetWorkLocation ||bdLocation.getLocType() == BDLocation.INDOOR_LOCATION_SOURCE_WIFI){
                            if(isFirstLocate){
                                LatLng ll = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                                MapStatus.Builder builder = new MapStatus.Builder();
                                builder.target(ll).zoom(18.0f);
                                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                                isFirstLocate = false;
                            }
                            MyLocationData.Builder  locationBuilder = new MyLocationData.Builder();
                            locationBuilder.latitude(bdLocation.getLatitude());
                            locationBuilder.longitude(bdLocation.getLongitude());
                            MyLocationData locationData = locationBuilder.build();
                            mBaiduMap.setMyLocationData(locationData);
                        }
                        tvPostion.setText(currentPosition);
                        location = String.valueOf(currentPosition);
                    }
                });
            }
        });
    }
    private void requestLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenAutoNotifyMode();
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "必须同意所有的权限才能使用本程序", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                }
                requestLocation();
            } else {
                Toast.makeText(this, "发生了错误", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mMapView.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                setResult(BDLocation_CANCEL);
                finish();
                break;
            case R.id.tvFinish:
                setResult(BDLocation_FINISH);
                finish();
                break;
        }
    }

}
