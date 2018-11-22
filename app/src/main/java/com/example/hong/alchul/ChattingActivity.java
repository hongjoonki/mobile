package com.example.hong.alchul;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ChattingActivity extends Activity{

    String userId;
    String userName;
    String userPhoneNum;
    String userStat;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        userId = intent.getStringExtra("UserId");
        userName = intent.getStringExtra("UserName");
        userPhoneNum = intent.getStringExtra("UserPhoneNum");
        userStat = intent.getStringExtra("UserStat");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        // 이제 데이터베이스에 올리기 받아오기해야함
        // send버튼누르면 올리고, 받아오는건 실시간으로 구현!!!!


    }
}
