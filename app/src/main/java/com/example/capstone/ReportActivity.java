package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ReportActivity extends AppCompatActivity {

    private TextView tv_reportedId;

    static final String[] LIST_MENU = {"사유1", "사유2", "사유3", "사유4", "사유5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        tv_reportedId = findViewById(R.id.reportedId);

        Intent intent = getIntent();
        String reportedId = intent.getStringExtra("reported_id");

        tv_reportedId.setText(reportedId);

        ArrayAdapter adapter = new ArrayAdapter(ReportActivity.this, R.layout.report_item, R.id.report_reason, LIST_MENU);

        ListView listView = (ListView) findViewById(R.id.report_reason_list);
        listView.setAdapter(adapter);
    }
}