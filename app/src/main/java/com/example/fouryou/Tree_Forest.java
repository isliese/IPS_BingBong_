package com.example.fouryou;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Tree_Forest extends Fragment {

    @Nullable
    @Override

    // 사용자가 Backforest 버튼을 클릭하면 Forest Fragment가 생성되고, fragment_home_content 영역에 표시됩니다.
    // 이전 Fragment는 백스택에 저장되어 백 버튼을 통해 돌아갈 수 있습니다.
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tree__forest, container, false);

        // Forest Fragment로 돌아가는 버튼 설정
        ImageButton Backforest = view.findViewById(R.id.entire_forest_button);
        Backforest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 새로운 Fragment 인스턴스
                Fragment forest  = new Forest();

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fragment_home_content, forest);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        // 나뭇잎 클릭 리스너 설정
        ImageView leaf1 = view.findViewById(R.id.leaf1);
        ImageView leaf2 = view.findViewById(R.id.leaf2);
        ImageView leaf3 = view.findViewById(R.id.leaf3);

        leaf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateOrder("2004-12-29");
            }
        });

        leaf2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateOrder("2004-05-07");
            }
        });

        leaf3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateOrder("2004-03-10");
            }
        });

        return view;
    }

    // 날짜를 보여주는 메서드
    private void showDateOrder(String date) {
        Toast.makeText(getActivity(), "Date: " + date, Toast.LENGTH_SHORT).show();
    }
}