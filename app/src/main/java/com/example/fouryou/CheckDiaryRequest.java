package com.example.fouryou;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CheckDiaryRequest extends StringRequest {
    // 서버 URL
    final static private String URL = "http://hyeonseo0457.dothome.co.kr/CheckDiary.php";
    private Map<String, String> map;

    public CheckDiaryRequest(String userEmail, String date, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        map = new HashMap<>();
        map.put("userEmail", userEmail);
        map.put("date", date);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
