package com.example.hong.alchul.request;

import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterStoreRequest extends StringRequest {
    // RegisterStore.php 파일사용
    // store 테이블에 데이터 Insert 및 user테이블에 해당 userId에 있는 storeCode값 변경
    final static private String URL = "http://10.0.2.2/teamproject/RegisterStore.php";
    private Map<String, String> parameters;


    public RegisterStoreRequest(String storeName, String storeCode, String userId, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("storeName", storeName);
        parameters.put("storeCode", storeCode);
        parameters.put("userId", userId);
    }

    public Map<String, String> getParams() {
        return parameters;
    }

}
