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


        et_login_id = findViewById( R.id.id );
        et_login_password = findViewById( R.id.password );

        join_button = findViewById( R.id.registerButton );
        join_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( LoginActivity.this, RegisterActivity.class );
                startActivity( intent );
            }
        });


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
                                String login_pwd = jsonObject.getString( "login_pwd" );
                                String user_name = jsonObject.getString( "user_name" );
                                String session = jsonObject.getString( "session" );

                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString("inputId", login_id);
                                //editor.putString("inputPwd", login_pwd);
                                editor.putString(getResources().getString(R.string.prefLoginState), "loggedin");
                                editor.putString(getResources().getString(R.string.prefAutoLoginState), "autoLogin");
                                editor.commit();


                                Toast.makeText(getApplicationContext(), String.format("%s님 로그인에 성공하였습니다.", user_name),Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent( LoginActivity.this, MypageActivity.class );
                                //intent.putExtra("user_name", user_name);
                                //intent.putExtra("session", session);
                                //intent.putExtra("login_id",sharedPreferences.getString("inputId",""));



                                startActivity( intent );

                            } else {//로그인 실패시
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(getResources().getString(R.string.prefLoginState), "loggedout");
                                editor.commit();
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
                LoginRequest loginRequest = new LoginRequest( login_id, login_pwd, responseListener );
                RequestQueue queue = Volley.newRequestQueue( LoginActivity.this );
                queue.add( loginRequest );

            }
        });

        /*
        String loginStatus = sharedPreferences.getString(getResources().getString(R.string.prefAutoLoginState), "");
        if (loginStatus.equals("autoLogin")){
            Toast.makeText(getApplicationContext(),"자동 로그인 되었습니다.",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);

            intent.putExtra("login_id", sharedPreferences.getString("inputId",""));
            //intent.putExtra("userPass", sharedPreferences.getString("inputPwd",""));
            startActivity(intent);
            //startActivity(new Intent(LoginActivity.this, MainActivity.class ));
        }

         */


    }
}