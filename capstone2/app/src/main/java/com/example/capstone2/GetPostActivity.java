package com.example.capstone2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URI;

public class GetPostActivity extends AppCompatActivity {
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

        postTitle=findViewById(R.id.postTitle);
        postCategory=findViewById(R.id.postCategory);
        postLocal=findViewById(R.id.postLocal);
        postPlace=findViewById(R.id.postPlace);
        postColor=findViewById(R.id.postColor);
        postDate=findViewById(R.id.postDate);
        postMoreInfo=findViewById(R.id.postMoreInfo);

        Intent intent = getIntent();
        String postTitleData=intent.getExtras().getString("title");
        String postCategoryData=intent.getExtras().getString("category");
        String postLocalData=intent.getExtras().getString("local");
        String postPlaceData=intent.getExtras().getString("place");
        String postColorData=intent.getExtras().getString("color");
        String postDateData=intent.getExtras().getString("date");
        String postMoreInfoData=intent.getExtras().getString("moreInfo");
        String postImgUriData=intent.getExtras().getString("imgUri");

        postTitle.setText(postTitleData);
        postCategory.setText(postCategoryData);
        postLocal.setText(postLocalData);
        postPlace.setText(postPlaceData);
        postColor.setText(postColorData);
        postDate.setText(postDateData);
        postMoreInfo.setText(postMoreInfoData);

        img=findViewById(R.id.postImg);
        uri= Uri.parse(postImgUriData);
        Log.e("imgdata",postImgUriData);
        Glide.with(this).load(postImgUriData).override(200,200)
                .error(R.drawable.defaultimg).into(img);
    }
}