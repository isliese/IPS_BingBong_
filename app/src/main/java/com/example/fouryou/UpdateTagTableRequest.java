package com.example.fouryou;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateTagTableRequest extends StringRequest {
    // 서버 URL
    private static final String URL = "http://hyeonseo0457.dothome.co.kr/update_tag.php"; // 실제 서버 URL로 변경해야 함
    private final Map<String, String> params;

    public UpdateTagTableRequest(String userEmail, String date, String keywordTag1, String keywordTag2, String emotionTag, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("userEmail", userEmail);
        params.put("date", date);
        params.put("keywordTag1", keywordTag1);
        params.put("keywordTag2", keywordTag2);
        params.put("emotionTag", emotionTag);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}

