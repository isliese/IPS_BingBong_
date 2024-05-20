package com.example.fouryou;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;


public class book_recommend extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_recommend, container, false);

        // music 버튼 찾기
        Button music_button = view.findViewById(R.id.music);

        ImageButton bookImgButton1 = view.findViewById(R.id.book_img1);
        String book_imageUrl1 = "https://i.namu.wiki/i/N0P5TbMbpc2spx_zCQlDip9WM-7ZoO1_lo2IT5qp2-Hmr0gHmrokOLwy2K1OdG4Xtc6wQEYv6vaavMMMGXaPxA.webp";
        Glide.with(requireContext()).load(book_imageUrl1).into(bookImgButton1);

        ImageButton bookImgButton2 = view.findViewById(R.id.book_img2);
        String book_imageUrl2 = "https://i.namu.wiki/i/N0P5TbMbpc2spx_zCQlDip9WM-7ZoO1_lo2IT5qp2-Hmr0gHmrokOLwy2K1OdG4Xtc6wQEYv6vaavMMMGXaPxA.webp";
        Glide.with(requireContext()).load(book_imageUrl1).into(bookImgButton2);

        ImageButton bookImgButton3 = view.findViewById(R.id.book_img3);
        String book_imageUrl3 = "https://i.namu.wiki/i/N0P5TbMbpc2spx_zCQlDip9WM-7ZoO1_lo2IT5qp2-Hmr0gHmrokOLwy2K1OdG4Xtc6wQEYv6vaavMMMGXaPxA.webp";
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
        return view;
    }
}