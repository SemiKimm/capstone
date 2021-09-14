package com.example.capstone2;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class SearchGetPostActivity extends AppCompatActivity {
    private TextView date, color, category;

    String[] colorItems = {"검정색 ","흰색","빨강색","연두색","파랑색 ","노랑색","핑크색","보라색","회색"};
    String[] categoryItems = {"가방", "의류", "전자제품", "악세서리", "모자", "신발", "시계", "휴대폰"};

    //날짜 선택 구현
    DatePickerDialog.OnDateSetListener dateSetListener1=
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                    //DatePicker 선택한 날짜를 TextView 에 설정
                    TextView dateView=findViewById(R.id.searchGetDateData1);
                    dateView.setText(String.format("%d/%d/%d",yy,mm+1,dd));
                }
            };

    DatePickerDialog.OnDateSetListener dateSetListener2=
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                    //DatePicker 선택한 날짜를 TextView 에 설정
                    TextView dateView=findViewById(R.id.searchGetDateData2);
                    dateView.setText(String.format("%d/%d/%d",yy,mm+1,dd));
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_get_post);

        Button homeButton=findViewById(R.id.homeBtn);
        homeButton.setOnClickListener(view -> {
            Intent intent=new Intent(SearchGetPostActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Button searchButton=findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(view -> {
            Intent intent=new Intent(SearchGetPostActivity.this, SelectSearchPostActivity.class);
            startActivity(intent);
        });

        Button postingButton=findViewById(R.id.postingBtn);
        postingButton.setOnClickListener(view -> {
            Intent intent=new Intent(SearchGetPostActivity.this, SelectPostingActivity.class);
            startActivity(intent);
        });


        //colorSpinner 처리 (색상선택처리)
        Spinner colorSpin = (Spinner)findViewById(R.id.searchGetColorSpinner);
        color=findViewById(R.id.searchGetColorData);
        ArrayAdapter<String> colorAdapter=new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item,colorItems
        );
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        colorSpin.setAdapter(colorAdapter);
        colorSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override//선택되면
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                color.setText(colorItems[position]);
            }
            @Override//아무것도 선택 안되면
            public void onNothingSelected(AdapterView<?> parent) {
                color.setText("색상선택");
            }
        });

        //categorySpinner 처리 (색상선택처리)
        Spinner categorySpin = (Spinner)findViewById(R.id.searchGetCategorySpinner);
        category=findViewById(R.id.searchGetCategoryData);
        ArrayAdapter<String> categoryAdapter=new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item,categoryItems
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        categorySpin.setAdapter(categoryAdapter);
        categorySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override//선택되면
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                category.setText(categoryItems[position]);
            }
            @Override//아무것도 선택 안되면
            public void onNothingSelected(AdapterView<?> parent) {
                category.setText("물건 분류 선택");
            }
        });

        //날짜 선택 구현
        Button dateBtn1=findViewById(R.id.searchSelectDateBtn1);
        dateBtn1.setOnClickListener(view -> {
            Calendar calender = Calendar.getInstance();
            new DatePickerDialog(this, dateSetListener1, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DATE)).show();});

        Button dateBtn2=findViewById(R.id.searchSelectDateBtn2);
        dateBtn2.setOnClickListener(view -> {
            Calendar calender = Calendar.getInstance();
            new DatePickerDialog(this, dateSetListener2, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DATE)).show();});

    }
}