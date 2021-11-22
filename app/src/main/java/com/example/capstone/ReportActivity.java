package com.example.capstone;

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

import java.util.HashMap;

public class ReportActivity extends AppCompatActivity {

    private TextView tv_reportedId;
    public static String reporterId;

    static final String[] LIST_MENU = {"사유1", "사유2", "사유3", "사유4", "사유5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        final SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        tv_reportedId = findViewById(R.id.reportedId);

        Intent intent = getIntent();
        String reportedId = intent.getStringExtra("reported_id"); // 피신고자 id

        reporterId = sharedPreferences.getString("inputId", ""); // 신고자 id

        tv_reportedId.setText(reportedId);

        ArrayAdapter adapter = new ArrayAdapter(ReportActivity.this, R.layout.report_item, R.id.report_reason, LIST_MENU);

        ListView listView = (ListView) findViewById(R.id.report_reason_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                String data = ( String) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(ReportActivity.this, ReportDetailActivity.class);
                /* putExtra의 첫 값은 식별 태그, 뒤에는 다음 화면에 넘길 값 */
                intent.putExtra("reason", data);
                intent.putExtra("reportedId", reportedId);
                //intent.putExtra("reporterId", reporterId);
                startActivity(intent);
            }
        });
    }
}