package com.example.capstone;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;


public class PostingActivity extends AppCompatActivity {
    private EditText postingTitle, placeData, moreInfo;
    private TextView date, color;
    private AlertDialog inputErrorDialog;
    private Button imgButton;
    private ImageView img;
    private Uri urii;
    //사진 요청 코드
    private static final int REQUEST_CODE=0;

    //    String[] colorItems = getResources().getStringArray(R.array.colorSpinnerArray); --이거 오류 왜날까...왜지..? 뭐가 나는거지..?
    String[] colorItems = {"검정색 ","흰색","빨강색","연두색","파랑색 ","노랑색","핑크색","보라색","회색"};

    //날짜 선택 구현
    DatePickerDialog.OnDateSetListener dateSetListener=
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                    //DatePicker 선택한 날짜를 TextView 에 설정
                    TextView dateView=findViewById(R.id.LostDateData);
                    dateView.setText(String.format("%d/%d/%d",yy,mm+1,dd));
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);

        Button homeButton=findViewById(R.id.homeBtn);
        homeButton.setOnClickListener(view -> finish());

        //img입력 처리
        img=findViewById(R.id.LostImgData);
        imgButton= findViewById(R.id.imgInputBtn);
        //갤러리에 요청코드 보내기
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        //colorSpinner 처리 (색상선택처리)
        Spinner colorSpin = (Spinner)findViewById(R.id.LostColorSpinner);
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

        postingTitle = (EditText) findViewById(R.id.LostPostingTitle);
        placeData = (EditText) findViewById(R.id.LostPlaceData);
        moreInfo = (EditText) findViewById(R.id.LostMoreInfo);
        date = findViewById(R.id.LostDateData);
        color = findViewById(R.id.colorData);

        Button inputButton=findViewById(R.id.inputBtn);

        inputButton.setOnClickListener(view -> {
            String PostTitleData=postingTitle.getText().toString();
            String PostPlaceData=placeData.getText().toString();
            String PostDateData=date.getText().toString();
            String PostColorData=color.getText().toString();
            String PostMoreInfoData=moreInfo.getText().toString();
            String PostImgData=urii.toString();
            Log.e("Test", String.valueOf(urii));
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
                    ,  PostDateData,  PostMoreInfoData, PostColorData, PostImgData, responseListener);
            RequestQueue queue = Volley.newRequestQueue( PostingActivity.this );
            queue.add( postingRequest );
        });
    }

    //갤러리에서 이미지 넣기
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            if(resultCode==RESULT_OK){
                try{
                    Uri uri=data.getData();
                    urii=uri;
                    //다이얼로그 이미지 사진에 넣기
                    Glide.with(getApplicationContext()).load(uri).into(img);
                }catch(Exception e){

                }
            }else if(resultCode == RESULT_CANCELED){
                //취소시 호출할 행동
            }
        }
    }
}