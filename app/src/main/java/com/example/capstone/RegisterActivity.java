package com.example.capstone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText join_id, join_password, join_name, join_pwck, join_phone;
    private Button join_button, check_button;
    private AlertDialog dialog;
    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        // 회원가입 폼 입력값 찾아주기
        join_id = findViewById( R.id.id );
        join_password = findViewById( R.id.password );
        join_name = findViewById( R.id.name );
        join_pwck = findViewById(R.id.passwordCheck);
        join_phone = findViewById(R.id.phone);

        // 아이디 중복체크 버튼 클릭 시 실행
        check_button = findViewById(R.id.idDuplicationCheck);
        check_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String login_id = join_id.getText().toString();
                if (validate) {
                    return; //중복체크 검증 완료
                }

                // 아이디를 입력하지 않았을 경우
                if (login_id.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);

                            if(success) { // 아이디 중복X
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                join_id.setEnabled(false); //아이디값 고정
                                validate = true; //검증 완료
                                check_button.setBackgroundColor(getResources().getColor(R.color.purple_200));
                            }
                            else { // 아이디 중복O
                                dialog = builder.setMessage("이미 존재하는 아이디입니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //서버로 Volley를 이용해서 아이디 중복 체크 요청
                ValidateRequest validateRequest = new ValidateRequest(login_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });

        //회원가입(완료) 버튼 클릭 시 수행
        join_button = findViewById( R.id.registerButton );
        join_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String login_id = join_id.getText().toString();
                final String login_pwd = join_password.getText().toString();
                final String user_name = join_name.getText().toString();
                final String user_phone = join_phone.getText().toString();
                final String PassCk = join_pwck.getText().toString();

                //한 칸이라도 입력 안했을 경우
                if (login_id.equals("") || login_pwd.equals("") || user_name.equals("") || user_phone.equals("") || PassCk.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if(login_pwd.equals(PassCk)) { // 비밀번호와 비밀번호 확인 일치
                                if (success) { // 회원등록에 성공한 경우
                                    Toast.makeText(getApplicationContext(),"회원 등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else { // 회원등록에 실패한 경우
                                    Toast.makeText(getApplicationContext(),"회원 등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } else { // 비밀번호와 비밀번호 확인 불일치
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("비밀번호가 동일하지 않습니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                                return;
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 Volley를 이용해서 회원가입 요청
                RegisterRequest registerRequest = new RegisterRequest( login_id, login_pwd,user_name, user_phone, responseListener);
                RequestQueue queue = Volley.newRequestQueue( RegisterActivity.this );
                queue.add( registerRequest );
            }
        });


    }


}