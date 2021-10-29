package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class MyGetPostListActivity extends AppCompatActivity {

    private static String TAG = "getJsonList";

    private static final String TAG_JSON="getpostdata";
    private static final String TAG_GetPostTitle = "GetPostTitleData";
    private static final String TAG_GetPostCategory = "GetPostCategoryData";
    private static final String TAG_GetPostLocal = "GetPostLocalData";
    private static final String TAG_GetPostPlace = "GetPostPlaceData";
    private static final String TAG_GetPostDate ="GetPostDateData";
    private static final String TAG_GetPostColor ="GetPostColorData";
    private static final String TAG_GetPostMoreInfo ="GetPostMoreInfoData";
    private static final String TAG_GetPostImg ="GetPostImgData";
    private static final String TAG_GetPostUserIdData ="GetPostUserIdData";
    private static final String TAG_GetPostIdData ="GetPostIdData";
    private static final String TAG_count = "count";

    private String count = "";
    private String posterId;
    private int icount=0;

    TextView tv_non;

    public static String drawable, imguri;

    final private String url1 = "http://myapp.dothome.co.kr/GetMyPostGetJson.php";

    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    String mJsonString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_get_post_list);

        final SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);


        // 본인이 작성한 글 목록 불러오기
        mlistView = (ListView) findViewById(R.id.my_get_post_list);
        mArrayList = new ArrayList<>();

        ContentValues values1 = new ContentValues();
        values1.put("login_id", sharedPreferences.getString("inputId",""));

        GetData task = new GetData(url1, values1);
        task.execute();

        tv_non = findViewById(R.id.list_non);


        // 리스트뷰 클릭 시 해당 글 상세보기로 넘어감
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                HashMap<String, String> data = (HashMap<String, String>) adapterView.getItemAtPosition(position);
                //Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, GetPostListActivity.this, GetPostDetail.class);
                Intent intent = new Intent(MyGetPostListActivity.this, GetPostDetail.class);
                /* putExtra의 첫 값은 식별 태그, 뒤에는 다음 화면에 넘길 값 */
                intent.putExtra("posterId", data.get(TAG_GetPostUserIdData));
                intent.putExtra("postId", data.get(TAG_GetPostIdData));
                intent.putExtra("postTitle", data.get(TAG_GetPostTitle));
                intent.putExtra("category", data.get(TAG_GetPostCategory));
                intent.putExtra("local", data.get(TAG_GetPostLocal));
                intent.putExtra("place", data.get(TAG_GetPostPlace));
                intent.putExtra("color", data.get(TAG_GetPostColor));
                intent.putExtra("date", data.get(TAG_GetPostDate));
                intent.putExtra("moreInfo", data.get(TAG_GetPostMoreInfo));
                intent.putExtra("imgUri", data.get(TAG_GetPostImg));

                startActivity(intent);
            }
        });

    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        private String url;
        private ContentValues values;

        public GetData(String url, ContentValues values){
            this.url = url;
            this.values = values;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MyGetPostListActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);

            if (result == null){
                //mTextViewResult.setText(errorString);
            }
            else {
                mJsonString = result;
                showResult();
                //tv_name.setText(drawable);
                //Log.e("muri", imguri);

            }
        }

        @Override
        protected String doInBackground(String... params) {
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            String result = requestHttpURLConnection.postRequest(url, values);
            return result;

        }
    }

    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){
                JSONObject item = jsonArray.getJSONObject(i);

                String GetPostIdData = item.getString(TAG_GetPostIdData );
                String GetPostTitleData = item.getString(TAG_GetPostTitle );
                String GetPostCategoryData = item.getString(TAG_GetPostCategory );
                String GetPostLocalData = item.getString(TAG_GetPostLocal );
                String GetPostPlaceData = item.getString(TAG_GetPostPlace );
                String GetPostDateData = item.getString(TAG_GetPostDate );
                String GetPostColorData = item.getString(TAG_GetPostColor );
                String GetPostMoreInfoData = item.getString(TAG_GetPostMoreInfo );
                String GetPostImgData = item.getString(TAG_GetPostImg );
                String GetPostUserIdData = item.getString(TAG_GetPostUserIdData );
                count = item.getString(TAG_count); // 글 작성 횟수
                icount = Integer.parseInt(count);

                System.out.println(icount);

                imguri = GetPostImgData;

                if("".equals(count)) { // 작성한 게시물이 없으면 "작성 내역 없음" 보여줌
                    tv_non.setVisibility(View.VISIBLE);
                } else {
                    tv_non.setVisibility(View.GONE);
                }



                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_GetPostIdData, GetPostIdData);
                hashMap.put(TAG_GetPostTitle, GetPostTitleData);
                hashMap.put(TAG_GetPostCategory, GetPostCategoryData);
                hashMap.put(TAG_GetPostLocal, GetPostLocalData);
                hashMap.put(TAG_GetPostPlace, GetPostPlaceData);
                hashMap.put(TAG_GetPostDate, GetPostDateData);
                hashMap.put(TAG_GetPostColor, GetPostColorData);
                hashMap.put(TAG_GetPostMoreInfo, GetPostMoreInfoData);
                hashMap.put(TAG_GetPostImg, GetPostImgData);
                hashMap.put(TAG_GetPostUserIdData, GetPostUserIdData);
                hashMap.put(TAG_count, count);

                posterId = GetPostUserIdData;

                mArrayList.add(hashMap);
            }


            ListAdapter adapter = new SimpleAdapter(
                    MyGetPostListActivity.this, mArrayList, R.layout.get_post_list_item,
                    new String[]{TAG_GetPostTitle ,TAG_GetPostCategory ,TAG_GetPostLocal, TAG_GetPostPlace , TAG_GetPostDate, TAG_GetPostColor,  TAG_GetPostMoreInfo, TAG_GetPostImg},
                    new int[]{R.id.get_textView_list_title, R.id.get_textView_list_category, R.id.get_textView_list_local, R.id.get_textView_list_place, R.id.get_textView_list_date, R.id.get_textView_list_color, R.id.get_textView_list_more_info, R.id.get_imgView_list}
            );
            ((SimpleAdapter) adapter).setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data, String textRepresentation) {
                    if(view.getId() == R.id.get_imgView_list) {
                        ImageView imageView = (ImageView) view;

                        drawable = data.toString();
                        //String drawable = String.valueOf(data);
                        GlideApp.with(MyGetPostListActivity.this).load(drawable)
                                .into(imageView);

                        return true;
                    }
                    return false;
                }
            });

            mlistView.setAdapter(adapter);








        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }
}