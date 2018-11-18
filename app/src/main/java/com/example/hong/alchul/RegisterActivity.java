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
        // check버튼에 체크여부를 확인하기 위해 job변수 선언
        String job = null;

        // part버튼에 체크가 되어있으면 job = "part-time", manager버튼에 체크가 되어있으면 job = "manager" 저장
        if(partButton.isChecked()) {
            job = "part-time";
        }
        else if(managerButton.isChecked()) {
            job = "manager";
        }

        // check버튼중 아무것도 누르지 않았을때 메세지 호출
        if (job == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("part 또는 manager에 체크하세요")
                    .setNegativeButton("다시 시도", null)
                    .create()
                    .show();
        } else {

            // check버튼은 눌럿지만, 해당 Id의 유효성검사를 받지 않았을 때 메세지 호출
            if (checkId == "") {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("아이디가 유효한지 체크하세요")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }else {
                // Response.Listener형식의 객체 생성 (response 결과값을 받아서 실행할 코드)
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            // php파일 실행 결과로 Json형식의 response가 생성된다
                            // 이 결과를 Json형식으로 받아오기 위해 JSONObject를 만들어 주었다.
                            JSONObject jsonResponse = new JSONObject(response);

                            // 결과 json에서 "success"라는 키의 값을 success라는 변수에 boolean 형태로 저장한다.
                            boolean success = jsonResponse.getBoolean("success");

                            /* success의 값은 2가지 결과로 나온다.
                                 1.  해당 php코드가 문제없이 실행되었으면 sucess = true
                                 2.  해당 php코드에 문제가 생겼으면 sucess = false
                            */
                            // insert쿼리가 정상적으로 작동하였으므로 회원 등록 성공 메세지 호출
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원 등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }
                            // insert쿼리가 잘 작동하지 않았으므로 회원등록 실패 메세지 호출
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
                // RegistRequest 객채 생성 (파라미터 값으로 id, password, name, phonNum, job, response.Listener의 객체 전달)
                RegisterRequest registerRequest = new RegisterRequest(id, password, name, phoneNum, job, responseListener);
                /*
                RequestQueue 객체 생성 후 request할 클래스를 큐에 넣은후 실행
                Volley 사용을 위해서 build.gradle에  compile 'com.android.volley:volley:1.0.0'를 추가하여야 한다.
                또한, manifest파일에 인터넷 사용허가 추가 -> <uses-permission android:name="android.permission.INTERNET" />
                Request class로 RegisterRequest를 선언하였으므로 RegisterRequest 클래스로 이동하게 된다.
                */
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        }

    }

    // CHECK ID 버튼 눌렀을때 이벤트 추가 -> idText에 쓰여져있는 값이 user테이블에 존재하는지 확인 후 checkId값을 설정
    public void checkButton(View view) {
        // idText에 있는 값을 받아옴
        String id = idText.getText().toString();

        // Response.Listener형식의 객체 생성 (response 결과값을 받아서 실행할 코드)
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    // php파일 실행 결과로 Json형식의 response가 생성된다
                    // 이 결과를 Json형식으로 받아오기 위해 JSONObject를 만들어 주었다.
                    JSONObject jsonResponse = new JSONObject(response);

                    // 결과 json에서 "success"라는 키의 값을 success라는 변수에 string 형태로 저장한다.
                    String success = jsonResponse.getString("success");

                    /* success의 값은 2가지 결과로 나온다.
                         1.  idText에 있는 ID값이 user테이블에 있으면 "SORRY"
                         2.  idText에 있는 ID값이 user테이블에 없으면 "OK"
                    */
                    // success가 "OK"일 경우 해당 id의 값이 테이블에 없으므로 아이디 사용 가능 메세지 호출
                    if (success.equals("OK")) {

                        // checkId변수의 값을 ""에서 "OK"로 바꿔준다 -> 회원 등록이 가능해진다.
                        checkId = "OK";
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);

                        builder.setMessage("아이디 사용 가능.")
                                .setPositiveButton("확인", null)
                                .create()
                                .show();
                    }
                    // success가 "SORRY"일 경우 해당 id의 값이 테이블에 존재하므로 이미 존재한다고 알려주는 메세지 호출
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
        // IdCheckRequest 객채 생성 (파라미터 값으로 idText에 있는 id값과 response.Listener의 객체 전달)
        IdCheckRequest idCheckRequest = new IdCheckRequest(id, responseListener);

        // queue 실행
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(idCheckRequest);

    }

}
