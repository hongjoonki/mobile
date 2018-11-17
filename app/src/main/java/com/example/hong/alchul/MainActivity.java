package com.example.hong.alchul;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String userId;
    String userPassword;
    String userName;
    String userPhoneNum;
    String userStat;

    EditText findText;
    String storeCode;

    String find = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        userId = intent.getStringExtra("UserId");
        userPassword = intent.getStringExtra("UserPassword");
        userName = intent.getStringExtra("UserName");
        userPhoneNum = intent.getStringExtra("UserPhoneNum");
        userStat = intent.getStringExtra("UserStat");

        findText = (EditText) findViewById(R.id.findText);

        // 화면 오른쪽 위에 user 이름 표시
        TextView textView;
        textView = (TextView) findViewById(R.id.NameView);
        textView.setText(userName);

    }

    public void clickenter(View view) {
        storeCode = findText.getText().toString();

        if (find.equals("OK")) {
            Intent enterIntent = new Intent(MainActivity.this, partime_home.class);
            enterIntent.putExtra("UserId", userId);
            enterIntent.putExtra("UserPassword", userPassword);
            enterIntent.putExtra("UserName", userName);
            enterIntent.putExtra("UserPhoneNum", userPhoneNum);
            enterIntent.putExtra("UserStat", userStat);
            enterIntent.putExtra("StoreCode", storeCode);
            MainActivity.this.startActivity(enterIntent);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("코드번호를 먼저 확인하십시오")
                    .setNegativeButton("다시 시도", null)
                    .create()
                    .show();
        }
    }

    public void clickfind(View view) {
        storeCode = findText.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String success = jsonResponse.getString("success");
                    if (success.equals("SORRY")) {
                        find = "OK";
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("OKOK")
                                .setPositiveButton("확인", null)
                                .create()
                                .show();
                    }
                    else {
                        find="no";
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("코드번호를 다시 입력하십시오.")
                                .setNegativeButton("다시 시도", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        FindStoreRequest findStoreRequest = new FindStoreRequest(storeCode, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(findStoreRequest);

    }
}
