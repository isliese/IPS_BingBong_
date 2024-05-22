package com.example.fouryou;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CheckDiaryRequest extends StringRequest {
    // 서버 URL
    final static private String URL = "http://hyeonseo0457.dothome.co.kr/CheckDiary.php";
    private Map<String, String> params; // 변수명 수정

    public CheckDiaryRequest(String userEmail, String date, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("userEmail", userEmail);
        params.put("date", date);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
