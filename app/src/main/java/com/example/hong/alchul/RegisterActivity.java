package com.example.hong.alchul;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hong.alchul.request.IdCheckRequest;
import com.example.hong.alchul.request.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends Activity{

    EditText idText;
    EditText passwordText;
    EditText phoneText;
    EditText nameText;
    Button registerButton;
    RadioButton partButton;
    RadioButton managerButton;
    RadioGroup rg;
    String checkId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        idText = (EditText) findViewById(R.id.idText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        phoneText = (EditText) findViewById(R.id.PhoneText);
        nameText = (EditText) findViewById(R.id.nameText);
        registerButton = (Button) findViewById(R.id.registerButton);
        partButton = (RadioButton) findViewById(R.id.partButton);
        managerButton = (RadioButton) findViewById(R.id.managerButton);

        rg = (RadioGroup) findViewById(R.id.radio);

    }

    public void registerBt(View v) {
        String id = idText.getText().toString();
        String password = passwordText.getText().toString();
        String name = nameText.getText().toString();
        String phoneNum = phoneText.getText().toString();
        String job = null;

        if(partButton.isChecked()) {
            job = "part-time";
        }
        else if(managerButton.isChecked()) {
            job = "manager";
        }

        if (checkId == "") {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("아이디가 유효한지 체크하세요")
                    .setPositiveButton("확인", null)
                    .create()
                    .show();
        }else {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage("회원 등록에 성공했습니다.")
                                    .setPositiveButton("확인", null)
                                    .create()
                                    .show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            RegisterActivity.this.startActivity(intent);
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage("회원 등록에 실패했습니다.")
                                    .setNegativeButton("다시 시도", null)
                                    .create()
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            RegisterRequest registerRequest = new RegisterRequest(id, password, name, phoneNum, job, responseListener);
            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
            queue.add(registerRequest);
        }
    }

    public void checkButton(View view) {

        String id = idText.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String success = jsonResponse.getString("success");
                    if (success.equals("OK")) {
                        checkId = "OK";
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);

                        builder.setMessage("아이디 사용 가능.")
                                .setPositiveButton("확인", null)
                                .create()
                                .show();
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("이미 존재하는 아이디입니다.")
                                .setNegativeButton("다시 시도", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        };
        IdCheckRequest idCheckRequest = new IdCheckRequest(id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(idCheckRequest);

    }

}
