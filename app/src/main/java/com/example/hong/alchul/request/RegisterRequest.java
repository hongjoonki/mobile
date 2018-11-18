package com.example.hong.alchul.request;

import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//request를 받아서 결과값을 Response.Listener에 전달하는 RegisterRequest class
public class RegisterRequest extends StringRequest {

    // Request를 전달할 URL을 설정하였다.
    // 에뮬레이터를 이용하여 아파치 웹서버에 있는 php파일을 사용하기 위해 10.0.2.2주소를 사용하였다.
    // 사용된 php파일 이름: Register.php
    final static private String URL = "http://10.0.2.2:8080/teamproject/Register.php";

    // 여러 갯수의 파라미터를 한번에 전달하는 방법으로 Map을 아용하였다.
    private Map<String, String> parameters;

    // 파라미터를 해당 URL에 전달하기 위한 메소드
    public RegisterRequest(String userId, String userPassword, String userName,
                           String userPhoneNum, String userStat, Response.Listener<String> listener) {
        // POST방식으로 전달
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        // 파라미터는 "userId", "userPassword" ... 라는 이름의 POST방식으로 전달된다.
        parameters.put("userId", userId);
        parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
        parameters.put("userPhoneNum", userPhoneNum);
        parameters.put("userStat", userStat);
    }

    public Map<String, String> getParams() {
        return parameters;
    }

}
