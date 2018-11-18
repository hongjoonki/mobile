package com.example.hong.alchul.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class eventRequest extends StringRequest {
<<<<<<< HEAD
    final static private String URL = "http://10.0.2.2/test2.php";
=======
    final static private String URL = "http://10.0.2.2:8080/teamproject/workday.php";
    final static private String URL1 = "http://10.0.2.2:8080/teamproject/workday_event.php";
>>>>>>> 5233c51eedf3fc833d15c741e17e041a9476c3c2
    private Map<String, String> parameters;

    public eventRequest(String userId, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userId", userId);

    }

    public eventRequest(String userId, String startday, Response.Listener<String> listener) {
        super(Method.POST, URL1, listener, null);
        parameters = new HashMap<>();
        parameters.put("userId", userId);
        parameters.put("startday", startday);

    }

    public Map<String, String> getParams() {
        return parameters;
    }

}
