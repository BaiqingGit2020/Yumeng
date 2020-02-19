package com.chanshiguan.yumeng;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.List;

public class CurrentLocationDemo extends AppCompatActivity {
    public LocationClient mLocationClient;
    private TextView positionText;
    private double Latitude1,Latitude2=0;
    private double Longitude1,Longitude2=0;
    private static final String TAG = "CurrentLocationDemo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        setContentView(R.layout.activity_current_location_demo);
        positionText = (TextView) findViewById(R.id.position_text_view);
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(CurrentLocationDemo.this, android.Manifest.
                permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(CurrentLocationDemo.this, android.Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(CurrentLocationDemo.this, android.Manifest.
                permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String [] permissions = permissionList.toArray(new String[permissionList.
                    size()]);
            ActivityCompat.requestPermissions(CurrentLocationDemo.this, permissions, 1);
        } else {
            requestLocation();
        }
    }
    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序",
                                    Toast.LENGTH_SHORT).show();
                            positionText.setText("必须同意所有权限才能使用本程序");
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder currentPosition = new StringBuilder();
                    currentPosition.append("纬度：").append(location.getLatitude()).
                            append("\n");
                    Latitude1=location.getLatitude();
                    currentPosition.append("经线：").append(location.getLongitude()).
                            append("\n");
//                    Longitude1=location.getLongitude();
//                    currentPosition.append("国家：").append(location.getCountry()).
//                            append("\n");
//                    currentPosition.append("省：").append(location.getProvince()).
//                            append("\n");
//                    currentPosition.append("市：").append(location.getCity()).
//                            append("\n");
//                    currentPosition.append("区：").append(location.getDistrict()).
//                            append("\n");
//                    currentPosition.append("街道：").append(location.getStreet()).
//                            append("\n");
//                    currentPosition.append("位置变化：").append("\n").
//                            append("经度变化：").append(Longitude1-Longitude2).append("\n").
//                            append("纬度变化：").append(Latitude1-Latitude2).append("\n");
//                    Longitude2=Longitude1;
//                    Latitude2=Latitude1;
//                    Log.i(TAG, "run: "+(Longitude1-Longitude2));
//                    currentPosition.append("定位方式：");
//                    if (location.getLocType() == BDLocation.TypeGpsLocation) {
//                        currentPosition.append("GPS");
//                    } else if (location.getLocType() ==
//                            BDLocation.TypeNetWorkLocation) {
//                        currentPosition.append("网络");
//                    }
                    positionText.setText("Lat: \" 39.244402 \"  Long: \" 117.065624");
                }
            });
        }
        public void onConnectHotSpotMessage(String s, int i) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
}
