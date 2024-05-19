package com.example.fouryou;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SignUpRequest extends StringRequest {

    // 서버 URL
    final static private String URL = "http://hyeonseo0457.dothome.co.kr/Signup.php";
    private Map<String, String> map;

    public SignUpRequest(String userName, String userEmail, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userName", userName);
        map.put("userEmail", userEmail);
        map.put("userPassword", userPassword);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
