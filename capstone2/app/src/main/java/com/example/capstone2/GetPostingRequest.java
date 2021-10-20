package com.example.capstone2;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetPostingRequest extends StringRequest{
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://jamong.ivyro.net/GetPosting.php";
    private Map<String, String> map;
    public GetPostingRequest(String GetPostIdData, String GetPostTitleData, String GetPostCategoryData, String GetPostLocalData, String GetPostPlaceData
            , String GetPostDateData, String GetPostMoreInfoData, String GetPostColorData, String GetPostImgData
            , String GetPostUserIdData, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("GetPostIdData", GetPostIdData);
        map.put("GetPostTitleData", GetPostTitleData);
        map.put("GetPostCategoryData", GetPostCategoryData);
        map.put("GetPostLocalData", GetPostLocalData);
        map.put("GetPostPlaceData", GetPostPlaceData);
        map.put("GetPostDateData", GetPostDateData);
        map.put("GetPostMoreInfoData", GetPostMoreInfoData);
        map.put("GetPostColorData", GetPostColorData);
        map.put("GetPostImgData", GetPostImgData);
        map.put("GetPostUserIdData", GetPostUserIdData);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
