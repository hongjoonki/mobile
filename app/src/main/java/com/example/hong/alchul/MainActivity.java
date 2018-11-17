package com.example.hong.alchul;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("UserId");
        String userPassword = intent.getStringExtra("UserPassword");
        String userName = intent.getStringExtra("UserName");
        String userPhoneNum = intent.getStringExtra("UserPhoneNum");
        String userStat = intent.getStringExtra("UserStat");

        String message = "회원정보: " + userStat + "\n안녕하십니까 " + userId + "님";

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void clickenter(View view) {
        Intent enterIntent = new Intent(MainActivity.this, partime_home.class);
        MainActivity.this.startActivity(enterIntent);

    }
    public void btn_logout(View v) {
        new AlertDialog.Builder(this)
                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .show();
    }

}
