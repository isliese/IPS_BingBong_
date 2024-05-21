package com.example.fouryou;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Edit_Diary extends Fragment {

    private TextView viewDatePick;
    private TextView diaryTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit__diary, container, false);

        viewDatePick = view.findViewById(R.id.veiwDatePick); // ID 수정
        diaryTextView = view.findViewById(R.id.written_diary_text);

        Bundle bundle = getArguments();

        if (bundle != null) {
            String selectedDate = bundle.getString("selectedDate");
            viewDatePick.setText(selectedDate);
        } else {
            String currentDate = getCurrentDate();
            viewDatePick.setText(currentDate);
        }

        return view;
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }
}
