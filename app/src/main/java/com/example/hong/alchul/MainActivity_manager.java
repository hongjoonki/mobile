package com.example.hong.alchul;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity_manager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manager);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("UserId");
        String userPassword = intent.getStringExtra("UserPassword");
        String userName = intent.getStringExtra("UserName");
        String userPhoneNum = intent.getStringExtra("UserPhoneNum");
        String userStat = intent.getStringExtra("UserStat");

        String message = "회원정보: " + userStat + "\n안녕하십니까 " + userId + "님";

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
