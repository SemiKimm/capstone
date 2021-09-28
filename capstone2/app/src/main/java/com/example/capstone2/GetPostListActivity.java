package com.example.capstone2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class GetPostListActivity extends AppCompatActivity {
    private static String TAG = "getJsonList";

    private static final String TAG_JSON="getpostdata";
    private static final String TAG_GetPostTitle = "GetPostTitleData";
    private static final String TAG_GetPostCategory = "GetPostCategoryData";
    private static final String TAG_GetPostLocal = "GetPostLocalData";
    private static final String TAG_GetPostPlace = "GetPostPlaceData";
    private static final String TAG_GetPostDate ="GetPostDateData";
    private static final String TAG_GetPostColor ="GetPostColorData";
    private static final String TAG_GetPostMoreInfo ="GetPostMoreInfoData";
    private static final String TAG_GetPostImg ="GetPostImgData";

    private ImageView imgview;

    //private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_post_list);

        Button homeButton=findViewById(R.id.homeBtn);
        homeButton.setOnClickListener(view -> {
            Intent intent=new Intent(GetPostListActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Button postingButton=findViewById(R.id.postingBtn);
        postingButton.setOnClickListener(view -> {
            Intent intent=new Intent(GetPostListActivity.this, SelectPostingActivity.class);
            startActivity(intent);
        });

        //mTextViewResult = (TextView)findViewById(R.id.get_textView_main_result);
        mlistView = (ListView) findViewById(R.id.get_listView_main_list);
        mArrayList = new ArrayList<>();

        GetData task = new GetData();
        task.execute("http://jamong.ivyro.net/GetPostGetJson.php");

        //목록 눌러서 해당 게시글 데이터 받아서 보내기
        mlistView.setOnItemClickListener((AdapterView.OnItemClickListener) (adapterView, view, index, l) -> {
            HashMap<String,String> data =(HashMap<String,String>) adapterView.getItemAtPosition(index);
            Log.e("itemdata",String.valueOf(data));
            String title=data.get(TAG_GetPostTitle);
            String category=data.get(TAG_GetPostCategory);
            String local = data.get(TAG_GetPostLocal);
            String place = data.get(TAG_GetPostPlace);
            String color = data.get(TAG_GetPostColor);
            String date = data.get(TAG_GetPostDate);
            String moreInfo=data.get(TAG_GetPostMoreInfo);
            String imgUri=data.get(TAG_GetPostImg);
            Intent intent = new Intent(GetPostListActivity.this,GetPostActivity.class);
            intent.putExtra("title",title);
            intent.putExtra("category",category);
            intent.putExtra("local",local);
            intent.putExtra("place",place);
            intent.putExtra("color",color);
            intent.putExtra("date",date);
            intent.putExtra("moreInfo",moreInfo);
            intent.putExtra("imgUri",imgUri);
            startActivity(intent);
        });
    }


    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(GetPostListActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);

            if (result == null){
                //mTextViewResult.setText(errorString);
            }
            else {
                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);
                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();
            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }

        }
    }

    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){
                JSONObject item = jsonArray.getJSONObject(i);

                String GetPostTitleData = item.getString(TAG_GetPostTitle );
                String GetPostCategoryData = item.getString(TAG_GetPostCategory );
                String GetPostLocalData = item.getString(TAG_GetPostLocal );
                String GetPostPlaceData = item.getString(TAG_GetPostPlace );
                String GetPostDateData = item.getString(TAG_GetPostDate );
                String GetPostColorData = item.getString(TAG_GetPostColor );
                String GetPostMoreInfoData = item.getString(TAG_GetPostMoreInfo );
                String GetPostImgData = item.getString(TAG_GetPostImg );

                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_GetPostTitle, GetPostTitleData);
                hashMap.put(TAG_GetPostCategory, GetPostCategoryData);
                hashMap.put(TAG_GetPostLocal, GetPostLocalData);
                hashMap.put(TAG_GetPostPlace, GetPostPlaceData);
                hashMap.put(TAG_GetPostDate, GetPostDateData);
                hashMap.put(TAG_GetPostColor, GetPostColorData);
                hashMap.put(TAG_GetPostMoreInfo, GetPostMoreInfoData);
                hashMap.put(TAG_GetPostImg, GetPostImgData);
                mArrayList.add(hashMap);
            }
            ListAdapter adapter = new SimpleAdapter(
                    GetPostListActivity.this, mArrayList, R.layout.get_post_list_item,
                    new String[]{TAG_GetPostTitle ,TAG_GetPostCategory ,TAG_GetPostLocal, TAG_GetPostPlace , TAG_GetPostDate, TAG_GetPostColor,  TAG_GetPostMoreInfo, TAG_GetPostImg},
                    new int[]{R.id.get_textView_list_title, R.id.get_textView_list_category, R.id.get_textView_list_local, R.id.get_textView_list_place, R.id.get_textView_list_date, R.id.get_textView_list_color, R.id.get_textView_list_more_info, R.id.get_imgView_list}
            );
            ((SimpleAdapter) adapter).setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data, String textRepresentation) {
                    if(view.getId() == R.id.get_imgView_list) {
                        ImageView imageView = (ImageView) view;
                        String drawable = data.toString();
                        Glide.with(GetPostListActivity.this).load(drawable).override(200,200)
                                .error(R.drawable.defaultimg).into(imageView);
                        return true;
                    }
                    return false;
                }
            });
            mlistView.setAdapter(adapter);
        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }
}
