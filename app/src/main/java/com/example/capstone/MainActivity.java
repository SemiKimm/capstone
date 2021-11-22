package com.example.capstone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button btn_mypg; //마이페이지 임시 버튼
    Button btn_posting;
    Button btn_getpostlist;
    Button btn_chatList;

    SharedPreferences sharedPreferences;

    private AlertDialog dialog;

    final private String url = "http://myapp.dothome.co.kr/UserInfo.php";
    public static String login_id, sreportCount;
    public static int ireportCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        // 로그인, 자동로그인 상태 가져오기
        String loginStatus = sharedPreferences.getString("loginstate", "");
        String autoLoginStatus = sharedPreferences.getString(getResources().getString(R.string.prefAutoLoginState), "");

        // 아이디 값
        login_id = sharedPreferences.getString("inputId", "");

        // values 값 세팅
        ContentValues values = new ContentValues();
        values.put("login_id", sharedPreferences.getString("inputId", ""));

        // httpurlconnection 실행. login_id 값 넘겨 줌
        HttpUtil httpUtil = new HttpUtil(url, values);
        httpUtil.execute();

        //마이페이지로 이동 (임시)
        btn_mypg = findViewById(R.id.button7);
        btn_mypg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ireportCount >= 5) { //신고횟수 파악

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("inputId");
                    editor.remove("inputPwd");
                    editor.putString(getResources().getString(R.string.prefAutoLoginState), "non-autoLogin");
                    editor.putString(getResources().getString(R.string.prefLoginState), "loggedout");
                    editor.apply();
                    editor.commit();

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    dialog = builder.setMessage(String.format("신고 %s회 누적으로 사용이 제한되었습니다.\n문의 : kce6064@naver.com", sreportCount)).setNegativeButton("확인", null).create();
                    dialog.show();

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);

                }

                // 로그인 상태이면 바로 mypage로 이동
                else if (loginStatus.equals("loggedin") && autoLoginStatus.equals("autoLogin")) {
                    //Toast.makeText(getApplicationContext(),"자동 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MypageActivity.class);
                    startActivity(intent);
                } else { // 로그아웃 상태이면 login으로 이동
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        });

        btn_posting = findViewById(R.id.posting);
        btn_posting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SelectPostingActivity.class);
                startActivity(intent);

            }
        });

        btn_getpostlist = findViewById(R.id.GetPostList);
        btn_getpostlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, GetPostListActivity.class);
                startActivity(intent);

            }
        });

        btn_chatList = findViewById(R.id.chatList);
        btn_chatList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ChatListActivity.class);
                startActivity(intent);

            }
        });
    }

    public class HttpUtil extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;

        public HttpUtil(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            String result = requestHttpURLConnection.postRequest(url, values);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                // jsonobject value 파싱
                JSONObject jsonObject = new JSONObject(result);
                sreportCount = jsonObject.getString("count");
                ireportCount = Integer.parseInt(sreportCount);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}