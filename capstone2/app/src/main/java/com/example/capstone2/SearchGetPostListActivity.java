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
import java.util.ArrayList;
import java.util.HashMap;

public class SearchGetPostListActivity extends AppCompatActivity {
    private static final String TAG_GetPostTitle = "GetPostTitleData";
    private static final String TAG_GetPostCategory = "GetPostCategoryData";
    private static final String TAG_GetPostLocal ="GetPostLocalData";
    private static final String TAG_GetPostPlace = "GetPostPlaceData";
    private static final String TAG_GetPostDate ="GetPostDateData";
    private static final String TAG_GetPostColor ="GetPostColorData";
    private static final String TAG_GetPostMoreInfo ="GetPostMoreInfoData";
    private static final String TAG_GetPostImg ="GetPostImgData";

    private ImageView imgview;

    ArrayList mArrayList;
    ListView mlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_post_list);

        Button homeButton=findViewById(R.id.homeBtn);
        homeButton.setOnClickListener(view -> {
            Intent intent=new Intent(SearchGetPostListActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Button postingButton=findViewById(R.id.postingBtn);
        postingButton.setOnClickListener(view -> {
            Intent intent=new Intent(SearchGetPostListActivity.this, SelectPostingActivity.class);
            startActivity(intent);
        });

        mlistView = (ListView) findViewById(R.id.get_listView_main_list);
        mArrayList = new ArrayList<>();

        //SearchLostPostActivity에서 넘긴 검색 조건 데이터 받기
        Intent intent = getIntent();
        mArrayList=(ArrayList) intent.getSerializableExtra("data");

        ListAdapter adapter = new SimpleAdapter(
                SearchGetPostListActivity.this, mArrayList, R.layout.get_post_list_item,
                new String[]{TAG_GetPostTitle ,TAG_GetPostCategory ,TAG_GetPostLocal, TAG_GetPostPlace , TAG_GetPostDate, TAG_GetPostColor,  TAG_GetPostMoreInfo, TAG_GetPostImg},
                new int[]{R.id.get_textView_list_title, R.id.get_textView_list_category, R.id.get_textView_list_local, R.id.get_textView_list_place, R.id.get_textView_list_date, R.id.get_textView_list_color, R.id.get_textView_list_more_info, R.id.get_textView_list_img}
        );
        mlistView.setAdapter(adapter);

        //목록 눌러서 해당 게시글 데이터 받아서 보내기
        mlistView.setOnItemClickListener((AdapterView.OnItemClickListener) (adapterView, view, index, l) -> {
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
            Intent postIntent = new Intent(SearchGetPostListActivity.this,GetPostActivity.class);
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
