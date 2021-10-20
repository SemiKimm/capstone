package com.example.capstone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

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


public class MypageActivity extends AppCompatActivity {

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
    private String count;
    private String posterId;
    private int icount=0;

    public static String drawable, imguri;

    private TextView tv_name, tv_id;
    public static String user_name, login_id, session, user_phone, login_pwd, profile_img;
    private ImageView img;

    final private String url = "http://myapp.dothome.co.kr/UserInfo.php";
    final private String url1 = "http://myapp.dothome.co.kr/GetMyPostGetJson.php";

    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    String mJsonString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);


        final SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        final Button logout = (Button) findViewById(R.id.logoutButton);
        final Button edit_profile = (Button) findViewById((R.id.edit_profile));
        final Button badge = (Button) findViewById(R.id.badge_list);


        tv_name = findViewById(R.id.tv_name);
        tv_id = findViewById(R.id.tv_id);

        login_id = sharedPreferences.getString("inputId","");

        // values 값 세팅
        ContentValues values = new ContentValues();
        values.put("login_id", sharedPreferences.getString("inputId",""));

        // httpurlconnection 실행. login_id 값 넘겨 줌
        HttpUtil httpUtil = new HttpUtil(url, values);
        httpUtil.execute();





        // 로그아웃 버튼 클릭 시 수행
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sharedprefernce에 저장된 값 모두 삭제
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("inputId");
                editor.remove("inputPwd");
                editor.putString(getResources().getString(R.string.prefAutoLoginState),"non-autoLogin");
                editor.putString(getResources().getString(R.string.prefLoginState),"loggedout");
                editor.apply();
                editor.commit();

                startActivity(new Intent(MypageActivity.this, LoginActivity.class));
                finish();

            }
        });

        // 프로필 편집 버튼 클릭 시 수행
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // editprofile activity로 값 넘겨주기
                Intent intent = new Intent(MypageActivity.this, EditProfile.class);
                intent.putExtra("login_id", sharedPreferences.getString("inputId",""));
                intent.putExtra("user_name", user_name);
                intent.putExtra("login_pwd", login_pwd);
                intent.putExtra("profile_img", profile_img);

                startActivity(intent);
            }
        });

        // 배지 보기 버튼 클릭 시 수행
        badge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // editprofile activity로 값 넘겨주기
                Intent intent = new Intent(MypageActivity.this, BadgeActivity.class);
                intent.putExtra("login_id", sharedPreferences.getString("inputId",""));
                intent.putExtra("postcount", icount);
                startActivity(intent);
            }
        });




        // 본인이 작성한 글 목록 불러오기
        mlistView = (ListView) findViewById(R.id.my_post_list);
        mArrayList = new ArrayList<>();

        ContentValues values1 = new ContentValues();
        values1.put("login_id", sharedPreferences.getString("inputId",""));

        GetData task = new GetData(url1, values1);
        task.execute();

        // 리스트뷰 클릭 시 해당 글 상세보기로 넘어감
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                HashMap<String, String> data = (HashMap<String, String>) adapterView.getItemAtPosition(position);
                //Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, GetPostListActivity.this, GetPostDetail.class);
                Intent intent = new Intent(MypageActivity.this, GetPostDetail.class);
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


    public class HttpUtil extends AsyncTask<Void, Void, String>{
        private String url;
        private ContentValues values;

        public HttpUtil(String url, ContentValues values){
            this.url = url;
            this.values = values;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params){
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            String result = requestHttpURLConnection.postRequest(url, values);
            return result;
        }

        @Override
        protected  void onPostExecute(String result){
            super.onPostExecute(result);

            try {
                // jsonobject value 파싱
                JSONObject jsonObject = new JSONObject(result);
                user_name = jsonObject.getString("user_name");
                user_phone = jsonObject.getString("user_phone");
                login_pwd = jsonObject.getString("login_pwd");
                profile_img = jsonObject.getString("profile_img");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // php에서 받아 온 값 세팅
            tv_name.setText(user_name);
            tv_id.setText(login_id);

            // 프로필 이미지
            img=findViewById(R.id.user_profile);
            Glide.with(getApplicationContext())
                    .load(profile_img)
                    .into(img);


            Log.e("name", user_name);

        }
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
            progressDialog = ProgressDialog.show(MypageActivity.this,
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

                imguri = GetPostImgData;



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
                    MypageActivity.this, mArrayList, R.layout.get_post_list_item,
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
                        GlideApp.with(MypageActivity.this).load(drawable)
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