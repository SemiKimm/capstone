package com.example.capstone;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ProfileEditRequest extends StringRequest {
    final static private String URL = "http://myapp.dothome.co.kr/.php";
    private Map<String, String> map;

    public ProfileEditRequest(String ProfileImg, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("ProfileImg", ProfileImg);


    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
