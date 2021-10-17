package com.example.capstone2;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SearchGetPostRequest extends StringRequest{
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://jamong.ivyro.net/SearchGetGetJson.php";
    private Map<String, String> map;
    public SearchGetPostRequest(String category, String color, String local, String place
            , String date1, String date2,
                                 Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("category", category);
        map.put("color", color);
        map.put("local", local);
        map.put("place", place);
        map.put("date1", date1);
        map.put("date2", date2);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
