package com.example.fouryou;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Calendar_Page extends Fragment {

    private CalendarView calendarView;
    private Button dateButton;
    private String selectedMeetingDate = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calender, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        dateButton = view.findViewById(R.id.dateButton);

        dateButton.setText(getTodayDate() + " 기록 확인하기");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                selectedMeetingDate = selectedDate;
                dateButton.setText(selectedDate + " 기록 확인하기");
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼 클릭 시 해당 날짜의 기록을 확인하는 동작을 수행할 수 있습니다.
                Toast.makeText(getContext(), selectedMeetingDate + " 기록 확인", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }
}