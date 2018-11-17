package com.example.hong.alchul;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class partime_home extends AppCompatActivity {
    Bundle bundle = new Bundle(5);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partime_home);

        Intent intent = getIntent();
        final String userId = intent.getStringExtra("UserId");
        String userName = intent.getStringExtra("UserName");
        String userPhoneNum = intent.getStringExtra("UserPhoneNum");
        String userStat = intent.getStringExtra("UserStat");
        String storeCode = intent.getStringExtra("StoreCode");
        String message = "회원정보: " + userStat + "\n안녕하십니까 " + userId + "님";
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();        //intent 받아오기

        LinearLayout button1 = (LinearLayout)findViewById(R.id.button1);
        LinearLayout button2 = (LinearLayout)findViewById(R.id.button2);
        LinearLayout button3 = (LinearLayout)findViewById(R.id.button3);           //버튼 인식
    //fragment에 정보넘길것들.



        bundle.putString("UserId", userId);
        bundle.putString("UserName", userName);
        bundle.putString("UserPhoneNum", userPhoneNum);
        bundle.putString("UserStat", userStat);
        bundle.putString("StoreCode", storeCode);         //번들에 값 추기


        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                MyFragment1 fragment1= new MyFragment1();
                fragment1.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment1).commit();

            }
        });

        button2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                MyFragment2 fragment2 = new MyFragment2();
                fragment2.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment2).commit();

            }
        });

        button3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                MyFragment3 fragment3 = new MyFragment3();
                fragment3.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment3).commit();

            }
        });



    }
}

