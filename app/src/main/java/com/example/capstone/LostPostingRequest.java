package com.example.capstone;

import com.android.volley.error.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.request.StringRequest;
//import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LostPostingRequest extends StringRequest {
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://myapp.dothome.co.kr/LostPosting.php";
    private Map<String, String> map;
    public LostPostingRequest(String LostPostTitleData, String LostPostPlaceData
            , String LostPostDateData, String LostPostMoreInfoData, String LostPostColorData, String LostPostImgData,
                              Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("LostPostTitleData", LostPostTitleData);
        map.put("LostPostPlaceData", LostPostPlaceData);
        map.put("LostPostDateData", LostPostDateData);
        map.put("LostPostMoreInfoData", LostPostMoreInfoData);
        map.put("LostPostColorData", LostPostColorData);
        map.put("LostPostImgData", LostPostImgData);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}