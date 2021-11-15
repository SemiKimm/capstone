package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EditProfile extends AppCompatActivity {

    private TextView ed_name, ed_id, ed_pwd;
    private Button editImg_button, editConfirm_button, withdrawal_button;
    private ImageView img;
    //private Uri urii;
    private String profile_img;
    String profile_img_lo;

    private String imgPath;
    private String imgFileName;

    String upLoadServerUri;
    int serverResponseCode = 0;

    private AlertDialog dialog;
    ProgressDialog Pdialog = null;

    // 사진 요청 코드
    private static final int REQUEST_CODE=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        final SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        upLoadServerUri = "http://myapp.dothome.co.kr/UploadToServer.php";//서버컴퓨터의 ip주소

        ed_name = findViewById(R.id.edit_name);
        ed_id = findViewById(R.id.edit_id);
        ed_pwd = findViewById(R.id.edit_pwd);

        Intent intent = getIntent();
        String user_name = intent.getStringExtra("user_name");
        String login_id = intent.getStringExtra("login_id");
        String login_pwd = intent.getStringExtra("login_pwd");
        profile_img = intent.getStringExtra("profile_img");

        ed_name.setText(user_name);
        ed_id.setText(login_id);
        ed_pwd.setText(login_pwd);

        // img입력 처리
        img=findViewById(R.id.user_profile);

        // 프로필 이미지 load
        Glide.with(getApplicationContext())
                .load(profile_img)
                .into(img);

        // 프로필 이미지 수정 버튼 클릭 시
        editImg_button = (Button) findViewById(R.id.edit_profileImg);
        // 갤러리에 요청코드 보내기
        editImg_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });



        // 수정 완료 버튼 클릭 시
        editConfirm_button = (Button) findViewById(R.id.edit_confirm);
        editConfirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = ed_id.getText().toString();
                final String pwd = ed_pwd.getText().toString();
                //final String imgLocation = profile_img; // 이미지 경로

                Pdialog = ProgressDialog.show(EditProfile.this, "", "Uploading file...", true);
                new Thread(new Runnable() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                //messageText.setText("uploading started.....");
                            }
                        });

                        //uploadFile(uploadFilePath + "" + uploadFileName);
                        uploadFile(imgPath);


                    }
                }).start();


                String imgLocation = "http://myapp.dothome.co.kr/uploads/" + imgFileName;

                //비밀번호 입력 안했을 경우
                if (pwd.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
                    dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                                if (success) { // 프로필 수정에 성공한 경우
                                    Toast.makeText(getApplicationContext(),"프로필 수정을 완료하였습니다.",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(EditProfile.this, MypageActivity.class);
                                    startActivity(intent);
                                } else { // 프로필 수정에 실패한 경우
                                    Toast.makeText(getApplicationContext(),"프로필 수정을 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                    return;
                                }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 Volley를 이용해서 프로필 수정 요청
                ProfileEditRequest profileEditRequest = new ProfileEditRequest( id, pwd, imgLocation, responseListener);
                RequestQueue queue = Volley.newRequestQueue( EditProfile.this );
                queue.add( profileEditRequest );
            }



        });

        // 회원탈퇴 버튼 클릭 시 수행
        withdrawal_button = (Button) findViewById(R.id.withdrawal);
        withdrawal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = ed_id.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                                if (success) { // 회원탈퇴에 성공한 경우
                                    Toast.makeText(getApplicationContext(),"회원탈퇴를 완료 하였습니다.",Toast.LENGTH_SHORT).show();

                                    // sharedpreference에 저장된 정보 삭제
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.remove("inputId");
                                    editor.remove("inputPwd");
                                    editor.putString(getResources().getString(R.string.prefAutoLoginState),"non-autoLogin");
                                    editor.putString(getResources().getString(R.string.prefLoginState),"loggedout");
                                    editor.apply();
                                    editor.commit();

                                    Intent intent = new Intent(EditProfile.this, MainActivity.class);
                                    startActivity(intent);
                                } else { // 회원탈퇴에 실패한 경우
                                    Toast.makeText(getApplicationContext(),"회원탈퇴를 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                    return;
                                }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };


                //서버로 Volley를 이용해서 회원탈퇴 요청
                DeleteUser deleteUser = new DeleteUser( id, responseListener);
                RequestQueue queue = Volley.newRequestQueue( EditProfile.this );
                queue.add( deleteUser );
            }

        });



    }

    public int uploadFile(String sourceFileUri) {

        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            Pdialog.dismiss();

            //Log.e("uploadFile", "Source File not exist :"
            //+uploadFilePath + "" + uploadFileName);


            runOnUiThread(new Runnable() {
                public void run() {
                    //messageText.setText("Source File not exist :"
                    //+ uploadFilePath + "" + uploadFileName);
                }
            });

            return 0;

        } else {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                //conn.setRequestProperty("uploaded_file", fileName);
                conn.setRequestProperty("uploaded_file", imgPath);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                //dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                //+ fileName + "\"" + lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + imgPath + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {

                    runOnUiThread(new Runnable() {
                        public void run() {

                            //String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                            //  + uploadFileName;

                            //messageText.setText(msg);
                            //Toast.makeText(MainActivity.this, "File Upload Complete.",
                            //Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                Pdialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        //messageText.setText("MalformedURLException Exception : check script url.");
                        // Toast.makeText(MainActivity.this, "MalformedURLException",
                        //Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                Pdialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        //messageText.setText("Got Exception : see logcat ");
                        //Toast.makeText(MainActivity.this, "Got Exception : see logcat ",
                        //Toast.LENGTH_SHORT).show();
                    }
                });
                //Log.e("Upload file to server Exception", "Exception : "
                //+ e.getMessage(), e);
            }
            Pdialog.dismiss();
            return serverResponseCode;

        } // End else block
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 10 :
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED) //사용자가 허가 했다면
                {
                    Toast.makeText(this, "외부 메모리 읽기/쓰기 사용 가능", Toast.LENGTH_SHORT).show();

                }else{//거부했다면
                    Toast.makeText(this, "외부 메모리 읽기/쓰기 제한", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    //갤러리에서 이미지 넣기
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            if(resultCode==RESULT_OK){
                try{
                    Uri uri=data.getData();
                    profile_img = uri.toString(); // uri string으로 변환

                    imgPath = getPathFromUri(uri);
                    imgFileName = getFileName(uri);

                    Log.e("realPath", imgPath);

                    //다이얼로그 이미지 사진에 넣기
                    Glide.with(getApplicationContext()).load(uri).into(img);
                }catch(Exception e){

                }
            }else if(resultCode == RESULT_CANCELED){
                //취소시 호출할 행동
            }
        }
    }

    // uri to realPath
    public String getPathFromUri(Uri uri){

        Cursor cursor = getContentResolver().query(uri, null, null, null, null );

        cursor.moveToNext();

        String path = cursor.getString( cursor.getColumnIndex( "_data" ) );

        cursor.close();

        return path;
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)); // display_name : string 형식ㅇ의 파일 이름. = file.getName()
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }


}