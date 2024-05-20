package com.example.fouryou;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import android.widget.ImageButton;


public class music_recommend extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_recommend, container, false);

        // book 버튼 찾기
        Button book_button = view.findViewById(R.id.book);

        ImageButton musicImgButton1 = view.findViewById(R.id.music_img1);
        String music_imageUrl1 = "https://i.namu.wiki/i/N0P5TbMbpc2spx_zCQlDip9WM-7ZoO1_lo2IT5qp2-Hmr0gHmrokOLwy2K1OdG4Xtc6wQEYv6vaavMMMGXaPxA.webp";
        Glide.with(requireContext()).load(music_imageUrl1).into(musicImgButton1);

        ImageButton musicImgButton2 = view.findViewById(R.id.music_img2);
        String music_imageUrl2 = "https://i.namu.wiki/i/N0P5TbMbpc2spx_zCQlDip9WM-7ZoO1_lo2IT5qp2-Hmr0gHmrokOLwy2K1OdG4Xtc6wQEYv6vaavMMMGXaPxA.webp";
        Glide.with(requireContext()).load(music_imageUrl2).into(musicImgButton2);

        ImageButton musicImgButton3 = view.findViewById(R.id.music_img3);
        String music_imageUrl3 = "https://i.namu.wiki/i/N0P5TbMbpc2spx_zCQlDip9WM-7ZoO1_lo2IT5qp2-Hmr0gHmrokOLwy2K1OdG4Xtc6wQEYv6vaavMMMGXaPxA.webp";
        Glide.with(requireContext()).load(music_imageUrl3).into(musicImgButton3);

        book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 새로운 Fragment 인스턴스
                Fragment fragment_book_recommend = new book_recommend();

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fragment_home_content, fragment_book_recommend);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}