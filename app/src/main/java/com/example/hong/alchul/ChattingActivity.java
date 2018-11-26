package com.example.hong.alchul;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ChattingActivity extends Activity{

    String userId;
    String userName;
    String userPhoneNum;
    String userStat;
    String storeCode;
    EditText editText;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ListView listView;

    ArrayList<String> array = new ArrayList<>();
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        editText = (EditText)findViewById(R.id.send_txt);
        array.add("홍준기");

        Intent intent = getIntent();
        userId = intent.getStringExtra("UserId");
        userName = intent.getStringExtra("UserName");
        userPhoneNum = intent.getStringExtra("UserPhoneNum");
        userStat = intent.getStringExtra("UserStat");
        storeCode = intent.getStringExtra("StoreCode");

        listView = (ListView) findViewById(R.id.listView);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.chat_item, array);



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
    }
}
