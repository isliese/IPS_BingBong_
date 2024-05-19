package com.example.fouryou;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.fouryou.databinding.FragmentHomeBinding;

public class write_diaryFragment extends Fragment {

    private TextView viewDatePick;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_write_diary, container, false);
        viewDatePick = view.findViewById(R.id.veiwDatePick);

        String currentDate = getCurrentDate();
        viewDatePick.setText(currentDate);
        return view;
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }

}
