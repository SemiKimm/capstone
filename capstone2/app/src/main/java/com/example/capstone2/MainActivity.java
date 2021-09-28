package com.example.capstone2;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    String[] localItems = {"지역 선택", "서울특별시", "강원도", "경기도", "경상남도", "경상북도", "광주광역시", "대구광역시"
            , "대전광역시", "부산광역시", "울산광역시", "인천광역시", "전라남도", "전라북도", "충청남도", "충청북도"
            , "제주특별자치도", "세종특별자치시", "기타"};
    String selectedLocal;
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

        //localSpinner 처리 (물건분류선택처리)
        Spinner localSpin = (Spinner)findViewById(R.id.selectLocalSpinner);
        //local=findViewById(R.id.searchGetLocalData);
        ArrayAdapter<String> localAdapter=new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item,localItems
        );
        localAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        localSpin.setAdapter(localAdapter);
        localSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override//선택되면
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedLocal=localItems[position];
            }
            @Override//아무것도 선택 안되면
            public void onNothingSelected(AdapterView<?> parent) {
                selectedLocal ="지역 선택";
            }
        });


    }
}