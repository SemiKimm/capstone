package com.example.capstone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MypageActivity extends AppCompatActivity {

    private TextView tv_name, tv_session, tv_id;
    private Button home_button;
    public static String user_name, login_id, session, user_phone, login_pwd, profile_img;
    private ImageView img;
    //String mJsonString;

    private Uri urii;
    private static final int REQUEST_CODE=0; //사진 요청 코드드

    final private String url = "http://myapp.dothome.co.kr/UserInfo.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        final SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        final Button logout = (Button) findViewById(R.id.logoutButton);
        final Button edit_profile = (Button) findViewById((R.id.edit_profile));


        tv_name = findViewById(R.id.tv_name);
        tv_session = findViewById(R.id.tv_session);
        tv_id = findViewById(R.id.tv_id);



        ContentValues values = new ContentValues();
        values.put("login_id", sharedPreferences.getString("inputId",""));

        login_id = sharedPreferences.getString("inputId","");


        HttpUtil httpUtil = new HttpUtil(url, values);
        httpUtil.execute();




        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, EditProfile.class);
                intent.putExtra("login_id", sharedPreferences.getString("inputId",""));
                intent.putExtra("user_name", user_name);
                intent.putExtra("login_pwd", login_pwd);
                intent.putExtra("profile_img", profile_img);

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
                JSONObject jsonObject = new JSONObject(result);

                user_name = jsonObject.getString("user_name");
                user_phone = jsonObject.getString("user_phone");
                login_pwd = jsonObject.getString("login_pwd");
                profile_img = jsonObject.getString("profile_img");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            tv_name.setText(user_name);
            tv_session.setText(login_pwd);
            //tv_id.setText(profile_img);

            // 프로필 이미지
            img=findViewById(R.id.user_profile);
            Glide.with(getApplicationContext())
                    .load(profile_img)
                    .into(img);


        }
    }






}