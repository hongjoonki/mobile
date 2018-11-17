package com.example.hong.alchul;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TabHost;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {

    EditText idText;
    EditText passwordText;
    Button loginButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idText = (EditText) findViewById(R.id.idText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        loginButton = (Button) findViewById(R.id.LoginButton);
        registerButton = (Button) findViewById(R.id.registerButton);


    }


    public void onClick2(View v) {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(registerIntent);
    }


    public void onClick1(View v) {
        String id = idText.getText().toString();
        String password = passwordText.getText().toString();


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        String userId = jsonResponse.getString("userId");
                        String userPhoneNum = jsonResponse.getString("userPhoneNum");
                        String userName = jsonResponse.getString("userName");
                        String userStat = jsonResponse.getString("userStat");
                        String storeCode = jsonResponse.getString("storeCode");
                        Log.d("test", "debug4");
                        if(userStat.equals("manager")) {

                            if (storeCode == null||storeCode.length()==0) {
                                Intent intent1 = new Intent(LoginActivity.this, manager_home.class);
                                intent1.putExtra("UserId", userId);
                                intent1.putExtra("UserName", userName);
                                intent1.putExtra("UserPhoneNum", userPhoneNum);
                                intent1.putExtra("UserStat", userStat);
                                LoginActivity.this.startActivity(intent1);
                            } else{
                                Intent intent = new Intent(LoginActivity.this, MainActivity_manager.class);
                                intent.putExtra("UserId", userId);
                                intent.putExtra("UserName", userName);
                                intent.putExtra("UserPhoneNum", userPhoneNum);
                                intent.putExtra("UserStat", userStat);
                                intent.putExtra("StoreCode", storeCode);
                                LoginActivity.this.startActivity(intent);
                            }
                        } else {

                            if (storeCode.equals("123")) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("UserId", userId);
                                intent.putExtra("UserName", userName);
                                intent.putExtra("UserPhoneNum", userPhoneNum);
                                intent.putExtra("UserStat", userStat);
                                LoginActivity.this.startActivity(intent);
                            } else{
                                Intent intent = new Intent(LoginActivity.this, partime_home.class);
                                intent.putExtra("UserId", userId);
                                intent.putExtra("UserName", userName);
                                intent.putExtra("UserPhoneNum", userPhoneNum);
                                intent.putExtra("UserStat", userStat);
                                intent.putExtra("StoreCode", storeCode);
                                LoginActivity.this.startActivity(intent);
                            }
                        }
                    }
                    else {
                        Log.d("test", "debug5");
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Login에 실패하였습니다.")
                                .setNegativeButton("다시 시도", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    Log.d("test", "debug6");
                    e.printStackTrace();
                }
            }
        };
        LoginRequest loginRequest = new LoginRequest(id, password,responseListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);
    }
}
