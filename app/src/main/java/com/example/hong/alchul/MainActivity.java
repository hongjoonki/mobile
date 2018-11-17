package com.example.hong.alchul;

import android.content.DialogInterface;
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
        String userId = intent.getStringExtra("UserId");
        String userName = intent.getStringExtra("UserName");
        String userPhoneNum = intent.getStringExtra("UserPhoneNum");
        String userStat = intent.getStringExtra("UserStat");
        userId = intent.getStringExtra("UserId");
        userName = intent.getStringExtra("UserName");
        userPhoneNum = intent.getStringExtra("UserPhoneNum");
        userStat = intent.getStringExtra("UserStat");

        findText = (EditText) findViewById(R.id.findText);

        String message = "회원정보: " + userStat + "\n안녕하십니까 " + userId + "님";   // 화면 오른쪽 위에 user 이름 표시

        TextView textView;
        textView = (TextView) findViewById(R.id.NameView);
        textView.setText(userName);

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void clickenter(View view) {
        Intent enterIntent = new Intent(MainActivity.this, partime_home.class);
        storeCode = findText.getText().toString();

        if (find.equals("OK")) {
            enterIntent.putExtra("UserId", userId);
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
                        builder.setMessage("OK")
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
