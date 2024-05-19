package com.example.fouryou;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;
import java.util.HashMap;

public class LoginRequest extends StringRequest{

    // 서버 URL
    final static private String URL = "http://hyeonseo0457.dothome.co.kr/Login.php";
    private Map<String, String> map;

    public LoginRequest(String userEmail, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userEmail", userEmail);
        map.put("userPassword", userPassword);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
