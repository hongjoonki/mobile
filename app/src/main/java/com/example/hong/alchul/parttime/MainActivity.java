package com.example.hong.alchul.parttime;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.hong.alchul.LoginActivity;
import com.example.hong.alchul.R;
import com.example.hong.alchul.request.ConnectStoreRequest;
import com.example.hong.alchul.request.RegisterRequest;
import com.example.hong.alchul.request.storeRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String userId;
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
        userName = intent.getStringExtra("UserName");
        userPhoneNum = intent.getStringExtra("UserPhoneNum");
        userStat = intent.getStringExtra("UserStat");

        findText = (EditText) findViewById(R.id.findText);

        // 화면 오른쪽에 userName 표시
        TextView textView;
        textView = (TextView) findViewById(R.id.NameView);
        textView.setText(userName);

        // Toast를 이용해 userStat와 userId 표시
        String message = "회원정보: " + userStat + "\n안녕하십니까 " + userId + "님";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // ENTER 버튼 눌렀을 때 이벤트 추가
    public void clickenter(View view) {
        storeCode = findText.getText().toString();

        if (find.equals("OK")) {

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("정상적으로 실행하였습니다.")
                                    .setNegativeButton("확인", null)
                                    .create()
                                    .show();
                            Intent enterIntent = new Intent(MainActivity.this, partime_home.class);

                            enterIntent.putExtra("UserId", userId);
                            enterIntent.putExtra("UserName", userName);
                            enterIntent.putExtra("UserPhoneNum", userPhoneNum);
                            enterIntent.putExtra("UserStat", userStat);
                            enterIntent.putExtra("StoreCode", storeCode);

                            MainActivity.this.startActivity(enterIntent);
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("update쿼리가 제대로 작동하지 않았습니다..")
                                    .setNegativeButton("다시 시도", null)
                                    .create()
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            ConnectStoreRequest connectStoreRequest = new ConnectStoreRequest(userId, storeCode, responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(connectStoreRequest);

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("코드번호를 먼저 확인하십시오")
                    .setNegativeButton("다시 시도", null)
                    .create()
                    .show();
        }
    }

    // FIND STORE버튼 눌렀을 때 이벤트 추가
    public void clickfind(View view) {
        storeCode = findText.getText().toString();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String success = jsonResponse.getString("success");
                    // 해당 storeCode값의 store이 존재하면 success = "SORRY"
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
        // FindStoreRequest 객채 생성
        storeRequest findStoreRequest = new storeRequest(storeCode, responseListener);
        // queue 실행
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(findStoreRequest);

    }

    // LOGOUT 버튼 눌렀을 때 이벤트 추가
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
