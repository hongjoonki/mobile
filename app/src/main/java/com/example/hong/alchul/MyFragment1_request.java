package com.example.hong.alchul;

import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MyFragment1_request extends StringRequest {

    final static private String URL = "http://10.0.2.2:8080/teamproject/worktime.php";
    private Map<String, String> parameters;


    public MyFragment1_request(String userId, String workstart, String workend, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userId", userId);
        parameters.put("workstart", workstart);
        parameters.put("workend", workend);
    }

    public Map<String, String> getParams() {
        return parameters;
    }

}
