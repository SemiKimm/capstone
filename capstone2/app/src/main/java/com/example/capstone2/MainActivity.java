package com.example.capstone2;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    String[] localItems = {"지역 선택", "서울특별시", "강원도", "경기도", "경상남도", "경상북도", "광주광역시", "대구광역시"
            , "대전광역시", "부산광역시", "울산광역시", "인천광역시", "전라남도", "전라북도", "충청남도", "충청북도"
            , "제주특별자치도", "세종특별자치시", "기타"};
    String selectedLocal, selectedCategory;

    //습득물 목록 관련 데이터
    private static String TAG = "getJsonList";
    private static final String TAG_GETJSON="getpostdata";
    private static final String TAG_GetPostTitle = "GetPostTitleData";
    private static final String TAG_GetPostCategory = "GetPostCategoryData";
    private static final String TAG_GetPostLocal = "GetPostLocalData";
    private static final String TAG_GetPostPlace = "GetPostPlaceData";
    private static final String TAG_GetPostDate ="GetPostDateData";
    private static final String TAG_GetPostColor ="GetPostColorData";
    private static final String TAG_GetPostMoreInfo ="GetPostMoreInfoData";
    private static final String TAG_GetPostImg ="GetPostImgData";
    //분실물 목록 관련 데이터
    private static final String TAG_LOSTJSON="lostpostdata";
    private static final String TAG_LostPostTitle = "LostPostTitleData";
    private static final String TAG_LostPostCategory = "LostPostCategoryData";
    private static final String TAG_LostPostLocal ="LostPostLocalData";
    private static final String TAG_LostPostPlace = "LostPostPlaceData";
    private static final String TAG_LostPostDate ="LostPostDateData";
    private static final String TAG_LostPostColor ="LostPostColorData";
    private static final String TAG_LostPostMoreInfo ="LostPostMoreInfoData";
    private static final String TAG_LostPostImg ="LostPostImgData";

    ArrayList<HashMap<String, String>> mlostArrayList,mgetArrayList;
    GridView mlostlistView,mgetlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mgetlistView = (GridView) findViewById(R.id.get_main_list);
        mlostlistView = (GridView) findViewById(R.id.lost_main_list);

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
        ArrayAdapter<String> localAdapter=new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item,localItems
        );
        localAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        localSpin.setAdapter(localAdapter);
        localSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override//선택되면
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedLocal=localItems[position];
                Log.e("localdata",selectedLocal);

                Response.Listener<String> getresponseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject JsonObject = new JSONObject(response);
                            boolean success=JsonObject.getBoolean("success");
                            JSONArray jsonArray = JsonObject.getJSONArray("getpostdata");
                            Log.e("getlist",String.valueOf(JsonObject));
                            mgetArrayList = new ArrayList<>();
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject item = jsonArray.getJSONObject(i);

                                String GetPostTitleData = item.getString(TAG_GetPostTitle );
                                String GetPostCategoryData = item.getString(TAG_GetPostCategory );
                                String GetPostLocalData = item.getString(TAG_GetPostLocal );
                                String GetPostPlaceData = item.getString(TAG_GetPostPlace );
                                String GetPostDateData = item.getString(TAG_GetPostDate );
                                String GetPostColorData = item.getString(TAG_GetPostColor );
                                String GetPostMoreInfoData = item.getString(TAG_GetPostMoreInfo );
                                String GetPostImgData = item.getString(TAG_GetPostImg );

                                HashMap<String,String> hashMap = new HashMap<>();

                                hashMap.put(TAG_GetPostTitle, GetPostTitleData);
                                hashMap.put(TAG_GetPostCategory, GetPostCategoryData);
                                hashMap.put(TAG_GetPostLocal, GetPostLocalData);
                                hashMap.put(TAG_GetPostPlace, GetPostPlaceData);
                                hashMap.put(TAG_GetPostDate, GetPostDateData);
                                hashMap.put(TAG_GetPostColor, GetPostColorData);
                                hashMap.put(TAG_GetPostMoreInfo, GetPostMoreInfoData);
                                hashMap.put(TAG_GetPostImg, GetPostImgData);
                                mgetArrayList.add(hashMap);
                                Log.e("gettest",String.valueOf(mgetArrayList));
                            }
                            if(success){
                                ListAdapter adapter = new SimpleAdapter(
                                        MainActivity.this, mgetArrayList, R.layout.main_get_list_item,
                                        new String[]{TAG_GetPostTitle ,TAG_GetPostCategory ,TAG_GetPostLocal, TAG_GetPostDate, TAG_GetPostImg},
                                        new int[]{R.id.get_textView_list_title, R.id.get_textView_list_category, R.id.get_textView_list_local, R.id.get_textView_list_date, R.id.get_imgView_list}
                                );
                                ((SimpleAdapter) adapter).setViewBinder(new SimpleAdapter.ViewBinder() {
                                    @Override
                                    public boolean setViewValue(View view, Object data, String textRepresentation) {
                                        if(view.getId() == R.id.get_imgView_list) {
                                            ImageView imageView = (ImageView) view;
                                            String drawable = data.toString();
                                            Glide.with(MainActivity.this).load(drawable).override(200,200)
                                                    .error(R.drawable.defaultimg).into(imageView);
                                            return true;
                                        }
                                        return false;
                                    }
                                });
                                mgetlistView.setAdapter(adapter);

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
                MainGetPostRequest mainGetPostRequest = new MainGetPostRequest( selectedLocal, getresponseListener);
                RequestQueue getqueue = Volley.newRequestQueue( MainActivity.this );
                getqueue.add( mainGetPostRequest );

                Response.Listener<String> lostresponseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject JsonObject = new JSONObject(response);
                            boolean success=JsonObject.getBoolean("success");
                            JSONArray jsonArray = JsonObject.getJSONArray("lostpostdata");
                            Log.e("lostlist",String.valueOf(jsonArray));
                            Log.e("jsonArray길이", String.valueOf(Integer.valueOf(jsonArray.length())));
                            mlostArrayList = new ArrayList<>();
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
                                mlostArrayList.add(hashMap);
                                Log.e("losttest",String.valueOf(mlostArrayList));
                            }
                            if(success){
                                ListAdapter adapter = new SimpleAdapter(
                                        MainActivity.this, mlostArrayList, R.layout.main_lost_list_item,
                                        new String[]{TAG_LostPostTitle , TAG_LostPostCategory, TAG_LostPostLocal, TAG_LostPostDate, TAG_LostPostImg},
                                        new int[]{R.id.lost_textView_list_title, R.id.lost_textView_list_category, R.id.lost_textView_list_local, R.id.lost_textView_list_date, R.id.lost_imgView_list}
                                );
                                ((SimpleAdapter) adapter).setViewBinder(new SimpleAdapter.ViewBinder() {
                                    @Override
                                    public boolean setViewValue(View view, Object data, String textRepresentation) {
                                        if(view.getId() == R.id.lost_imgView_list) {
                                            ImageView imageView = (ImageView) view;
                                            String drawable = data.toString();
                                            Glide.with(MainActivity.this).load(drawable).override(200,200)
                                                    .error(R.drawable.defaultimg).into(imageView);
                                            return true;
                                        }
                                        return false;
                                    }
                                });
                                mlostlistView.setAdapter(adapter);

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
                MainLostPostRequest mainLostPostRequest = new MainLostPostRequest( selectedLocal, lostresponseListener);
                RequestQueue lostqueue = Volley.newRequestQueue( MainActivity.this );
                lostqueue.add( mainLostPostRequest );
            }
            @Override//아무것도 선택 안되면
            public void onNothingSelected(AdapterView<?> parent) {
                selectedLocal ="지역 선택";
            }
        });

        //습득물 목록 눌러서 해당 게시글 데이터 받아서 보내기
        mgetlistView.setOnItemClickListener((AdapterView.OnItemClickListener) (adapterView, view, index, l) -> {
            HashMap<String,String> data =(HashMap<String,String>) adapterView.getItemAtPosition(index);
            Log.e("itemdata",String.valueOf(data));
            String title=data.get(TAG_GetPostTitle);
            String category=data.get(TAG_GetPostCategory);
            String local = data.get(TAG_GetPostLocal);
            String place = data.get(TAG_GetPostPlace);
            String color = data.get(TAG_GetPostColor);
            String date = data.get(TAG_GetPostDate);
            String moreInfo=data.get(TAG_GetPostMoreInfo);
            String imgUri=data.get(TAG_GetPostImg);
            Intent postIntent = new Intent(MainActivity.this,GetPostActivity.class);
            postIntent.putExtra("title",title);
            postIntent.putExtra("category",category);
            postIntent.putExtra("local",local);
            postIntent.putExtra("place",place);
            postIntent.putExtra("color",color);
            postIntent.putExtra("date",date);
            postIntent.putExtra("moreInfo",moreInfo);
            postIntent.putExtra("imgUri",imgUri);
            startActivity(postIntent);
        });

        //분실물 목록 눌러서 해당 게시글 데이터 받아서 보내기
        mlostlistView.setOnItemClickListener((AdapterView.OnItemClickListener) (adapterView, view, index, l) -> {
            HashMap<String,String> data =(HashMap<String,String>) adapterView.getItemAtPosition(index);
            Log.e("itemdata",String.valueOf(data));
            String title=data.get(TAG_LostPostTitle);
            String category=data.get(TAG_LostPostCategory);
            String local = data.get(TAG_LostPostLocal);
            String place = data.get(TAG_LostPostPlace);
            String color = data.get(TAG_LostPostColor);
            String date = data.get(TAG_LostPostDate);
            String moreInfo=data.get(TAG_LostPostMoreInfo);
            String imgUri=data.get(TAG_LostPostImg);
            Intent intent = new Intent(MainActivity.this,LostPostActivity.class);
            intent.putExtra("title",title);
            intent.putExtra("category",category);
            intent.putExtra("local",local);
            intent.putExtra("place",place);
            intent.putExtra("color",color);
            intent.putExtra("date",date);
            intent.putExtra("moreInfo",moreInfo);
            intent.putExtra("imgUri",imgUri);
            startActivity(intent);
        });

        Button clothbtn=findViewById(R.id.cloth);
        clothbtn.setOnClickListener(view -> {
            selectedCategory=clothbtn.getText().toString();
            Log.e("cloth",selectedCategory);
            Intent intent=new Intent(MainActivity.this,CategoryLostListActivity.class);
            intent.putExtra("category",selectedCategory);
            startActivity(intent);
        });

        Button bagbtn=findViewById(R.id.bag);
        bagbtn.setOnClickListener(view -> {
            selectedCategory=bagbtn.getText().toString();
            Log.e("bag",selectedCategory);
            Intent intent=new Intent(MainActivity.this,CategoryLostListActivity.class);
            intent.putExtra("category",selectedCategory);
            startActivity(intent);
        });

        Button accessorybtn=findViewById(R.id.accessory);
        accessorybtn.setOnClickListener(view -> {
            selectedCategory=accessorybtn.getText().toString();
            Log.e("bag",selectedCategory);
            Intent intent=new Intent(MainActivity.this,CategoryLostListActivity.class);
            intent.putExtra("category",selectedCategory);
            startActivity(intent);
        });

        Button shoesbtn=findViewById(R.id.shoes);
        shoesbtn.setOnClickListener(view -> {
            selectedCategory=shoesbtn.getText().toString();
            Log.e("bag",selectedCategory);
            Intent intent=new Intent(MainActivity.this,CategoryLostListActivity.class);
            intent.putExtra("category",selectedCategory);
            startActivity(intent);
        });

        Button watchbtn=findViewById(R.id.watch);
        watchbtn.setOnClickListener(view -> {
            selectedCategory=watchbtn.getText().toString();
            Log.e("bag",selectedCategory);
            Intent intent=new Intent(MainActivity.this,CategoryLostListActivity.class);
            intent.putExtra("category",selectedCategory);
            startActivity(intent);
        });

    }
}