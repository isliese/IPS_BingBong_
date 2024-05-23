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
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class book_recommend extends Fragment {

    private TextView tag1, tag2, tag3;
    private String userEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_recommend, container, false);

        tag1 = view.findViewById(R.id.tag1);
        tag2 = view.findViewById(R.id.tag2);
        tag3 = view.findViewById(R.id.tag3);

        String selectedDate = getCurrentDate();

        fetchTags(selectedDate);

        // music 버튼 찾기
        Button music_button = view.findViewById(R.id.music);

        ImageButton bookImgButton1 = view.findViewById(R.id.book_img1);
        String book_imageUrl1 = "https://image.aladin.co.kr/product/33010/94/coversum/8917239501_1.jpg";
        Glide.with(requireContext()).load(book_imageUrl1).into(bookImgButton1);

        ImageButton bookImgButton2 = view.findViewById(R.id.book_img2);
        String book_imageUrl2 = "https://image.aladin.co.kr/product/33034/23/coversum/k912937580_1.jpg";
        Glide.with(requireContext()).load(book_imageUrl2).into(bookImgButton2);

        ImageButton bookImgButton3 = view.findViewById(R.id.book_img3);
        String book_imageUrl3 = "https://image.aladin.co.kr/product/1191/84/coversum/8971994290_2.jpg";
        Glide.with(requireContext()).load(book_imageUrl3).into(bookImgButton3);

        music_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 새로운 Fragment 인스턴스
                Fragment fragment_music_recommend = new music_recommend();

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fragment_home_content, fragment_music_recommend);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        // 이미지 버튼 클릭 리스너 설정
        bookImgButton1.setOnClickListener(v -> openUrl("http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=330109498&amp;partner=openAPI&amp;start=api"));
        bookImgButton2.setOnClickListener(v -> openUrl("http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=330342383&amp;partner=openAPI&amp;start=api"));
        bookImgButton3.setOnClickListener(v -> openUrl("http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=11918421&amp;partner=openAPI&amp;start=api"));


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
