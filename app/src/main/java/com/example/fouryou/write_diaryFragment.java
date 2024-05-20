package com.example.fouryou;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import com.example.fouryou.databinding.FragmentHomeBinding;

public class write_diaryFragment extends Fragment {

    private TextView viewDatePick;
    private EditText diaryText;
    private Button writeDiaryButton;

    @Override
    public void onResume() {
        super.onResume();
        diaryText.setText(""); // EditText의 내용을 초기화합니다.
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_write_diary, container, false);

        viewDatePick = view.findViewById(R.id.veiwDatePick);
        diaryText = view.findViewById(R.id.diary_text);
        writeDiaryButton = view.findViewById(R.id.write_diary);

        String currentDate = getCurrentDate();
        viewDatePick.setText(currentDate);

        writeDiaryButton.setOnClickListener(v -> {
            saveDiaryToServer();
        });

        return view;
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }
    private void saveDiaryToServer() {
        String content = diaryText.getText().toString();
        String date = viewDatePick.getText().toString();
        String userEmail = "user@example.com"; // 실제 사용자의 이메일을 가져와야 함

        // Response 리스너 설정
        Response.Listener<String> responseListener = response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                boolean success = jsonObject.getBoolean("success");
                if (success) {
                    // 저장 성공 시 HomeFragment로 전환
                    switchToHomeFragment();
                    // EditText의 내용을 지웁니다.
                    diaryText.setText("");
                } else {
                    // 저장 실패 시 처리
                    // 서버에서 반환한 메시지에 따라 적절한 안내를 사용자에게 보여줍니다.
                    String message = jsonObject.getString("message");
                    if (message != null && !message.isEmpty()) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                        diaryText.setText("");
                    } else {
                        Toast.makeText(requireContext(), "일기를 저장하는 데 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };

        // DiaryRequest 생성 및 큐에 추가
        DiaryRequest diaryRequest = new DiaryRequest(userEmail, date, content, responseListener);
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(diaryRequest);
    }

    private void switchToHomeFragment() {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_main, new HomeFragment()); // fragment_container는 액티비티 레이아웃의 FragmentContainerView ID이어야 함
        transaction.addToBackStack(null);
        transaction.commit();
    }
}