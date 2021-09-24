package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class SelectPostingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_posting);

        Button homeButton=findViewById(R.id.homeBtn);
        homeButton.setOnClickListener(view -> {
            Intent intent=new Intent(SelectPostingActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Button selectLostBtn=findViewById(R.id.select_lost_btn);
        selectLostBtn.setOnClickListener(view -> {
            Intent intent=new Intent(SelectPostingActivity.this, LostPostingActivity.class);
            startActivity(intent);
        });

        Button selectGetBtn=findViewById(R.id.select_get_btn);
        selectGetBtn.setOnClickListener(view -> {
            Intent intent=new Intent(SelectPostingActivity.this, GetPostingActivity.class);
            startActivity(intent);
        });

        Button postingButton=findViewById(R.id.postingBtn);
        postingButton.setOnClickListener(view -> {
            Intent intent=new Intent(SelectPostingActivity.this, SelectPostingActivity.class);
            startActivity(intent);
        });
    }
}