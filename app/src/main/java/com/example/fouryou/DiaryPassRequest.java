package com.example.fouryou;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
public class DiaryPassRequest extends StringRequest{

    // 서버 URL
    final static private String URL = ""; // 삽입을 처리하는 PHP 파일의 URL로 변경
    private Map<String, String> map;

    public DiaryPassRequest(String content, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("content", content);
        map.put("requestType", "insert"); // 요청의 타입을 'insert'로 설정하여 데이터 삽입 요청임을 나타냄
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

