package com.example.capstone;

//import com.android.volley.error.AuthFailureError;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
//import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ProfileEditRequest extends StringRequest {
    final static private String URL = "http://myapp.dothome.co.kr/editUserInfo.php";
    private Map<String, String> map;

    public ProfileEditRequest(String id, String pwd, String imgLocation, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("login_id", id);
        map.put("login_pwd", pwd);
        map.put("profile_img", imgLocation);


    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
