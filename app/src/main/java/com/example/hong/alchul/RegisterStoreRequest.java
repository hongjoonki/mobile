package com.example.hong.alchul;

import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterStoreRequest extends StringRequest {

    final static private String URL = "http://10.0.2.2/teamproject/RegisterStore.php";
    private Map<String, String> parameters;


    public RegisterStoreRequest(String storeName, String storeCode, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("storeName", storeName);
        parameters.put("storeCode", storeCode);
    }

    public Map<String, String> getParams() {
        return parameters;
    }

}
