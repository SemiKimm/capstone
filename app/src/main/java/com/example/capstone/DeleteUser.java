package com.example.capstone;

import com.android.volley.error.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.request.StringRequest;
//import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteUser extends StringRequest {
    final static private String URL = "http://myapp.dothome.co.kr/deleteUser.php";
    private Map<String, String> map;

    public DeleteUser(String id, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("login_id", id);


    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
