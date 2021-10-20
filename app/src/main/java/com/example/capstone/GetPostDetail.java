package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class GetPostDetail extends AppCompatActivity {

    private static final String TAG_GetPostImg ="GetPostImgData";
    private static String TAG = "getJsonList";
    String mJsonString;
    private static final String TAG_JSON="getpostdata";

    private TextView tv_posterId;
    private Button report_getpost_btn, chat_btn;
    public static String login_id;

    private TextView postTitle,postCategory,postLocal,postPlace, postColor , postDate, postMoreInfo;
    ImageView img;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_post_detail);

        final SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        login_id = sharedPreferences.getString("inputId","");

        tv_posterId = findViewById(R.id.posterId); // 작성자 id

        postTitle=findViewById(R.id.postTitle);
        postCategory=findViewById(R.id.postCategory);
        postLocal=findViewById(R.id.postLocal);
        postPlace=findViewById(R.id.postPlace);
        postColor=findViewById(R.id.postColor);
        postDate=findViewById(R.id.postDate);
        postMoreInfo=findViewById(R.id.postMoreInfo);

        Intent intent = getIntent();
        String posterId = intent.getStringExtra("posterId"); // 게시물 작성자 id
        String postId = intent.getStringExtra("postId"); // 게시물id
        String postTitleData = intent.getStringExtra("postTitle");
        String postCategoryData=intent.getExtras().getString("category");
        String postLocalData=intent.getExtras().getString("local");
        String postPlaceData=intent.getExtras().getString("place");
        String postColorData=intent.getExtras().getString("color");
        String postDateData=intent.getExtras().getString("date");
        String postMoreInfoData=intent.getExtras().getString("moreInfo");
        //String postImgUriData=intent.getExtras().getString("imgUri");



        postTitle.setText(postTitleData);
        postCategory.setText(postCategoryData);
        postLocal.setText(postLocalData);
        postPlace.setText(postPlaceData);
        postColor.setText(postColorData);
        postDate.setText(postDateData);
        postMoreInfo.setText(postMoreInfoData);

        img=findViewById(R.id.postImg);

        GetData task = new GetData();
        task.execute("http://myapp.dothome.co.kr/GetPostGetJson.php");





        /*
        Glide.with(getApplicationContext())
                .load(postImgUriData)
                .into(img);

         */


        tv_posterId.setText(posterId); // 작성자 id
        tv_posterId.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(login_id.equals(posterId)){ // 본인이 작성한 글이면 본인 mypage로 이동
                    Intent intent = new Intent( GetPostDetail.this, MypageActivity.class );
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(GetPostDetail.this, MembersPageActivity.class);
                    intent.putExtra("posterId", posterId);
                    startActivity(intent);
                }
            }
        });

        chat_btn = findViewById(R.id.postChatBtn);


        // 본인이 작성한 게시물 채팅 막기
        if(login_id.equals(posterId)){
            chat_btn.setVisibility(View.GONE);
        }




        chat_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( GetPostDetail.this, MessageActivity.class );
                intent.putExtra("posterId", posterId); // 게시물 작성자 id
                intent.putExtra("postId", postId); // 게시물 id
                startActivity( intent );
            }
        });




        report_getpost_btn = findViewById(R.id.report_getpost);

        // 본인이 작성한 게시물 신고 막기
        if(login_id.equals(posterId)){
            report_getpost_btn.setVisibility(View.GONE);
        }


        report_getpost_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( GetPostDetail.this, ReportPostActivity.class );
                intent.putExtra("posterId", posterId); // 게시물 작성자 id
                intent.putExtra("postId", postId); // 게시물 id
                intent.putExtra("postTitle", postTitleData); // 게시물 제목
                startActivity( intent );
            }
        });


    }


    public static byte[] binaryStringToByteArray(String s) {
        int count = s.length() / 8;
        byte[] b = new byte[count];
        for (int i = 1; i < count; ++i) {
            String t = s.substring((i - 1) * 8, i * 8);
            b[i - 1] = binaryStringToByte(t);
        }
        return b;
    }

    public static byte binaryStringToByte(String s) {
        byte ret = 0, total = 0;
        for (int i = 0; i < 8; ++i) {
            ret = (s.charAt(7 - i) == '1') ? (byte) (1 << i) : 0;
            total = (byte) (ret | total);
        }
        return total;
    }

    public static Bitmap StringToBitmap(String ImageString) {
        try {
            byte[] bytes = binaryStringToByteArray(ImageString);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            Bitmap bitmap = BitmapFactory.decodeStream(bais);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    /*
    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

     */

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(GetPostDetail.this,
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
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);
                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();
            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }

        }
    }

    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){
                JSONObject item = jsonArray.getJSONObject(i);

                String GetPostImgData = item.getString(TAG_GetPostImg);
                Log.e("detail", GetPostImgData);

                Bitmap bitmap = StringToBitmap(GetPostImgData);



                img.setImageBitmap(bitmap);

                //img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                //img.setVisibility(View.VISIBLE);

/*
                Glide.with(getApplicationContext())
                        .load(bitmap)
                        .error(R.drawable.badge)
                        .into(img);

 */






                //Log.e("list_bitmap", GetPostImgData);





            }







        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }



}