package com.example.capstone;

import com.android.volley.error.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.request.StringRequest;
//import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://myapp.dothome.co.kr/Register.php";
    private Map<String, String> map;
    //private Map<String, String>parameters;

    public RegisterRequest(String login_id, String login_pwd, String user_name, String user_phone, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("login_id", login_id);
        map.put("login_pwd", login_pwd);
        map.put("user_name", user_name);
        map.put("user_phone", user_phone);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}