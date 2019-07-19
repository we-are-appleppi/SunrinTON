package com.example.sunrinton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

public class ChattingLobbyActivity extends AppCompatActivity {
    SharedPreferences prefs;

    RecyclerView rcv;
    Chatting_Lobby_RecyclerAdapter madapter;

    ArrayList<Chat_Lobby> items;
    String email, email_exept_dot;
    String name;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("chat");

    LinearLayout LL;

    JSONArray tmpArray;
    JSONObject tmpObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_lobby);
        rcv = findViewById(R.id.chattingLobby_rcv);
        LL = findViewById(R.id.chatting_lobby_tab);

        items = new ArrayList<>();

        prefs = getSharedPreferences("Profile_Data", MODE_PRIVATE);
        name = prefs.getString("S_name", "");
        email = prefs.getString("S_email", "");
        email_exept_dot = email.split("\\.")[0] + email.split("\\.")[1];

        Date time = new Date(System.currentTimeMillis());
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyyMMdd");
        String date = sdfd.format(time);

        madapter = new Chatting_Lobby_RecyclerAdapter(items, date);
        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcv.setAdapter(madapter);

        showChatList();
    }

    String recent_date;
    String recent_chat;
    String recent_time;
    String chatname;

    private void showChatList() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                chatname = dataSnapshot.getKey();

                if (chatname.split(",")[0].equals(email_exept_dot) || chatname.split(",")[1].equals(email_exept_dot)) {//나의 채팅방인가?
                    String op_name;
                    if (dataSnapshot.child("chatLog").getValue() != null) {
                        HashMap<String, String> b = (HashMap<String, String>) dataSnapshot.child("chatLog").getValue();
                        ArrayList<Chat> c = new ArrayList<>();
                        HashMap<String, String> a;
                        for (int i = 0; i < b.values().toArray().length; i++) {
                            a = (HashMap<String, String>) b.values().toArray()[i];
                            c.add(new Chat(a.get("message"), a.get("sender"),false));
                        }
                        recent_chat = c.get(0).getMessage();
                    }

                    if (dataSnapshot.child("name1").exists() && dataSnapshot.child("name2").exists()) {
                        if (dataSnapshot.child("name1").getValue().equals(name)) {//상대 이름은?
                            op_name = (String) dataSnapshot.child("name2").getValue();
                        } else {
                            op_name = (String) dataSnapshot.child("name1").getValue();
                        }
                        if (dataSnapshot.child("chatLog").exists()) {
                            items.add(new Chat_Lobby(dataSnapshot.getKey(), op_name, recent_chat));//채팅방 이름을 상대 이름으로
                        }
                    }
                    madapter.notifyItemInserted(items.size() - 1);
                }
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 7655:
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    break;
            }
        }
    }
}