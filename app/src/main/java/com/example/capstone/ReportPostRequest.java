package com.example.capstone;

import com.android.volley.error.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.request.StringRequest;
//import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReportPostRequest extends StringRequest {

    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://myapp.dothome.co.kr/ReportPost.php";
    private Map<String, String> map;

    public ReportPostRequest(String reportID, String postIdNum, String Reason, String moreInfo, String reporterID, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("reportID", reportID);
        map.put("postIdNum", postIdNum);
        map.put("Reason", Reason);
        map.put("moreInfo", moreInfo);
        map.put("reporterID", reporterID);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
