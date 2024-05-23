package com.example.fouryou;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Forest extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forest, container, false);

        // 나뭇잎 클릭 리스너 설정
        ImageView leaf1 = view.findViewById(R.id.leaf1);
        ImageView leaf2 = view.findViewById(R.id.leaf2);
        ImageView leaf3 = view.findViewById(R.id.leaf3);

        leaf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateAlert("2024-05-22");
            }
        });

        leaf2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateAlert("2024-05-20");
            }
        });

        leaf3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateAlert("2024-05-21");
            }
        });

        return view;
    }

    // 날짜를 보여주는 알림창 표시 메서드
    private void showDateAlert(String date) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Keyword : 하루")
                .setMessage("  일기 날짜는 " + date + "😉")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 사용자가 확인 버튼을 클릭했을 때 수행할 작업
                    }
                })
                .show();
    }
}