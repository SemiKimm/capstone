package com.example.capstone2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

public class PushGetCheckActivity extends AppCompatActivity {
    Boolean clothSubscribe=false;
    Boolean bagSubscribe=false;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword_push);

        Button homeButton = findViewById(R.id.homeBtn);
        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        Button postingButton=findViewById(R.id.postingBtn);
        postingButton.setOnClickListener(view -> {
            Intent intent=new Intent(this, SelectPostingActivity.class);
            startActivity(intent);
        });

        Button searchButton=findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(view -> {
            Intent intent=new Intent(this, SelectSearchPostActivity.class);
            startActivity(intent);
        });

        Button myButton = findViewById(R.id.myPageBtn);
        myButton.setOnClickListener(view -> {
            Intent intent=new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        //사용자 토큰 가져오기
        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("userToken","");

        Button clothBtn = findViewById(R.id.cloth);
        clothBtn.setOnClickListener(view->{
            if(clothSubscribe==false) {
                FirebaseMessaging.getInstance().subscribeToTopic("cloth").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        clothSubscribe=true;
                        String msg = getString(R.string.category_cloth_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.category_cloth_subscribed_failed);
                        }
                        Log.d("msg", msg);
                        Toast.makeText(PushGetCheckActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                });
            }
            else{
                FirebaseMessaging.getInstance().unsubscribeFromTopic("cloth").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        clothSubscribe=false;
                        String msg = getString(R.string.category_cloth_unsubscribed);
                        if(!task.isSuccessful()){
                            msg = getString(R.string.category_cloth_unsubscribed_failed);
                        }
                        Log.d("msg", msg);
                        Toast.makeText(PushGetCheckActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        Button bagBtn = findViewById(R.id.bag);
        bagBtn.setOnClickListener(view->{
            if(bagSubscribe==false) {
                FirebaseMessaging.getInstance().subscribeToTopic("bag").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        bagSubscribe=true;
                        String msg = getString(R.string.category_bag_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.category_bag_subscribed_failed);
                        }
                        Log.d("msg", msg);
                        Toast.makeText(PushGetCheckActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                });
            }
            else{
                FirebaseMessaging.getInstance().unsubscribeFromTopic("bag").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        bagSubscribe=false;
                        String msg = getString(R.string.category_bag_unsubscribed);
                        if(!task.isSuccessful()){
                            msg = getString(R.string.category_bag_unsubscribed_failed);
                        }
                        Log.d("msg", msg);
                        Toast.makeText(PushGetCheckActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("test","test: 공백");
                try {
                    JSONObject JsonObject = new JSONObject(response);
                    boolean success=JsonObject.getBoolean("success");
                    if(success){
                        Toast.makeText(getApplicationContext(),"키워드 등록 성공",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"키워드 등록 실패",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
//        //서버로 Volley를 이용해서 키워드 구독 처리 요청
//        KeywordRequest subscribeRequest = new KeywordRequest( user_keyword );
//        RequestQueue queue = Volley.newRequestQueue( PushGetCheckActivity.this );
//        queue.add( subscribeRequest );
    }
}
