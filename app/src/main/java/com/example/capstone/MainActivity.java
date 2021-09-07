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

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        String loginStatus = sharedPreferences.getString("loginstate", "");
        String autoLoginStatus = sharedPreferences.getString(getResources().getString(R.string.prefAutoLoginState), "");

        //마이페이지로 이동 (임시)
        btn_mypg = findViewById(R.id.button7);

        btn_mypg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(loginStatus.equals("loggedin")&&autoLoginStatus.equals("autoLogin")){
                    //Toast.makeText(getApplicationContext(),"자동 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MypageActivity.class);
                    intent.putExtra("login_id", sharedPreferences.getString("inputId",""));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}