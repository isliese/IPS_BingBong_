package com.example.fouryou;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class music_recommend extends Fragment {

    private TextView tag1, tag2, tag3;
    private String userEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_recommend, container, false);

        tag1 = view.findViewById(R.id.tag1);
        tag2 = view.findViewById(R.id.tag2);
        tag3 = view.findViewById(R.id.tag3);



        // book 버튼 찾기
        Button bookButton = view.findViewById(R.id.book);
        ImageButton musicImgButton1 = view.findViewById(R.id.music_img1);
        ImageButton musicImgButton2 = view.findViewById(R.id.music_img2);
        ImageButton musicImgButton3 = view.findViewById(R.id.music_img3);

        String musicImageUrl1 = "https://i.scdn.co/image/ab67616d0000b273c3040848e6ef0e132c5c8340";
        String musicImageUrl2 = "https://i.scdn.co/image/ab67616d0000b2735e352f6eccf8cb96d0b247cc";
        String musicImageUrl3 = "https://i.scdn.co/image/ab67616d0000b2732abcc266597eb46f897a8666";
        Glide.with(requireContext()).load(musicImageUrl1).into(musicImgButton1);
        Glide.with(requireContext()).load(musicImageUrl2).into(musicImgButton2);
        Glide.with(requireContext()).load(musicImageUrl3).into(musicImgButton3);

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragmentBookRecommend = new book_recommend();

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fragment_home_content, fragmentBookRecommend);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        // 이미지 버튼 클릭 리스너 설정
        musicImgButton1.setOnClickListener(v -> openUrl("https://open.spotify.com/track/2peoFPokM6eYAIwLm9IQ8E"));
        musicImgButton2.setOnClickListener(v -> openUrl("https://open.spotify.com/track/74X2u8JMVooG2QbjRxXwR8"));
        musicImgButton3.setOnClickListener(v -> openUrl("https://open.spotify.com/track/4l0Mvzj72xxOpRrp6h8nHi"));

        return view;
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void fetchTags(String selectedDate) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userEmail = sharedPreferences.getString("userEmail", "");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("ServerResponse", response);

                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        String keywordTag1 = jsonResponse.optString("Keyword_Tag1", "");
                        String keywordTag2 = jsonResponse.optString("Keyword_Tag2", "");
                        String emotionTag = jsonResponse.optString("Emotion_Tag", "");

                        if (keywordTag1.isEmpty()) {
                            keywordTag1 = "태그 없음";
                        }
                        if (keywordTag2.isEmpty()) {
                            keywordTag2 = "태그 없음";
                        }
                        if (emotionTag.isEmpty()) {
                            emotionTag = "태그 없음";
                        }

                        tag1.setText(keywordTag1);
                        tag2.setText(keywordTag2);
                        tag3.setText(emotionTag);
                    } else {
                        String errorMessage = jsonResponse.optString("error", "Unknown error");
                        Log.e("ServerResponseError", errorMessage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("JSONParsingError", e.getMessage());
                }
            }
        };

        Response.ErrorListener errorListener = error -> {
            error.printStackTrace();
            Log.e("VolleyError", error.toString());
        };

        Log.d("FetchTags", "userEmail: " + userEmail + ", selectedDate: " + selectedDate);

        rec_TagRequest tagRequest = new rec_TagRequest(userEmail, selectedDate, responseListener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(tagRequest);
    }
}
