package com.example.capstone2;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class KeywordRequest extends StringRequest {
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://jamong.ivyro.net/Subscribe.php";
    private Map<String, String> map;

    public KeywordRequest(String user_keyword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("user_keyword", user_keyword);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
