package com.example.capstone2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class GetPostActivity extends AppCompatActivity {

    private TextView tv_posterId;
    private Button report_getpost_btn;
    public static String login_id;

    private TextView postTitle,postCategory,postLocal,postPlace, postColor , postDate, postMoreInfo;
    ImageView img;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_post);

        Button homeButton=findViewById(R.id.homeBtn);
        homeButton.setOnClickListener(view -> {
            Intent intent=new Intent(GetPostActivity.this, MainActivity.class);
            startActivity(intent);
        });



        final SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        login_id = sharedPreferences.getString("inputId","");
        tv_posterId = findViewById(R.id.posterId); // 작성자 id

        postTitle=findViewById(R.id.postTitle);
        postCategory=findViewById(R.id.postCategory);
        postLocal=findViewById(R.id.postLocal);
        postPlace=findViewById(R.id.postPlace);
        postColor=findViewById(R.id.postColor);
        postDate=findViewById(R.id.postDate);
        postMoreInfo=findViewById(R.id.postMoreInfo);

        Intent intent = getIntent();
        String posterId = intent.getStringExtra("posterId"); // 게시물 작성자 id
        String postId = intent.getStringExtra("postId"); // 게시물id
        String postTitleData=intent.getExtras().getString("title");
        String postCategoryData=intent.getExtras().getString("category");
        String postLocalData=intent.getExtras().getString("local");
        String postPlaceData=intent.getExtras().getString("place");
        String postColorData=intent.getExtras().getString("color");
        String postDateData=intent.getExtras().getString("date");
        String postMoreInfoData=intent.getExtras().getString("moreInfo");
        String postImgUriData=intent.getExtras().getString("imgUri");

        tv_posterId.setText(posterId); // 작성자 id
        tv_posterId.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(login_id.equals(posterId)){ // 본인이 작성한 글이면 본인 mypage로 이동
                    Intent intent = new Intent( GetPostActivity.this, MypageActivity.class );
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(GetPostActivity.this, MembersPageActivity.class);
                    intent.putExtra("posterId", posterId);
                    startActivity(intent);
                }
            }
        });

        postTitle.setText(postTitleData);
        postCategory.setText(postCategoryData);
        postLocal.setText(postLocalData);
        postPlace.setText(postPlaceData);
        postColor.setText(postColorData);
        postDate.setText(postDateData);
        postMoreInfo.setText(postMoreInfoData);
        Log.e("dataCheck",String.valueOf(postTitleData));

        Button chatButton = findViewById(R.id.postChatBtn);
        chatButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( GetPostActivity.this, MessageActivity.class );
                intent.putExtra("posterId", posterId); // 게시물 작성자 id
                intent.putExtra("postId", postId); // 게시물 id
                startActivity( intent );
            }
        });

        img=findViewById(R.id.postImg);
        uri= Uri.parse(postImgUriData);
        Log.e("imgdata",postImgUriData);
        Glide.with(this).load(postImgUriData).override(200,200)
                .error(R.drawable.defaultimg).into(img);

        report_getpost_btn = findViewById(R.id.report_getpost);

        // 본인이 작성한 게시물 신고 막기
        if(login_id.equals(posterId)){
            report_getpost_btn.setVisibility(View.GONE);
        }
        report_getpost_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( GetPostActivity.this, ReportPostActivity.class );
                intent.putExtra("posterId", posterId); // 게시물 작성자 id
                intent.putExtra("postId", postId); // 게시물 id
                intent.putExtra("postTitle", postTitleData); // 게시물 제목
                startActivity( intent );
            }
        });
    }
}