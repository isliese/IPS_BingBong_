package com.example.fouryou;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AdditionalDataRequest extends StringRequest {
    // 서버 URL
    private static final String URL = "http://hyeonseo0457.dothome.co.kr/Additional_data.php"; // 실제 서버 URL로 변경해야 함
    private final Map<String, String> params;

    public AdditionalDataRequest(String userEmail, String date, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("userEmail", userEmail);
        params.put("date", date);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}

