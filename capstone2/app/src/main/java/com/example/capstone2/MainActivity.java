package com.example.capstone2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getListCheckBtn=findViewById(R.id.get_list_check_btn);
        getListCheckBtn.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this, GetPostListActivity.class);
            startActivity(intent);
        });

        Button lostListCheckBtn=findViewById(R.id.lost_list_check_btn);
        lostListCheckBtn.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this, LostPostListActivity.class);
            startActivity(intent);
        });

        Button postingButton=findViewById(R.id.postingBtn);
        postingButton.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this, SelectPostingActivity.class);
            startActivity(intent);
        });

        Button searchButton=findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this, SelectSearchPostActivity.class);
            startActivity(intent);
        });


    }
}