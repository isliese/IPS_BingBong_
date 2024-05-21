package com.example.fouryou;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DiaryRequest extends StringRequest {
    private static final String SAVE_DIARY_URL = "http://yourserver.com/saveDiary"; // 일기 저장을 위한 서버 URL
    private static final String LOAD_DIARY_URL = "http://yourserver.com/loadDiary"; // 일기 불러오기를 위한 서버 URL
    private final Map<String, String> params;

    // 일기 저장을 위한 생성자
    public DiaryRequest(String userEmail, String date, String content, Response.Listener<String> listener) {
        super(Method.POST, SAVE_DIARY_URL, listener, null);
        params = new HashMap<>();
        params.put("userEmail", userEmail);
        params.put("date", date);
        params.put("content", content);
    }

    // 일기 불러오기를 위한 생성자
    public DiaryRequest(String userEmail, String date, Response.Listener<String> listener) {
        super(Method.POST, LOAD_DIARY_URL, listener, null);
        params = new HashMap<>();
        params.put("userEmail", userEmail);
        params.put("date", date);
    }

    @Override
    protected Map<String, String> getParams() {
        return params;
    }
}
