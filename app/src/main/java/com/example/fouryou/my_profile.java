package com.example.fouryou;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class my_profile extends Fragment {

    private TextView userNameTextView;
    private TextView userEmailTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        // 사용자 이름과 이메일을 표시할 텍스트뷰 찾기
        userNameTextView = view.findViewById(R.id.userNameTextView);
        userEmailTextView = view.findViewById(R.id.userEmailTextView);

        // SharedPreferences에서 사용자 이름과 이메일 가져오기
        String userName = getUserData("userName");
        String userEmail = getUserData("userEmail");

        // 텍스트뷰에 사용자 이름과 이메일 설정
        userNameTextView.setText(userName);
        userEmailTextView.setText(userEmail);

        return view;
    }

    // SharedPreferences에서 사용자 데이터 가져오는 메서드
    private String getUserData(String key) {
        // SharedPreferences 객체 생성
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        // 해당 키로 저장된 데이터 가져오기, 없으면 기본값은 ""
        return sharedPreferences.getString(key, "");
    }
}
