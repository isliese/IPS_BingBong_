package com.example.fouryou;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DiaryRequest extends StringRequest{
    // 서버 URL
    // 서버 URL
    final static private String URL = "http://hyeonseo0457.dothome.co.kr/Diary.php";
    private Map<String, String> map;

    public DiaryRequest(String userEmail, String selectedDate, String content, Response.Listener<String> listener) { // 수정된 부분: date를 selectedDate로 변경
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userEmail", userEmail);
        map.put("selectedDate", selectedDate); // 수정된 부분: date를 selectedDate로 변경
        map.put("content", content);
        map.put("requestType", "load");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}