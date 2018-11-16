package com.example.hong.alchul;

import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://10.0.2.2/teamproject/Register.php";
    private Map<String, String> parameters;


    public RegisterRequest(String userId, String userPassword, String userName,
                           String userPhoneNum, String userStat, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
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
