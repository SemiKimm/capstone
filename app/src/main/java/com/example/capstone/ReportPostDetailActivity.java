package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class ReportPostDetailActivity extends AppCompatActivity {

    private TextView tv_reason, tv_postTitle;
    private EditText ed_moreInfo;
    private Button sendbtn;
    public static String reporterId, reportedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_post_detail);

        final SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        reporterId = sharedPreferences.getString("inputId", ""); // 신고자 id

        tv_reason = findViewById(R.id.reason);
        tv_postTitle = findViewById(R.id.postTitle);

        Intent intent = getIntent();
        reportedId = intent.getStringExtra("reportedId"); // 피신고자 id
        String reason = intent.getStringExtra("reason"); // 신고 사유
        String postTitle = intent.getStringExtra("postTitle"); // 게시물 제목
        String postId = intent.getStringExtra("postId"); // 게시물 id

        tv_reason.setText(reason);
        tv_postTitle.setText(postTitle);

        ed_moreInfo = findViewById(R.id.ed_moreInfo);

        sendbtn = findViewById(R.id.sendReport);
        sendbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String moreInfo = ed_moreInfo.getText().toString();
                final String reportedID = reportedId; // 피신고자 id
                final String reporterID = reporterId; // 신고자 id
                final String Reason = reason; // 신고 사유
                final String postIdNum = postId;
                final String reportID = Integer.toString(getRand());

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );

                            if(success) {

                                //성공 메시지 출력
                                Toast.makeText(getApplicationContext(), String.format("신고 완료하였습니다."),Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent( ReportPostDetailActivity.this, GetPostListActivity.class );
                                intent.putExtra("posterId", reportedID);
                                startActivity( intent );

                            } else {
                                // 실패 메시지 출력
                                Toast.makeText(getApplicationContext(), String.format("신고 실패하였습니다."),Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //서버로 Volley를 이용해서 로그인 처리 요청
                ReportPostRequest reportRequest = new ReportPostRequest( reportID, postIdNum, Reason, moreInfo ,  reporterID,  responseListener );
                RequestQueue queue = Volley.newRequestQueue( ReportPostDetailActivity.this );
                queue.add( reportRequest );

            }
        });

    }

    // 게시물 신고 id 8자리 난수
    public static int getRand(){
        Random rand = new Random();
        String rst = Integer.toString(rand.nextInt(8)+1);
        for(int i=0; i<7; i++){
            rst += Integer.toString(rand.nextInt(9));
        }
        return Integer.parseInt(rst);
    }
}