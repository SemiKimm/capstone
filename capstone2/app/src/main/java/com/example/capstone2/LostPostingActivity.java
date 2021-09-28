package com.example.capstone2;
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


public class LostPostingActivity extends AppCompatActivity {
    private EditText postingTitle, placeData, moreInfo;
    private TextView date, color, category, local;
    private AlertDialog inputErrorDialog;
    private Button imgButton;
    private ImageView img;
    private Uri urii;
    //사진 요청 코드
    private static final int REQUEST_CODE=0;

    //    String[] colorItems = getResources().getStringArray(R.array.colorSpinnerArray); --이거 오류 왜날까...왜지..? 뭐가 나는거지..?
    String[] colorItems = {"선택","검정색","흰색","빨강색","연두색","파랑색 ","노랑색","핑크색","보라색","회색","갈색"};
    String[] categoryItems = {"선택","가방", "의류", "전자제품", "악세서리", "모자", "신발", "시계", "휴대폰"};
    String[] localItems = {"선택", "서울특별시", "강원도", "경기도", "경상남도", "경상북도", "광주광역시", "대구광역시"
            , "대전광역시", "부산광역시", "울산광역시", "인천광역시", "전라남도", "전라북도", "충청남도", "충청북도"
            , "제주특별자치도", "세종특별자치시", "기타"};

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
        setContentView(R.layout.lost_posting);

        Button homeButton=findViewById(R.id.homeBtn);
        homeButton.setOnClickListener(view -> {
            Intent intent=new Intent(LostPostingActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Button postingButton=findViewById(R.id.postingBtn);
        postingButton.setOnClickListener(view -> {
            Intent intent=new Intent(LostPostingActivity.this, SelectPostingActivity.class);
            startActivity(intent);
        });

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

        //categorySpinner 처리 (물건분류선택처리)
        Spinner categorySpin = (Spinner)findViewById(R.id.lostPostingCategorySpinner);
        category=findViewById(R.id.lostPostingCategoryData);
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

        //localSpinner 처리 (물건분류선택처리)
        Spinner localSpin = (Spinner)findViewById(R.id.lostPostingLocalSpinner);
        local=findViewById(R.id.lostPostingLocalData);
        ArrayAdapter<String> localAdapter=new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item,localItems
        );
        localAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        localSpin.setAdapter(localAdapter);
        localSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override//선택되면
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                local.setText(localItems[position]);
            }
            @Override//아무것도 선택 안되면
            public void onNothingSelected(AdapterView<?> parent) {
                local.setText("물건 분류 선택");
            }
        });

        //날짜선택 처리
        Button dateBtn=findViewById(R.id.selectDateBtn);
        dateBtn.setOnClickListener(view -> {
            Calendar calender = Calendar.getInstance();
            new DatePickerDialog(this, dateSetListener, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DATE)).show();});

        //디비 연동
        postingTitle = (EditText) findViewById(R.id.LostPostingTitle);
        placeData = (EditText) findViewById(R.id.LostPlaceData);
        moreInfo = (EditText) findViewById(R.id.LostMoreInfo);
        date = findViewById(R.id.LostDateData);
        color = findViewById(R.id.colorData);
        category = findViewById(R.id.lostPostingCategoryData);
        local = findViewById(R.id.lostPostingLocalData);

        Button inputButton=findViewById(R.id.inputBtn);

        inputButton.setOnClickListener(view -> {
            String LostPostTitleData=postingTitle.getText().toString();
            String LostPostPlaceData=placeData.getText().toString();
            String LostPostDateData=date.getText().toString();
            String LostPostColorData=color.getText().toString();
            String LostPostMoreInfoData=moreInfo.getText().toString();
            String LostPostImgData=urii.toString();
            String LostPostCategoryData=category.getText().toString();
            String LostPostLocalData=local.getText().toString();
            Log.e("Test", String.valueOf(urii));
            //한 칸이라도 입력 안했을 경우
            if (LostPostTitleData.equals("") || LostPostPlaceData.equals("") || LostPostImgData.equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LostPostingActivity.this);
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
                            Intent intent = new Intent(LostPostingActivity.this,LostPostListActivity.class);
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
            LostPostingRequest lostPostingRequest = new LostPostingRequest(  LostPostTitleData, LostPostCategoryData, LostPostLocalData,  LostPostPlaceData
                    ,  LostPostDateData,  LostPostMoreInfoData, LostPostColorData, LostPostImgData, responseListener);
            RequestQueue queue = Volley.newRequestQueue( LostPostingActivity.this );
            queue.add( lostPostingRequest );
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