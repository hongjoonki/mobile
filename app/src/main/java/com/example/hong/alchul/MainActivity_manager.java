package com.example.hong.alchul;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity_manager extends AppCompatActivity {

    String userId;
    String userPassword;
    String userName;
    String userPhoneNum;
    String userStat;
    String find = "";
    String storeCode;
    String storeName;

    EditText createText;
    EditText createCodeText;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manager);

        Intent intent = getIntent();
        userId = intent.getStringExtra("UserId");
        userPassword = intent.getStringExtra("UserPassword");
        userName = intent.getStringExtra("UserName");
        userPhoneNum = intent.getStringExtra("UserPhoneNum");
        userStat = intent.getStringExtra("UserStat");

        createText = (EditText) findViewById(R.id.createText);
        createCodeText = (EditText) findViewById(R.id.createCodeText);

        textView = (TextView) findViewById(R.id.NameView);
        textView.setText(userName);

        String message = "회원정보: " + userStat + "\n안녕하십니까 " + userId + "님";

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void createStore(View view) {

        storeCode = createCodeText.getText().toString();
        storeName = createText.getText().toString();

        Intent enterIntent = new Intent(MainActivity_manager.this, manager_home.class);

        if (find.equals("OK")) {

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_manager.this);
                            builder.setMessage("가게 등록 성공")
                                    .setPositiveButton("확인", null)
                                    .create()
                                    .show();

                            Intent enterIntent = new Intent(MainActivity_manager.this, manager_home.class);
                            enterIntent.putExtra("UserId", userId);
                            enterIntent.putExtra("UserName", userName);
                            enterIntent.putExtra("UserPhoneNum", userPhoneNum);
                            enterIntent.putExtra("UserStat", userStat);
                            enterIntent.putExtra("StoreCode", storeCode);
                            enterIntent.putExtra("StoreName", storeName);
                            MainActivity_manager.this.startActivity(enterIntent);
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_manager.this);
                            builder.setMessage("가게 등록 실패")
                                    .setNegativeButton("다시 시도", null)
                                    .create()
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            RegisterStoreRequest registerRequest = new RegisterStoreRequest(storeName, storeCode, responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity_manager.this);
            queue.add(registerRequest);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_manager.this);
            builder.setMessage("코드번호를 먼저 확인하십시오")
                    .setNegativeButton("다시 시도", null)
                    .create()
                    .show();
        }

    }

    public void checkStore(View view) {
        storeCode = createCodeText.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String success = jsonResponse.getString("success");
                    if (success.equals("OK")) {
                        find = "OK";
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_manager.this);
                        builder.setMessage("생성 가능한 코드입니다")
                                .setPositiveButton("확인", null)
                                .create()
                                .show();
                    }
                    else {
                        find="no";
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_manager.this);
                        builder.setMessage("이미 존재하는 코드입니다.")
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
        RequestQueue queue = Volley.newRequestQueue(MainActivity_manager.this);
        queue.add(findStoreRequest);
    }

    public void btn_logout(View v) {
        new AlertDialog.Builder(this)
                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent i = new Intent(MainActivity_manager.this, LoginActivity.class);
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
