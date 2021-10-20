package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
//import com.android.volley.error.VolleyError;
//import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Random;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;


public class GetPostingActivity extends AppCompatActivity {
    private EditText postingTitle, placeData, moreInfo;
    private TextView date, color, category, local;
    private AlertDialog inputErrorDialog;
    private Button imgButton;
    private ImageView img;
    private Uri urii;
    private String uriii;
    private String imgPath;

    private Bitmap bitmap;
    private String image;

    private String GetPostId;

    //사진 요청 코드
    private static final int REQUEST_CODE=0;

    public static String login_id;

    //    String[] colorItems = getResources().getStringArray(R.array.colorSpinnerArray); --이거 오류 왜날까...왜지..? 뭐가 나는거지..?
    String[] colorItems = {"검정색 ","흰색","빨강색","연두색","파랑색 ","노랑색","핑크색","보라색","회색"};
    String[] categoryItems = {"가방", "의류", "전자제품", "악세서리", "모자", "신발", "시계", "휴대폰"};
    String[] localItems = {"선택", "서울특별시", "강원도", "경기도", "경상남도", "경상북도", "광주광역시", "대구광역시"
            , "대전광역시", "부산광역시", "울산광역시", "인천광역시", "전라남도", "전라북도", "충청남도", "충청북도"
            , "제주특별자치도", "세종특별자치시", "기타"};

    //날짜 선택 구현
    DatePickerDialog.OnDateSetListener dateSetListener=
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                    //DatePicker 선택한 날짜를 TextView 에 설정
                    TextView dateView=findViewById(R.id.GetDateData);
                    dateView.setText(String.format("%d/%d/%d",yy,mm+1,dd));
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_posting);

        //동적퍼미션 작업
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int permissionResult= checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult== PackageManager.PERMISSION_DENIED){
                String[] permissions= new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions,10);
            }
        }else{
            //cv.setVisibility(View.VISIBLE);
        }


        /*
        // 저장소 접근 권한 허용
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }

         */

        // 로그인 아이디 찾아주기
        final SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        login_id = sharedPreferences.getString("inputId","");


        Button homeButton=findViewById(R.id.homeBtn);
        homeButton.setOnClickListener(view -> {
            Intent intent=new Intent(GetPostingActivity.this, MainActivity.class);
            startActivity(intent);
        });
        //homeButton.setOnClickListener(view -> finish()); 이건 이전화면으로 돌아가기 기능

        Button postingButton=findViewById(R.id.postingBtn);
        postingButton.setOnClickListener(view -> {
            Intent intent=new Intent(GetPostingActivity.this, SelectPostingActivity.class);
            startActivity(intent);
        });

        //img입력 처리
        img=findViewById(R.id.GetImgData);
        imgButton= findViewById(R.id.imgInputBtn);
        //갤러리에 요청코드 보내기
        /*
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });



        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });


         */

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        //colorSpinner 처리 (색상선택처리)
        Spinner colorSpin = (Spinner)findViewById(R.id.GetColorSpinner);
        color=findViewById(R.id.GetColorData);
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
        Spinner categorySpin = (Spinner)findViewById(R.id.getPostingCategorySpinner);
        category=findViewById(R.id.getPostingCategoryData);
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
        Spinner localSpin = (Spinner)findViewById(R.id.getPostingLocalSpinner);
        local=findViewById(R.id.getPostingLocalData);
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

        //날짜 선택 처리
        Button dateBtn=findViewById(R.id.selectDateBtn);
        dateBtn.setOnClickListener(view -> {
            Calendar calender = Calendar.getInstance();
            new DatePickerDialog(this, dateSetListener, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DATE)).show();});

        //디비 연동
        postingTitle = (EditText) findViewById(R.id.GetPostingTitle);
        placeData = (EditText) findViewById(R.id.GetPlaceData);
        moreInfo = (EditText) findViewById(R.id.GetMoreInfo);
        date = findViewById(R.id.GetDateData);
        color = findViewById(R.id.GetColorData);
        category = findViewById(R.id.getPostingCategoryData);
        local = findViewById(R.id.getPostingLocalData);
        GetPostId = Integer.toString(getRand()); // 게시글id


        Button inputButton=findViewById(R.id.inputBtn);

        inputButton.setOnClickListener(view -> {
            String GetPostTitleData=postingTitle.getText().toString();
            String GetPostPlaceData=placeData.getText().toString();
            String GetPostDateData=date.getText().toString();
            String GetPostColorData=color.getText().toString();
            String GetPostMoreInfoData=moreInfo.getText().toString();

            String GetPostImgData=urii.toString();
            //String GetPostImgData = imgPath;
            //String GetPostImgData = image;


            String GetPostCategoryData=category.getText().toString();
            String GetPostLocalData=local.getText().toString();
            String GetPostUserIdData= login_id; // 로그인 id 데이터
            String GetPostIdData = GetPostId; // 게시물 id
            Log.e("Test", String.valueOf(urii));
            //한 칸이라도 입력 안했을 경우
            if (GetPostTitleData.equals("") || GetPostPlaceData.equals("") || GetPostImgData.equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GetPostingActivity.this);
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
                            Intent intent = new Intent(GetPostingActivity.this,GetPostListActivity.class);
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


            /*

            String serverUrl = "http://myapp.dothome.co.kr/GetPosting.php";

            //파일 전송 요청 객체 생성[결과를 String으로 받음]
            SimpleMultiPartRequest smpr= new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //new AlertDialog.Builder(GetPostingActivity.this).setMessage("응답:"+response).create().show();
                    try {
                        JSONObject JsonObject = new JSONObject(response);
                        boolean success=JsonObject.getBoolean("success");
                        if(success){
                            Toast.makeText(getApplicationContext(),"게시글 등록 성공",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(GetPostingActivity.this,GetPostListActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),"게시글 등록 실패",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(GetPostingActivity.this, "통신오류", Toast.LENGTH_SHORT).show();
                }
            });

            smpr.addStringParam("GetPostIdData", GetPostIdData);
            smpr.addStringParam("GetPostTitleData", GetPostTitleData);
            smpr.addStringParam("GetPostPlaceData", GetPostPlaceData);
            smpr.addStringParam("GetPostDateData", GetPostDateData);
            smpr.addStringParam("GetPostMoreInfoData", GetPostMoreInfoData);
            smpr.addStringParam("GetPostColorData",GetPostColorData);
            smpr.addFile("GetPostImgData", imgPath);
            smpr.addStringParam("GetPostCategoryData",GetPostCategoryData);
            smpr.addStringParam("GetPostLocalData",GetPostLocalData);
            smpr.addStringParam("GetPostUserIdData",GetPostUserIdData);


             */


            //서버로 Volley 이용해서 요청
            GetPostingRequest getPostingRequest = new GetPostingRequest(  GetPostIdData, GetPostTitleData,  GetPostPlaceData
                    ,  GetPostDateData,  GetPostMoreInfoData, GetPostColorData, GetPostImgData
                    , GetPostCategoryData,GetPostLocalData, GetPostUserIdData, responseListener);


            RequestQueue queue = Volley.newRequestQueue( GetPostingActivity.this );
            queue.add(getPostingRequest);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 10 :
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED) //사용자가 허가 했다면
                {
                    Toast.makeText(this, "외부 메모리 읽기/쓰기 사용 가능", Toast.LENGTH_SHORT).show();

                }else{//거부했다면
                    Toast.makeText(this, "외부 메모리 읽기/쓰기 제한", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }


    //갤러리에서 이미지 넣기
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            if(resultCode==RESULT_OK){
                try{
                    Uri uri=data.getData();
                    urii=uri;

                    imgPath = getPathFromUri(uri);

                    Log.e("realPath", imgPath);





                    //Log.e("uri", uri.toString());
                    //uriii = uri.toString();

                    //다이얼로그 이미지 사진에 넣기
                    Glide.with(getApplicationContext())
                            .load(uri)
                            .into(img);

                    // glide, setImageBitmap 둘다 됨
                    //img.setImageBitmap(bitmap); // bitmap으로 바꿔도 이미지 잘 보인다 ..

                }catch(Exception e){

                }
            }else if(resultCode == RESULT_CANCELED){
                //취소시 호출할 행동
            }
        }
    }

    // uri to realPath
    public String getPathFromUri(Uri uri){

        Cursor cursor = getContentResolver().query(uri, null, null, null, null );

        cursor.moveToNext();

        String path = cursor.getString( cursor.getColumnIndex( "_data" ) );

        cursor.close();

        return path;
    }





/*
    //갤러리에서 이미지 넣기
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            if(resultCode==RESULT_OK){
                int takeFlags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                ContentResolver resolver = this.getContentResolver();
                try{

                    Uri uri=data.getData();
                    resolver.takePersistableUriPermission(uri, takeFlags);
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


 */


    // 게시물 id 8자리 난수
    public static int getRand(){
        Random rand = new Random();
        String rst = Integer.toString(rand.nextInt(8)+1);
        for(int i=0; i<7; i++){
            rst += Integer.toString(rand.nextInt(9));
        }
        return Integer.parseInt(rst);
    }


}