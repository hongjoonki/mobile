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
        Toast.makeText(this,"dfs",Toast.LENGTH_SHORT).show();

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
                        String userPassword = jsonResponse.getString("userPassword");
                        String userPhoneNum = jsonResponse.getString("userPhoneNum");
                        String userName = jsonResponse.getString("userName");
                        String userStat = jsonResponse.getString("userStat");

                        if(userStat.equals("manager")) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity_manager.class);
                            intent.putExtra("UserId", userId);
                            intent.putExtra("UserPassword", userPassword);
                            intent.putExtra("UserName", userName);
                            intent.putExtra("UserPhoneNum", userPhoneNum);
                            intent.putExtra("UserStat", userStat);
                            LoginActivity.this.startActivity(intent);
                        } else {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("UserId", userId);
                            intent.putExtra("UserPassword", userPassword);
                            intent.putExtra("UserName", userName);
                            intent.putExtra("UserPhoneNum", userPhoneNum);
                            intent.putExtra("UserStat", userStat);
                            LoginActivity.this.startActivity(intent);
                        }
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Login에 실패하였습니다.")
                                .setNegativeButton("다시 시도", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LoginRequest loginRequest = new LoginRequest(id, password,responseListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);
    }
}
