package com.example.capstone;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetPostingRequest extends StringRequest{
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://myapp.dothome.co.kr/GetPosting.php";
    private Map<String, String> map;
    public GetPostingRequest(String GetPostIdData, String GetPostTitleData, String GetPostPlaceData
            , String GetPostDateData, String GetPostMoreInfoData, String GetPostColorData, String GetPostImgData
            , String GetPostCategoryData, String GetPostLocalData, String GetPostUserIdData,
                             Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("GetPostIdData", GetPostIdData);
        map.put("GetPostTitleData", GetPostTitleData);
        map.put("GetPostPlaceData", GetPostPlaceData);
        map.put("GetPostDateData", GetPostDateData);
        map.put("GetPostMoreInfoData", GetPostMoreInfoData);
        map.put("GetPostColorData", GetPostColorData);
        map.put("GetPostImgData", GetPostImgData);
        map.put("GetPostCategoryData", GetPostCategoryData);
        map.put("GetPostLocalData", GetPostLocalData);
        map.put("GetPostUserIdData", GetPostUserIdData);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
