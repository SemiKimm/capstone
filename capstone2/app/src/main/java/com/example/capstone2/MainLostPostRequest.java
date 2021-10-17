package com.example.capstone2;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MainLostPostRequest extends StringRequest{
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://jamong.ivyro.net/MainLostPostGetJson.php";
    private Map<String, String> map;
    public MainLostPostRequest(String local,
                              Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("local", local);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
