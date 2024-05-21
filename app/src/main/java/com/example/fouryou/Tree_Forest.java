package com.example.fouryou;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Tree_Forest extends Fragment {

    private ImageButton entireForestButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tree__forest, container, false);

        entireForestButton = view.findViewById(R.id.entire_forest_button);
        entireForestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 다른 프래그먼트로 이동
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Forest 프래그먼트 인스턴스 생성
                Forest forestFragment = new Forest();

                // fragment_home_content 영역을 비웁니다.
                fragmentTransaction.remove(fragmentManager.findFragmentById(R.id.fragment_home_content));

                // fragment_home_content에 forestFragment로 전환
                fragmentTransaction.add(R.id.fragment_home_content, forestFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}
