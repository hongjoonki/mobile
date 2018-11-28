package com.example.hong.alchul;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GpsActivity extends Activity {

    private Button btnShowLocation, googlemap;
    private TextView txtLat;
    private TextView txtLon;
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

    private getmap gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        btnShowLocation = (Button) findViewById(R.id.btn_start);
        txtLat = (TextView)findViewById(R.id.tv_latitude);
        txtLon = (TextView)findViewById(R.id.tv_longitude);
        googlemap = (Button)findViewById(R.id.button4);


        googlemap.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GpsActivity.this, googlemap.class);
                startActivity(intent);
            }
        });

        //GPS정보 보여주기 위한 이벤트 클래스
        btnShowLocation.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                gps = new getmap(GpsActivity.this);
                //GPS사용유무
                if(gps.isGetLocation()){
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    txtLat.setText(String.valueOf(latitude));
                    txtLon.setText(String.valueOf(longitude));

                    Toast.makeText(
                            getApplicationContext(),
                            "당신의 위치 - \n위도: " + latitude + "\n경도: " + longitude,
                            Toast.LENGTH_LONG).show();
                } else {
                    // GPS 를 사용할수 없으므로
                    gps.showSettingsAlert();
                }
            }
        });
        }
       }

