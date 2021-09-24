package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GetPostDetail extends AppCompatActivity {

    private TextView tv_posterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_post_detail);

        tv_posterId = findViewById(R.id.posterId);

        Intent intent = getIntent();
        String posterId = intent.getStringExtra("posterId");

        tv_posterId.setText(posterId);

        tv_posterId.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( GetPostDetail.this, MembersPageActivity.class );
                intent.putExtra("posterId",posterId);
                startActivity( intent );
            }
        });

    }
}