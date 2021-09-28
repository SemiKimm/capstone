package com.example.capstone2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoryLostListActivity extends AppCompatActivity {
    private static final String TAG_LostPostTitle = "LostPostTitleData";
    private static final String TAG_LostPostCategory = "LostPostCategoryData";
    private static final String TAG_LostPostLocal ="LostPostLocalData";
    private static final String TAG_LostPostPlace = "LostPostPlaceData";
    private static final String TAG_LostPostDate ="LostPostDateData";
    private static final String TAG_LostPostColor ="LostPostColorData";
    private static final String TAG_LostPostMoreInfo ="LostPostMoreInfoData";
    private static final String TAG_LostPostImg ="LostPostImgData";

    String categoryData;

    ArrayList mArrayList;
    ListView mlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_post_list);

        Button homeButton=findViewById(R.id.homeBtn);
        homeButton.setOnClickListener(view -> {
            Intent intent=new Intent(CategoryLostListActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Button postingButton=findViewById(R.id.postingBtn);
        postingButton.setOnClickListener(view -> {
            Intent intent=new Intent(CategoryLostListActivity.this, SelectPostingActivity.class);
            startActivity(intent);
        });

        mlistView = (ListView) findViewById(R.id.lost_listView_main_list);


        //SearchLostPostActivity에서 넘긴 검색 조건 데이터 받기
        Intent intent = getIntent();
        categoryData=intent.getExtras().getString("category");

        Response.Listener<String> lostresponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject JsonObject = new JSONObject(response);
                    boolean success=JsonObject.getBoolean("success");
                    JSONArray jsonArray = JsonObject.getJSONArray("lostpostdata");
                    mArrayList = new ArrayList<>();
                    Log.e("lostlist",String.valueOf(jsonArray));
                    Log.e("jsonArray길이", String.valueOf(Integer.valueOf(jsonArray.length())));
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
                    }
                    if(success){
                        ListAdapter adapter = new SimpleAdapter(
                                CategoryLostListActivity.this, mArrayList, R.layout.lost_post_list_item,
                                new String[]{TAG_LostPostTitle , TAG_LostPostCategory, TAG_LostPostLocal, TAG_LostPostPlace, TAG_LostPostDate, TAG_LostPostColor,  TAG_LostPostMoreInfo, TAG_LostPostImg},
                                new int[]{R.id.lost_textView_list_title, R.id.lost_textView_list_category, R.id.lost_textView_list_local, R.id.lost_textView_list_place, R.id.lost_textView_list_date, R.id.lost_textView_list_color, R.id.lost_textView_list_more_info, R.id.lost_textView_list_img}
                        );
                        mlistView.setAdapter(adapter);
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
        CategoryLostListRequest categoryLostListRequest = new CategoryLostListRequest( categoryData, lostresponseListener);
        RequestQueue queue = Volley.newRequestQueue( CategoryLostListActivity.this );
        queue.add( categoryLostListRequest );

        //목록 눌러서 해당 게시글 데이터 받아서 보내기
        mlistView.setOnItemClickListener((AdapterView.OnItemClickListener) (adapterView, view, index, l) -> {
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
            Intent postIntent = new Intent(CategoryLostListActivity.this,LostPostActivity.class);
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
    }
}
