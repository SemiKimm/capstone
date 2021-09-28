package com.example.capstone2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

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
                new int[]{R.id.lost_textView_list_title, R.id.lost_textView_list_category, R.id.lost_textView_list_local, R.id.lost_textView_list_place, R.id.lost_textView_list_date, R.id.lost_textView_list_color, R.id.lost_textView_list_more_info, R.id.lost_imgView_list}
        );
        ((SimpleAdapter) adapter).setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if(view.getId() == R.id.lost_imgView_list) {
                    ImageView imageView = (ImageView) view;
                    String drawable = data.toString();
                    Glide.with(SearchLostPostListActivity.this).load(drawable).override(200,200)
                            .error(R.drawable.defaultimg).into(imageView);
                    return true;
                }
                return false;
            }
        });
        mlistView.setAdapter(adapter);

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
            Intent postIntent = new Intent(SearchLostPostListActivity.this,LostPostActivity.class);
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
