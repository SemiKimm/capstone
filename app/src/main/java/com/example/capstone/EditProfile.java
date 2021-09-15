package com.example.capstone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class EditProfile extends AppCompatActivity {

    private TextView ed_name, ed_id, ed_pwd;
    private Button editImg_button;
    private ImageView img;
    private Uri urii;

    // 사진 요청 코드
    private static final int REQUEST_CODE=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ed_name = findViewById(R.id.edit_name);
        ed_id = findViewById(R.id.edit_id);
        ed_pwd = findViewById(R.id.edit_pwd);

        Intent intent = getIntent();
        String user_name = intent.getStringExtra("user_name");
        String login_id = intent.getStringExtra("login_id");
        String login_pwd = intent.getStringExtra("login_pwd");
        String profile_img = intent.getStringExtra("profile_img");

        ed_name.setText(user_name);
        ed_id.setText(login_id);
        ed_pwd.setText(login_pwd);

        // img입력 처리
        img=findViewById(R.id.user_profile);

        // 프로필 이미지
        Glide.with(getApplicationContext())
                .load(profile_img)
                .into(img);

        // 갤러리에 요청코드 보내기
        editImg_button = (Button) findViewById(R.id.edit_profileImg);
        editImg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
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
                    urii=uri;
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