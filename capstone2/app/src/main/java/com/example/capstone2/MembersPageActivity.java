package com.example.capstone2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import org.json.JSONException;
import org.json.JSONObject;

public class MembersPageActivity extends AppCompatActivity {

    private TextView tv_name, tv_id;
    public static String user_name, member_id, session, user_phone, login_pwd, profile_img;
    private ImageView img;

    final private String url = "http://jamong.ivyro.net/UserInfo.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_page);

        final Button reportBtn = (Button) findViewById((R.id.reportButton));

        tv_name = findViewById(R.id.tv_name);
        tv_id = findViewById(R.id.tv_id);

        Intent intent = getIntent();
        member_id = intent.getStringExtra("posterId");

        // values 값 세팅
        ContentValues values = new ContentValues();
        values.put("login_id", member_id);

        // httpurlconnection 실행. member_id 값 넘겨 줌
        HttpUtil httpUtil = new HttpUtil(url, values);
        httpUtil.execute();

        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // editprofile activity로 값 넘겨주기
                Intent intent = new Intent(MembersPageActivity.this, ReportActivity.class);
                intent.putExtra("reported_id", member_id);

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
            tv_id.setText(member_id);

            // 프로필 이미지
            img=findViewById(R.id.user_profile);
            Glide.with(getApplicationContext())
                    .load(profile_img)
                    .into(img);
        }
    }
}