package com.example.capstone;

import com.android.volley.error.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.request.StringRequest;
//import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://myapp.dothome.co.kr/Login.php";
    private Map<String, String> map;

    public LoginRequest(String login_id, String login_pwd, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("login_id", login_id);
        map.put("login_pwd", login_pwd);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}

