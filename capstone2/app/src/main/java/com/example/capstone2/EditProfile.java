package com.example.capstone2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class EditProfile extends AppCompatActivity {

    private TextView ed_name, ed_id, ed_pwd;
    private Button editImg_button, editConfirm_button, withdrawal_button;
    private ImageView img;
    //private Uri urii;
    private String profile_img;
    String profile_img_lo;

    private AlertDialog dialog;

    // 사진 요청 코드
    private static final int REQUEST_CODE=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        final SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);


        ed_name = findViewById(R.id.edit_name);
        ed_id = findViewById(R.id.edit_id);
        ed_pwd = findViewById(R.id.edit_pwd);

        Intent intent = getIntent();
        String user_name = intent.getStringExtra("user_name");
        String login_id = intent.getStringExtra("login_id");
        String login_pwd = intent.getStringExtra("login_pwd");
        profile_img = intent.getStringExtra("profile_img");

        ed_name.setText(user_name);
        ed_id.setText(login_id);
        ed_pwd.setText(login_pwd);

        // img입력 처리
        img=findViewById(R.id.user_profile);

        // 프로필 이미지 load
        Glide.with(getApplicationContext())
                .load(profile_img)
                .into(img);

        // 프로필 이미지 수정 버튼 클릭 시
        editImg_button = (Button) findViewById(R.id.edit_profileImg);
        // 갤러리에 요청코드 보내기
        editImg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


        // 수정 완료 버튼 클릭 시
        editConfirm_button = (Button) findViewById(R.id.edit_confirm);
        editConfirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = ed_id.getText().toString();
                final String pwd = ed_pwd.getText().toString();
                final String imgLocation = profile_img; // 이미지 경로

                //비밀번호 입력 안했을 경우
                if (pwd.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
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

                            if (success) { // 프로필 수정에 성공한 경우
                                Toast.makeText(getApplicationContext(),"프로필 수정을 완료하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditProfile.this, MypageActivity.class);
                                startActivity(intent);
                            } else { // 프로필 수정에 실패한 경우
                                Toast.makeText(getApplicationContext(),"프로필 수정을 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 Volley를 이용해서 프로필 수정 요청
                ProfileEditRequest profileEditRequest = new ProfileEditRequest( id, pwd, imgLocation, responseListener);
                RequestQueue queue = Volley.newRequestQueue( EditProfile.this );
                queue.add( profileEditRequest );
            }
        });

        // 회원탈퇴 버튼 클릭 시 수행
        withdrawal_button = (Button) findViewById(R.id.withdrawal);
        withdrawal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = ed_id.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) { // 회원탈퇴에 성공한 경우
                                Toast.makeText(getApplicationContext(),"회원탈퇴를 완료 하였습니다.",Toast.LENGTH_SHORT).show();

                                // sharedpreference에 저장된 정보 삭제
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("inputId");
                                editor.remove("inputPwd");
                                editor.putString(getResources().getString(R.string.prefAutoLoginState),"non-autoLogin");
                                editor.putString(getResources().getString(R.string.prefLoginState),"loggedout");
                                editor.apply();
                                editor.commit();

                                Intent intent = new Intent(EditProfile.this, MainActivity.class);
                                startActivity(intent);
                            } else { // 회원탈퇴에 실패한 경우
                                Toast.makeText(getApplicationContext(),"회원탈퇴를 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };


                //서버로 Volley를 이용해서 회원탈퇴 요청
                DeleteUser deleteUser = new DeleteUser( id, responseListener);
                RequestQueue queue = Volley.newRequestQueue( EditProfile.this );
                queue.add( deleteUser );
            }

        });



    }

    //갤러리에서 이미지 넣기
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            if(resultCode==RESULT_OK){
                try{
                    Uri uri=data.getData();
                    profile_img = uri.toString(); // uri string으로 변환

                    //다이얼로그 이미지 사진에 넣기
                    Glide.with(getApplicationContext()).load(uri).into(img);
                }catch(Exception e){

                }
            }else if(resultCode == RESULT_CANCELED){
                //취소시 호출할 행동
            }
        }
    }

}