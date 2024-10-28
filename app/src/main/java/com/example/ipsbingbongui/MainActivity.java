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

import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    // 날짜별 일기 데이터를 저장할 HashMap 초기화
    HashMap<String, String> diaryEntries = new HashMap<>();

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


        initializeDiaryEntries();


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
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // Adding Calendar tab
        TabLayout.Tab tabCalendar = tabLayout.newTab();
        tabCalendar.setText("Calendar");
        tabLayout.addTab(tabCalendar);

        // Adding Forest tab
        TabLayout.Tab tabForest = tabLayout.newTab();
        tabForest.setText("Forest");
        tabLayout.addTab(tabForest);

        // Adding Recommend tab
        TabLayout.Tab tabRecommend = tabLayout.newTab();
        tabRecommend.setText("Recommend");
        tabLayout.addTab(tabRecommend);

        // Optional: Set tab text appearance if needed
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                TextView textView = new TextView(this);
                textView.setText(tab.getText());
                textView.setTextSize(17);  // Set desired text size
                textView.setTextColor(getResources().getColor(R.color.black));  // Set desired text color
                textView.setGravity(android.view.Gravity.CENTER);
                tab.setCustomView(textView);
            }
        }

    }

    private void initializeDiaryEntries() {
        // 예제 데이터 추가
        diaryEntries.put("2024.05.23", "오늘은 좋은 날씨였다.");
        diaryEntries.put("2024.05.24", "친구와 함께 산책을 했다.");
        diaryEntries.put("2024.05.25", "책을 읽고 휴식을 취했다.");
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


    // 해시맵에 있는 날짜별 데이터를 보여주는 함수
    private void displayDiaryEntry(int day, int month, int year) {
        String dateKey = String.format(Locale.getDefault(), "%04d.%02d.%02d", year, month, day);
        String entry = diaryEntries.get(dateKey);
        if (entry != null) {
            Toast.makeText(this, entry, Toast.LENGTH_LONG).show(); // 데이터를 Toast로 표시
        } else {
            Toast.makeText(this, "해당 날짜의 데이터가 없습니다.", Toast.LENGTH_LONG).show();
        }
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

