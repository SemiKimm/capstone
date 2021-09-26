package com.example.capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;

public class SearchLostPostListActivity extends AppCompatActivity {
    private static final String TAG_LostPostTitle = "LostPostTitleData";
    private static final String TAG_LostPostCategory = "LostPostCategoryData";
    private static final String TAG_LostPostLocal ="LostPostLocalData";
    private static final String TAG_LostPostPlace = "LostPostPlaceData";
    private static final String TAG_LostPostDate ="LostPostDateData";
    private static final String TAG_LostPostColor ="LostPostColorData";
    private static final String TAG_LostPostMoreInfo ="LostPostMoreInfoData";
    private static final String TAG_LostPostImg ="LostPostImgData";

    private ImageView imgview;

    ArrayList mArrayList;
    ListView mlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_post_list);

        Button homeButton=findViewById(R.id.homeBtn);
        homeButton.setOnClickListener(view -> {
            Intent intent=new Intent(SearchLostPostListActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Button postingButton=findViewById(R.id.postingBtn);
        postingButton.setOnClickListener(view -> {
            Intent intent=new Intent(SearchLostPostListActivity.this, SelectPostingActivity.class);
            startActivity(intent);
        });

        mlistView = (ListView) findViewById(R.id.lost_listView_main_list);
        mArrayList = new ArrayList<>();

        //SearchLostPostActivity에서 넘긴 검색 조건 데이터 받기
        Intent intent = getIntent();
        mArrayList=(ArrayList) intent.getSerializableExtra("data");

        ListAdapter adapter = new SimpleAdapter(
                SearchLostPostListActivity.this, mArrayList, R.layout.lost_post_list_item,
                new String[]{TAG_LostPostTitle , TAG_LostPostCategory, TAG_LostPostLocal, TAG_LostPostPlace, TAG_LostPostDate, TAG_LostPostColor,  TAG_LostPostMoreInfo, TAG_LostPostImg},
                new int[]{R.id.lost_textView_list_title, R.id.lost_textView_list_category, R.id.lost_textView_list_local, R.id.lost_textView_list_place, R.id.lost_textView_list_date, R.id.lost_textView_list_color, R.id.lost_textView_list_more_info, R.id.lost_textView_list_img}
        );
        mlistView.setAdapter(adapter);
    }
}
