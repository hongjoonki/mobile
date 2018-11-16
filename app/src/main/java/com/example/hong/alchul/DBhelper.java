package com.example.hong.alchul;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class DBhelper extends StringRequest {
    final static private String URL = "http://10.0.2.2/teamproject/UserInformation.php";


    public DBhelper(String url, Response.Listener<String> listener) {
        super(url, listener, null);

    }
}
