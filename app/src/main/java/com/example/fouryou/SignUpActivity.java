package com.example.fouryou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class SignUpActivity extends AppCompatActivity {

    private EditText mName, mEmail, mPassword;
    private Button signup_button;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        // 아이디값 찾아주기
        mEmail = findViewById(R.id.user_email);
        mPassword = findViewById(R.id.user_pw);
        mName = findViewById(R.id.user_name);

        signup_button = findViewById(R.id.signup_btn);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mName.getText().toString();
                String userEmail = mEmail.getText().toString();
                String userPassword = mPassword.getText().toString();

                if (userEmail.equals("") || userPassword.equals("") || userName.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    dialog = builder.setMessage("모두 입력해주세요").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                Toast.makeText(getApplicationContext(), "가입을 환영합니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "회원가입에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                SignUpRequest signUpRequest = new SignUpRequest(userName, userEmail, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                queue.add(signUpRequest);
            }
        });
    }

}
