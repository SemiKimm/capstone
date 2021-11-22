package com.example.capstone;

import androidx.appcompat.app.AlertDialog;
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

public class ReportDetailActivity extends AppCompatActivity {

    private TextView tv_reason, tv_reportedId;
    private EditText ed_moreInfo;
    private Button sendbtn;
    public static String reporterId, reportedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);

        final SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        reporterId = sharedPreferences.getString("inputId", ""); // 신고자 id

        tv_reason = findViewById(R.id.reason);
        tv_reportedId = findViewById(R.id.reportedId);

        Intent intent = getIntent();
        reportedId = intent.getStringExtra("reportedId"); // 피신고자 id
        String reason = intent.getStringExtra("reason");

        tv_reason.setText(reason);
        tv_reportedId.setText(reportedId);

        ed_moreInfo = findViewById(R.id.ed_moreInfo);

        sendbtn = findViewById(R.id.sendReport);
        sendbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String moreInfo = ed_moreInfo.getText().toString();
                final String reportedID = reportedId;
                final String reporterID = reporterId;
                final String Reason = reason;
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
                                Intent intent = new Intent( ReportDetailActivity.this, MembersPageActivity.class );
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
                ReportRequest reportRequest = new ReportRequest( reportID, Reason, moreInfo ,reportedID, reporterID,  responseListener );
                RequestQueue queue = Volley.newRequestQueue( ReportDetailActivity.this );
                queue.add( reportRequest );

            }
        });


    }

    // 계정 신고 id 8자리 난수
    public static int getRand(){
        Random rand = new Random();
        String rst = Integer.toString(rand.nextInt(8)+1);
        for(int i=0; i<7; i++){
            rst += Integer.toString(rand.nextInt(9));
        }
        return Integer.parseInt(rst);
    }
}