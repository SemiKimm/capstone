package com.example.capstone2;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;


public class PostingActivity extends AppCompatActivity {
    private EditText postingTitle, placeData, moreInfo;
    private TextView date, color;
    private AlertDialog inputErrorDialog;
//    String[] colorItems = getResources().getStringArray(R.array.colorSpinnerArray); --이거 오류 왜날까...왜지..? 뭐가 나는거지..?
    String[] colorItems = {"검정색 ","흰색","빨강색","연두색","파랑색 ","노랑색","핑크색","보라색","회색"};
    DatePickerDialog.OnDateSetListener dateSetListener=
        new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                //DatePicker 선택한 날짜를 TextView 에 설정
                TextView dateView=findViewById(R.id.dateData);
                dateView.setText(String.format("%d/%d/%d",yy,mm+1,dd));
            }
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posting);

        Button homeButton=findViewById(R.id.homeBtn);
        homeButton.setOnClickListener(view -> finish());

        //colorSpinner 처리 (색상선택처리)
        Spinner colorSpin = (Spinner)findViewById(R.id.colorSpinner);
        color=findViewById(R.id.colorData);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item,colorItems
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        colorSpin.setAdapter(adapter);
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

        Button dateBtn=findViewById(R.id.selectDateBtn);
        dateBtn.setOnClickListener(view -> {
            Calendar calender = Calendar.getInstance();
            new DatePickerDialog(this, dateSetListener, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DATE)).show();});

        postingTitle = (EditText) findViewById(R.id.postingTitle);
        placeData = (EditText) findViewById(R.id.placeData);
        moreInfo = (EditText) findViewById(R.id.moreInfo);
        date = findViewById(R.id.dateData);
        color = findViewById(R.id.colorData);

        Button inputButton=findViewById(R.id.inputBtn);

        inputButton.setOnClickListener(view -> {
            String PostTitleData=postingTitle.getText().toString();
            String PostPlaceData=placeData.getText().toString();
            String PostDateData=date.getText().toString();
            String PostColorData=color.getText().toString();
            String PostMoreInfoData=moreInfo.getText().toString();
            //한 칸이라도 입력 안했을 경우
            if (PostTitleData.equals("") || PostPlaceData.equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PostingActivity.this);
                inputErrorDialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create();
                inputErrorDialog.show();
                return;
            }

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("test","test: 공백");
                    try {
                        JSONObject JsonObject = new JSONObject(response);
                        boolean success=JsonObject.getBoolean("success");
                        if(success){
                            Toast.makeText(getApplicationContext(),"게시글 등록 성공",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PostingActivity.this,PostListActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),"게시글 등록 실패",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            //서버로 Volley 이용해서 요청
            PostingRequest postingRequest = new PostingRequest(  PostTitleData,  PostPlaceData
                    ,  PostDateData,  PostMoreInfoData, PostColorData, responseListener);
            RequestQueue queue = Volley.newRequestQueue( PostingActivity.this );
            queue.add( postingRequest );
        });
    }


}