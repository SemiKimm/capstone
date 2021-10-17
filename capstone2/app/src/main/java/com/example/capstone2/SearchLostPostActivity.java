package com.example.capstone2;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class SearchLostPostActivity extends AppCompatActivity {
    private TextView date, color, category, local;

    String[] colorItems = {"선택", "검정색 ","흰색","빨강색","연두색","파랑색 ","노랑색","핑크색","보라색","회색", "갈색"};
    String[] categoryItems = {"선택", "가방", "의류", "전자제품", "악세서리", "모자", "신발", "시계", "휴대폰"};
    String[] localItems = {"선택", "서울특별시", "강원도", "경기도", "경상남도", "경상북도", "광주광역시", "대구광역시"
            , "대전광역시", "부산광역시", "울산광역시", "인천광역시", "전라남도", "전라북도", "충청남도", "충청북도"
            , "제주특별자치도", "세종특별자치시", "기타"};

    private static final String TAG_LostPostTitle = "LostPostTitleData";
    private static final String TAG_LostPostCategory = "LostPostCategoryData";
    private static final String TAG_LostPostLocal ="LostPostLocalData";
    private static final String TAG_LostPostPlace = "LostPostPlaceData";
    private static final String TAG_LostPostDate ="LostPostDateData";
    private static final String TAG_LostPostColor ="LostPostColorData";
    private static final String TAG_LostPostMoreInfo ="LostPostMoreInfoData";
    private static final String TAG_LostPostImg ="LostPostImgData";

    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;

    //날짜 선택 구현
    DatePickerDialog.OnDateSetListener dateSetListener1=
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                    //DatePicker 선택한 날짜를 TextView 에 설정
                    TextView dateView=findViewById(R.id.searchLostDateData1);
                    dateView.setText(String.format("%d/%d/%d",yy,mm+1,dd));
                }
            };

    DatePickerDialog.OnDateSetListener dateSetListener2=
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                    //DatePicker 선택한 날짜를 TextView 에 설정
                    TextView dateView=findViewById(R.id.searchLostDateData2);
                    dateView.setText(String.format("%d/%d/%d",yy,mm+1,dd));
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_lost_post);

        Button homeButton=findViewById(R.id.homeBtn);
        homeButton.setOnClickListener(view -> {
            Intent intent=new Intent(SearchLostPostActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Button searchButton=findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(view -> {
            Intent intent=new Intent(SearchLostPostActivity.this, SelectSearchPostActivity.class);
            startActivity(intent);
        });

        Button postingButton=findViewById(R.id.postingBtn);
        postingButton.setOnClickListener(view -> {
            Intent intent=new Intent(SearchLostPostActivity.this, SelectPostingActivity.class);
            startActivity(intent);
        });

        //colorSpinner 처리 (색상선택처리)
        Spinner colorSpin = (Spinner)findViewById(R.id.searchLostColorSpinner);
        color=findViewById(R.id.searchLostColorData);
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
        Spinner categorySpin = (Spinner)findViewById(R.id.searchLostCategorySpinner);
        category=findViewById(R.id.searchLostCategoryData);
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
        Spinner localSpin = (Spinner)findViewById(R.id.searchLostLocalSpinner);
        local=findViewById(R.id.searchLostLocalData);
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

        //날짜 선택 구현
        Button dateBtn1=findViewById(R.id.searchSelectDateBtn1);
        dateBtn1.setOnClickListener(view -> {
            Calendar calender = Calendar.getInstance();
            new DatePickerDialog(this, dateSetListener1, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DATE)).show();});

        Button dateBtn2=findViewById(R.id.searchSelectDateBtn2);
        dateBtn2.setOnClickListener(view -> {
            Calendar calender = Calendar.getInstance();
            new DatePickerDialog(this, dateSetListener2, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DATE)).show();});

        mArrayList = new ArrayList<>();

        //검색 버튼 클릭 이벤트
        Button searchLostBtn=findViewById(R.id.searchLostFunctionBtn);
        searchLostBtn.setOnClickListener(view -> searchLostData());
    }

    public void searchLostData(){
        //검색 조건 데이터 받기
        TextView search_category, search_color, search_local, search_date1, search_date2;
        EditText search_place;
        search_category = findViewById(R.id.searchLostCategoryData);
        search_color = findViewById(R.id.searchLostColorData);
        search_local = findViewById(R.id.searchLostLocalData);
        search_place = findViewById(R.id.searchLostPlaceData);
        search_date1 = findViewById(R.id.searchLostDateData1);
        search_date2 = findViewById(R.id.searchLostDateData2);

        String search_category_data = search_category.getText().toString();
        String search_color_data = search_color.getText().toString();
        String search_local_data = search_local.getText().toString();
        String search_place_data = search_place.getText().toString();
        String search_date1_data = search_date1.getText().toString();
        String search_date2_data = search_date2.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject JsonObject = new JSONObject(response);
                    boolean success=JsonObject.getBoolean("success");
                    JSONArray jsonArray = JsonObject.getJSONArray("lostpostdata");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject item = jsonArray.getJSONObject(i);

                        String LostPostTitleData = item.getString(TAG_LostPostTitle );
                        String LostPostCategoryData = item.getString(TAG_LostPostCategory );
                        String LostPostLocalData = item.getString(TAG_LostPostLocal );
                        String LostPostPlaceData = item.getString(TAG_LostPostPlace );
                        String LostPostDateData = item.getString(TAG_LostPostDate );
                        String LostPostColorData = item.getString(TAG_LostPostColor );
                        String LostPostMoreInfoData = item.getString(TAG_LostPostMoreInfo );
                        String LostPostImgData = item.getString(TAG_LostPostImg );

                        HashMap<String,String> hashMap = new HashMap<>();

                        hashMap.put(TAG_LostPostTitle, LostPostTitleData);
                        hashMap.put(TAG_LostPostCategory, LostPostCategoryData);
                        hashMap.put(TAG_LostPostLocal, LostPostLocalData);
                        hashMap.put(TAG_LostPostPlace, LostPostPlaceData);
                        hashMap.put(TAG_LostPostDate, LostPostDateData);
                        hashMap.put(TAG_LostPostColor, LostPostColorData);
                        hashMap.put(TAG_LostPostMoreInfo, LostPostMoreInfoData);
                        hashMap.put(TAG_LostPostImg, LostPostImgData);
                        mArrayList.add(hashMap);
                        Log.e("test",String.valueOf(mArrayList));
                    }
                    if(success){
                        Toast.makeText(getApplicationContext(),"검색 조회 성공",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SearchLostPostActivity.this,SearchLostPostListActivity.class);
                        intent.putExtra("data",mArrayList);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"검색 조회 실패",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    Log.e("test","test: catch");
                    e.printStackTrace();
                }
            }
        };
        //서버로 Volley 이용해서 요청
        SearchLostPostRequest searchLostPostRequest = new SearchLostPostRequest( search_category_data, search_color_data, search_local_data,  search_place_data
                ,  search_date1_data,  search_date2_data, responseListener);
        RequestQueue queue = Volley.newRequestQueue( SearchLostPostActivity.this );
        queue.add( searchLostPostRequest );
    }
}