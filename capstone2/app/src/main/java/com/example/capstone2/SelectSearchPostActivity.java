package com.example.capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class SelectSearchPostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_search_post);

        Button homeButton=findViewById(R.id.homeBtn);
        homeButton.setOnClickListener(view -> {
            Intent intent=new Intent(SelectSearchPostActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Button selectLostBtn=findViewById(R.id.select_lost_btn);
        selectLostBtn.setOnClickListener(view -> {
            Intent intent=new Intent(SelectSearchPostActivity.this, SearchLostPostActivity.class);
            startActivity(intent);
        });

        Button selectGetBtn=findViewById(R.id.select_get_btn);
        selectGetBtn.setOnClickListener(view -> {
            Intent intent=new Intent(SelectSearchPostActivity.this, SearchGetPostActivity.class);
            startActivity(intent);
        });

        Button postingButton=findViewById(R.id.postingBtn);
        postingButton.setOnClickListener(view -> {
            Intent intent=new Intent(SelectSearchPostActivity.this, SelectPostingActivity.class);
            startActivity(intent);
        });
    }
}