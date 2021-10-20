package com.example.capstone2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ReportPostActivity extends AppCompatActivity {

    private TextView tv_postTitle;
    public static String reporterId;

    static final String[] LIST_MENU = {"사유1", "사유2", "사유3", "사유4", "사유5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_post);

        final SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        tv_postTitle = findViewById(R.id.postTitle);

        Intent intent = getIntent();
        String reportedId = intent.getStringExtra("posterId"); // 피신고자 id (게시물 작성자 id)
        String postId = intent.getStringExtra("postId"); // 게시물 id
        String postTitle = intent.getStringExtra("postTitle"); // 게시물 제목

        reporterId = sharedPreferences.getString("inputId", ""); // 신고자 id

        tv_postTitle.setText(postTitle);

        ArrayAdapter adapter = new ArrayAdapter(ReportPostActivity.this, R.layout.report_item, R.id.report_reason, LIST_MENU);

        ListView listView = (ListView) findViewById(R.id.report_reason_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                String data = ( String) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(ReportPostActivity.this, ReportPostDetailActivity.class);
                /* putExtra의 첫 값은 식별 태그, 뒤에는 다음 화면에 넘길 값 */
                intent.putExtra("reason", data); // 신고 사유
                intent.putExtra("reportedId", reportedId); // 피신고자 id (게시물 작성자 id)
                intent.putExtra("postId", postId); // 게시물 id
                intent.putExtra("postTitle", postTitle); // 게시물 제목
                startActivity(intent);
            }
        });
    }
}