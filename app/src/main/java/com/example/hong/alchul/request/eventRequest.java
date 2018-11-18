package com.example.hong.alchul.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class eventRequest extends StringRequest {
    final static private String URL = "http://10.0.2.2/test2.php";
    private Map<String, String> parameters;

    public eventRequest(String userId, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("userId", userId);

    }

    public Map<String, String> getParams() {
        return parameters;
    }

}
