package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MypageActivity extends AppCompatActivity {

    private TextView tv_name, tv_session, tv_id;
    private Button home_button;
    public static String user_name, login_id, session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        final SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        final Button logout = (Button) findViewById(R.id.logoutButton);

/*
        home_button = findViewById( R.id.home );
        home_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MypageActivity.this, MainActivity.class );
                startActivity( intent );
            }
        });

 */

        tv_name = findViewById(R.id.tv_name);
        tv_session = findViewById(R.id.tv_session);
        tv_id = findViewById(R.id.tv_id);


        Intent intent = getIntent();
        String user_name = intent.getStringExtra("user_name");
        session = intent.getStringExtra("session");
        String login_id = getIntent().getStringExtra("login_id");




        tv_name.setText(user_name);
        tv_session.setText(session);
        tv_id.setText(login_id);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("inputId");
                editor.remove("inputPwd");
                editor.putString(getResources().getString(R.string.prefAutoLoginState),"non-autoLogin");
                editor.putString(getResources().getString(R.string.prefLoginState),"loggedout");
                editor.apply();
                editor.commit();
                startActivity(new Intent(MypageActivity.this, LoginActivity.class));
                finish();

            }
        });

    }




}