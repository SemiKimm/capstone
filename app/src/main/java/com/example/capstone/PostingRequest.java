package com.example.capstone;

//import com.android.volley.error.AuthFailureError;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
//import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PostingRequest extends StringRequest {
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://myapp.dothome.co.kr/Posting.php";
    private Map<String, String> map;
    public PostingRequest(String PostTitleData, String PostPlaceData
            , String PostDateData, String PostMoreInfoData, String PostColorData, String PostImgData,
                          Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("PostTitleData", PostTitleData);
        map.put("PostPlaceData", PostPlaceData);
        map.put("PostDateData", PostDateData);
        map.put("PostColorData", PostColorData);
        map.put("PostMoreInfoData", PostMoreInfoData);
        map.put("PostImgData", PostImgData);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
