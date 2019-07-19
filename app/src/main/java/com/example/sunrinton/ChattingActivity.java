package com.example.sunrinton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChattingActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("chat");

    SharedPreferences prefs;

    EditText edt_message;
    String name, email;

    ArrayList<Chat> items;
    ChatAdapter adapter;
    RecyclerView rcv;

    TextView who, send;
    String op_name;
    String Current_chatName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        send = findViewById(R.id.chat_btn_send);
        edt_message = findViewById(R.id.chat_edt_message);
        rcv = findViewById(R.id.chat_speech_bubble_recyclerview);
        who=findViewById(R.id.chat_tv_who);

        prefs = getSharedPreferences("Profile_Data", MODE_PRIVATE);
        email = prefs.getString("S_email", "");
        name = prefs.getString("S_name", "");
        items = new ArrayList<>();

        Intent intent = getIntent();
        Current_chatName=intent.getStringExtra("ChatName");
        op_name=intent.getStringExtra("opname");
        who.setText(op_name);

        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new ChatAdapter(items, name);
        rcv.setAdapter(adapter);

        openChat(Current_chatName);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edt_message.getText().toString().equals("")) {
                    Date time = new Date(System.currentTimeMillis());
                    SimpleDateFormat sdf = new SimpleDateFormat("a hh:mmss");
                    SimpleDateFormat sdfd = new SimpleDateFormat("yyyyMMdd");
                    String current_time = sdf.format(time);
                    String date = sdfd.format(time);

                    Chat chatData = new Chat(edt_message.getText().toString(), name,false);  // 유저 이름과 메세지로 chatData 만들기
                    databaseReference.child(Current_chatName).child("chatLog").push().setValue(chatData);  // 기본 database 하위 message라는 child에 chatData를 list로 만들기

                    edt_message.setText("");
                }
            }
        });
    }

    private void openChat(String chatName) {
        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child(chatName).child("chatLog").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Chat chat = dataSnapshot.getValue(Chat.class);
                if(items.size()>1) {
                    if (chat.getSender().equals(items.get(items.size() - 1).getSender())) {
                        items.get(items.size() - 1).setPrev(true);
                        adapter.notifyDataSetChanged();
                    }
                }
                items.add(chat);
                rcv.scrollToPosition(items.size() - 1);
                adapter.notifyItemInserted(items.size() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}