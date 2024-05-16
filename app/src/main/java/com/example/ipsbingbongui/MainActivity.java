package com.example.ipsbingbongui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //    CalendarView calendarView;
    Calendar calendar;
    CalendarView calendarView;
    private Button dateButton;

    //  CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
//    long selectedDate = calendarView.getDate();
//    Button dateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView);
        dateButton = findViewById(R.id.dateButton);  // XML 파일에서 Button을 찾음

        calendar = Calendar.getInstance();

        // Set the initial date and update the button text
        setDate(23, 5, 2024); // 원하는 날짜로 설정
        getDate();  // Button 텍스트를 현재 날짜로 업데이트

        // Listener for date changes
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                setDate(day, month + 1, year);  // month는 0부터 시작하므로 1을 더함
                getDate();  // 새로운 날짜로 Button 텍스트를 업데이트
            }
        });
    }

    public void getDate() {
        long date = calendarView.getDate();  // 현재 CalendarView에서 선택된 날짜를 가져옴
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        calendar.setTimeInMillis(date);  // Calendar 인스턴스에 해당 날짜를 설정
        String selectedDate = simpleDateFormat.format(calendar.getTime());  // 날짜를 "yyyy.MM.dd" 형식으로 변환
        dateButton.setText(selectedDate + " 기록 확인하기");  // Button 텍스트를 설정
    }


    public void setDate(int day, int month, int year) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1); // month is 0-based
        calendar.set(Calendar.DAY_OF_MONTH, day);
        long milli = calendar.getTimeInMillis();
        calendarView.setDate(milli, true, true);
    }

}





//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//    public void onDateButtonClick(View view) {
//        // CalendarView에서 선택된 날짜 가져오기
//        long selectedDate = calendarView.getDate();
//
//        // 선택된 날짜를 버튼 텍스트로 설정하기
//        dateButton.setText(formatDate(selectedDate));
//    }
//    // 선택된 날짜를 원하는 형식으로 포맷하는 메서드
//    private String formatDate(long date) {
//        // 여기서 날짜를 원하는 형식으로 포맷합니다. 예를 들어, SimpleDateFormat을 사용하여 포맷할 수 있습니다.
//        // 이 예제에서는 간단하게 년, 월, 일을 추출하여 문자열로 반환합니다.
//        int year = getYearFromDate(date);
//        int month = getMonthFromDate(date) + 1; // 월은 0부터 시작하므로 1을 더해줍니다.
//        int dayOfMonth = getDayOfMonthFromDate(date);
//
//        return year + "." + String.format("%02d", month) + "." + String.format("%02d", dayOfMonth) + " 기록 확인하기";
//    }
//
//    // long 형식의 날짜에서 연도를 추출하는 메서드
//    private int getYearFromDate(long date) {
//        return Integer.parseInt(android.text.format.DateFormat.format("yyyy", date).toString());
//    }
//
//    // long 형식의 날짜에서 월을 추출하는 메서드
//    private int getMonthFromDate(long date) {
//        return Integer.parseInt(android.text.format.DateFormat.format("MM", date).toString());
//    }
//
//    // long 형식의 날짜에서 일을 추출하는 메서드
//    private int getDayOfMonthFromDate(long date) {
//        return Integer.parseInt(android.text.format.DateFormat.format("dd", date).toString());
//    }
//}

