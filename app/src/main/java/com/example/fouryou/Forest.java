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

public class Forest extends Fragment {

    private ImageButton eachTreeButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forest, container, false);

        eachTreeButton = view.findViewById(R.id.each_tree);
        eachTreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tree_Forest 프래그먼트로 이동
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Tree_Forest treeForest = new Tree_Forest();

                // 프래그먼트 전환 시 배경 초기화
                requireActivity().getWindow().setBackgroundDrawable(null);

                fragmentTransaction.replace(R.id.fragment_home_content, treeForest);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}
