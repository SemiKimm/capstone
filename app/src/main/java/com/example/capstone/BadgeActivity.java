package com.example.capstone;

import android.app.Activity;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class BadgeActivity extends Activity {

    ImageView badge1, badge2, badge3, badge4, badge5, badge6, badge7, badge8, badge9, badge10;
    ImageView badge1_1, badge2_2, badge3_3, badge4_4, badge5_5, badge6_6, badge7_7, badge8_8, badge9_9, badge10_10;
    //Button buttonClick;
    private int count=0;
    //private int ipostCount=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge);
        //buttonClick=findViewById(R.id.buttonClick);

        Intent intent = getIntent();
        //String postCount = intent.getStringExtra("postcount");
        //int ipostCount = Integer.parseInt(postCount);
        int ipostCount = intent.getIntExtra("postcount",0);

        badge1=findViewById(R.id.badge1);
        //badge1.setImageResource(R.drawable.gugu);

        badge2=findViewById(R.id.badge2);
        //badge2.setImageResource(R.drawable.haha);

        badge3=findViewById(R.id.badge3);
        //badge3.setImageResource(R.drawable.hoho);

        badge4=findViewById(R.id.badge4);
        badge5=findViewById(R.id.badge5);
        badge6=findViewById(R.id.badge6);
        badge7=findViewById(R.id.badge7);
        badge8=findViewById(R.id.badge8);
        badge9=findViewById(R.id.badge9);
        badge10=findViewById(R.id.badge10);

        badge1_1 = findViewById(R.id.badge1_1);
        badge2_2 = findViewById(R.id.badge2_2);
        badge3_3 = findViewById(R.id.badge3_3);
        badge4_4 = findViewById(R.id.badge4_4);
        badge5_5 = findViewById(R.id.badge5_5);
        badge6_6 = findViewById(R.id.badge6_6);
        badge7_7 = findViewById(R.id.badge7_7);
        badge8_8 = findViewById(R.id.badge8_8);
        badge9_9 = findViewById(R.id.badge9_9);
        badge10_10 = findViewById(R.id.badge10_10);



        //----컬러 필터를 흑백으로 변경------
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter=new ColorMatrixColorFilter(matrix);

        badge1.setColorFilter(filter);
        badge2.setColorFilter(filter);
        badge3.setColorFilter(filter);
        badge4.setColorFilter(filter);
        badge5.setColorFilter(filter);
        badge6.setColorFilter(filter);
        badge7.setColorFilter(filter);
        badge8.setColorFilter(filter);
        badge9.setColorFilter(filter);
        badge10.setColorFilter(filter);

        if(ipostCount>=3){
            badge1.setColorFilter(null);
            badge1_1.setVisibility(View.GONE);
        }


    }


    //------팝업 창을 띄우기 위해서
    public void badgeclick(View v){
        //데이터 담아서 팝업(액티비티) 호출
        Intent intent = new Intent(this, PopupActivity.class);
        if(v.getId()==R.id.badge1){
            intent.putExtra("data", "게시글 10회 작성 시 획득");
        } else if(v.getId()==R.id.badge2){
            intent.putExtra("data", "댓글 10회 작성 시 획득");
        } else if(v.getId()==R.id.badge3){
            intent.putExtra("data", "물건 10회 찾아주기 성공 시 획득");
        }
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                //데이터 받기
                String result = data.getStringExtra("result");
            }
        }
    }

}