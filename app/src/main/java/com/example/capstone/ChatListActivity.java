package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ChatListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Chat> chatList;
    private String nickname,login_id;
    private List<ChatModel> chatModels = new ArrayList<>();
    private ArrayList<String> destinationUsers = new ArrayList<>();

    private String postId; // 게시물 number 임시
    private String posterId; // 게시물 작성자 id 임시
    private String CHAT_NAME;
    private ListView chat_list;

    private static final String TAG_POSTERID = "POSTERID";

    ArrayList<HashMap<String, String>> mArrayList;



    private DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        setTitle("채팅 목록");

        final SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        login_id = sharedPreferences.getString("inputId",""); // 로그인 id

        //CHAT_NAME = postId + "&" + posterId + "&" + nickname; // 채팅방 이름

        chat_list = (ListView) findViewById(R.id.chat_list);
        mArrayList = new ArrayList<>();

        /*
        // 리스트 어댑터 생성 및 세팅
        final ArrayAdapter<String> adapter

                = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        chat_list.setAdapter(adapter);

         */



        FirebaseDatabase database = FirebaseDatabase.getInstance();

        myRef = database.getReference();

        /*

        //데이터들을 추가, 변경, 제거, 이동, 취소
        myRef.child("chatrooms").orderByChild("userss/"+login_id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatModel chatModel = new ChatModel();

                //어댑터에 DTO추가
                //Chat chat = snapshot.getValue(Chat.class);
                //((ChatAdapter)adapter).addChat(chat);

                // child에 포함된 값 다 가지고 옴
                //String name = snapshot.getValue().toString();
                //adapter.add(name);

                adapter.add(snapshot.getKey());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


         */




        //myRef.child("chatrooms").orderByChild("users/"+login_id).equalTo(true).addValueEventListener(new ValueEventListener() {
        myRef.child("chatrooms").orderByChild("users/" +login_id).equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //adapter.clear();
                mArrayList.clear();
                //adapter.notify();
                for (DataSnapshot messageData : snapshot.getChildren()) {

                    String val1 = messageData.child("userss/sender").getValue().toString(); // sender 값 가져옴
                    String val2 = messageData.child("userss/geter").getValue().toString();  // geter 값 가져옴

                    if(!val1.equals(login_id)){ // login_id와 다르면
                        posterId = val1;  // posterId (상대방 id로 세팅)
                    } else {
                        posterId = val2;
                    }


                    HashMap<String, String> hashMap = new HashMap<>();

                    hashMap.put(TAG_POSTERID, posterId);


                    mArrayList.add(hashMap);


                    String msg = messageData.child("userss/").getValue().toString();


                    ListAdapter adapter = new SimpleAdapter(ChatListActivity.this, mArrayList, R.layout.chat_list_item,
                            new String[]{TAG_POSTERID},
                            new int[] {R.id.get_textView_list_title}
                    );
                    chat_list.setAdapter(adapter); // msg = {test=ture, 1234=true}


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        // 리스트뷰 클릭 시 해당 채팅방으로 넘어감
        chat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                HashMap<String, String> data = (HashMap<String, String>) adapterView.getItemAtPosition(position);

                //리스트 뷰 클릭 시 test, 1234 중에 현재 로그인 아이디와 일치 하지 않는 값을 intent로 넘겨 줌

                Intent intent = new Intent(ChatListActivity.this, MessageActivity.class);
                /* putExtra의 첫 값은 식별 태그, 뒤에는 다음 화면에 넘길 값 */
                // value : users 키 중에 로그인 아이디와 일치하지 않는 것 : 상대방 아이디 넘김
                intent.putExtra("posterId", data.get(TAG_POSTERID));



                startActivity(intent);
            }
        });
    }
}