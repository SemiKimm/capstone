package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_mypg; //마이페이지 임시 버튼
    Button btn_posting;
    Button btn_getpostlist;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        // 로그인, 자동로그인 상태 가져오기
        String loginStatus = sharedPreferences.getString("loginstate", "");
        String autoLoginStatus = sharedPreferences.getString(getResources().getString(R.string.prefAutoLoginState), "");

        //마이페이지로 이동 (임시)
        btn_mypg = findViewById(R.id.button7);
        btn_mypg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 로그인 상태이면 바로 mypage로 이동
                if(loginStatus.equals("loggedin")&&autoLoginStatus.equals("autoLogin")){
                    //Toast.makeText(getApplicationContext(),"자동 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MypageActivity.class);
                    startActivity(intent);
                } else { // 로그아웃 상태이면 login으로 이동
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        });

        btn_posting = findViewById(R.id.posting);
        btn_posting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SelectPostingActivity.class);
                startActivity(intent);

            }
        });

        btn_getpostlist = findViewById(R.id.GetPostList);
        btn_getpostlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, GetPostListActivity.class);
                startActivity(intent);

            }
        });
    }
}