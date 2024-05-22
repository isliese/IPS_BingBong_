package com.example.fouryou;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Edit_Diary extends Fragment {

    private TextView viewDatePick;
    private TextView diaryTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit__diary, container, false);

        viewDatePick = view.findViewById(R.id.veiwDatePick);
        diaryTextView = view.findViewById(R.id.written_diary_text);

        Bundle bundle = getArguments();

        if (bundle != null) {
            String selectedDate = bundle.getString("selectedDate");
            viewDatePick.setText(selectedDate);
            // 선택한 날짜에 해당하는 일기를 가져옴
            fetchDiary(selectedDate);
        } else {
            String currentDate = getCurrentDate();
            viewDatePick.setText(currentDate);
        }

        return view;
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    private void fetchDiary(String date) {
        // SharedPreferences에서 사용자 이메일 가져오기
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userEmail", "");

        // 일기 가져오기 요청 생성
        CheckDiaryRequest diaryRequest = new CheckDiaryRequest(userEmail, date,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ServerResponse", response); // 응답 데이터를 로그로 출력
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                // 일기가 있는 경우 표시
                                JSONArray diariesArray = jsonResponse.getJSONArray("diaries");
                                JSONObject diaryObject = diariesArray.getJSONObject(0); // 가정: 하나의 일기만 가져옴
                                String diaryContent = diaryObject.getString("Content");
                                diaryTextView.setText(diaryContent); // TextView에 일기 내용 설정
                            } else {
                                // 해당 날짜에 저장된 일기가 없는 경우
                                String message = jsonResponse.getString("message");
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("JSONError", "JSON parsing error: " + e.getMessage()); // 추가된 로그 출력
                            Toast.makeText(requireContext(), "일기를 가져오는 중 JSON 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        String errorMsg;
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            errorMsg = new String(error.networkResponse.data);
                        } else {
                            errorMsg = error.getMessage();
                        }
                        Log.e("VolleyError", errorMsg); // 수정된 부분: 로그 출력
                        Toast.makeText(requireContext(), "일기를 가져오는 중 오류가 발생했습니다: " + errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Volley 요청 큐에 요청 추가
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(diaryRequest);
    }
}
