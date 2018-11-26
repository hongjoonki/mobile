package com.example.hong.alchul;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ChattingActivity extends Activity{

    String userId;
    String userName;
    String userPhoneNum;
    String userStat;
    String storeCode;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<String> array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        Intent intent = getIntent();
        userId = intent.getStringExtra("UserId");
        userName = intent.getStringExtra("UserName");
        userPhoneNum = intent.getStringExtra("UserPhoneNum");
        userStat = intent.getStringExtra("UserStat");
        storeCode = intent.getStringExtra("StoreCode");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        // 이제 데이터베이스에 올리기 받아오기해야함
        // send버튼누르면 올리고, 받아오는건 실시간으로 구현!!!!


    }
}
