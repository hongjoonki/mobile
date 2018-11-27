package com.example.hong.alchul;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hong.alchul.manager.MainActivity_manager;
import com.example.hong.alchul.manager.manager_frag1;
import com.example.hong.alchul.manager.manager_home;
import com.example.hong.alchul.parttime.MyFragment3;
import com.example.hong.alchul.parttime.partime_home;
import com.example.hong.alchul.request.LoginRequest;
import com.example.hong.alchul.request.noticeRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class WriteNotice extends AppCompatActivity {

    String userId;
    String userName;
    String userPhoneNum;
    String userStat;
    String storeCode;

    TextView checkButton, name;
    EditText title, content;
    Bundle bundle = new Bundle(5);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_notice);

        Intent intent = getIntent();
        userId = intent.getStringExtra("UserId");
        userName = intent.getStringExtra("UserName");
        userPhoneNum = intent.getStringExtra("UserPhoneNum");
        userStat = intent.getStringExtra("UserStat");
        storeCode = intent.getStringExtra("StoreCode");
        title = (EditText) findViewById(R.id.edit1);
        content = (EditText)findViewById(R.id.edit2);

        name = (TextView)findViewById(R.id.name);



        name.setText(userName);

    }


    public void check(View view) {
        String title1 = title.getText().toString();
        String content1 = content.getText().toString();


        if(title1==null || title1.equals("")||content1==null||content1.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(WriteNotice.this);
            builder.setMessage("제목과 내용을 기입해 주세요")
                    .setPositiveButton("확인", null)
                    .create()
                    .show();
        }
            else{
            Log.i("test", title1);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(WriteNotice.this);
                            builder.setMessage("새 게시물이 등록되었습니다.")
                                    .setPositiveButton("확인", null)
                                    .create()
                                    .show();
                            Intent intent = new Intent(WriteNotice.this, manager_home.class);
                            intent.putExtra("UserId", userId);
                            intent.putExtra("UserName", userName);
                            intent.putExtra("UserPhoneNum", userPhoneNum);
                            intent.putExtra("UserStat", userStat);
                            intent.putExtra("StoreCode", storeCode);
                            WriteNotice.this.startActivity(intent);
                        }
                        // insert쿼리가 잘 작동하지 않았으므로 회원등록 실패 메세지 호출
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(WriteNotice.this);
                            builder.setMessage("글 등록에 실패했습니다.")
                                    .setNegativeButton("다시 시도", null)
                                    .create()
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            noticeRequest request = new noticeRequest(storeCode, userName, title1, content1, responseListener);
            // queue 실행
            RequestQueue queue = Volley.newRequestQueue(WriteNotice.this);
            queue.add(request);
            //홍준기


        }




    }

}
