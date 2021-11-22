package com.example.capstone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    private EditText et_login_id, et_login_password;
    private Button login_button, join_button;
    private AlertDialog dialog;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        // 로그인 폼 입력값 찾아주기
        et_login_id = findViewById( R.id.id );
        et_login_password = findViewById( R.id.password );

        // 회원가입 버튼 클릭 시 수행
        join_button = findViewById( R.id.registerButton );
        join_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( LoginActivity.this, RegisterActivity.class );
                startActivity( intent );
            }
        });

        // 로그인 버튼 클릭 시 수행
        login_button = findViewById( R.id.loginButton );
        login_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login_id = et_login_id.getText().toString();
                String login_pwd = et_login_password.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );

                            if(success) {//로그인 성공시

                                final String login_id = jsonObject.getString( "login_id" );
                                String user_name = jsonObject.getString( "user_name" );
                                int count = Integer.parseInt(jsonObject.getString("count"));
                                String scount = Integer.toString(count);

                                if(count >= 5){ //신고횟수 파악
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(getResources().getString(R.string.prefAutoLoginState),"non-autoLogin");
                                    editor.putString(getResources().getString(R.string.prefLoginState),"loggedout");
                                    editor.commit();

                                    // 로그인 실패 메시지 출력
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    dialog = builder.setMessage(String.format("신고 %s회 누적으로 사용이 제한되었습니다.\n문의 : kce6064@naver.com", scount)).setNegativeButton("확인", null).create();
                                    dialog.show();
                                    return;
                                } else {

                                    // sharedprefernece에 정보 저장
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("inputId", login_id);
                                    editor.putString(getResources().getString(R.string.prefLoginState), "loggedin");
                                    editor.putString(getResources().getString(R.string.prefAutoLoginState), "autoLogin");
                                    editor.commit();

                                    // 로그인 성공 메시지 출력
                                    Toast.makeText(getApplicationContext(), String.format("%s님 로그인에 성공하였습니다.", user_name), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MypageActivity.class);
                                    startActivity(intent);
                                }

                            } else {// 로그인 실패시
                                // sharedprefernece에 정보 저장
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(getResources().getString(R.string.prefLoginState), "loggedout");
                                editor.putString(getResources().getString(R.string.prefAutoLoginState),"non-autoLogin");
                                editor.commit();

                                // 로그인 실패 메시지 출력
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("아이디 또는 비밀번호가 일치하지 않습니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //서버로 Volley를 이용해서 로그인 처리 요청
                LoginRequest loginRequest = new LoginRequest( login_id, login_pwd, responseListener );
                RequestQueue queue = Volley.newRequestQueue( LoginActivity.this );
                queue.add( loginRequest );

            }
        });



    }
}