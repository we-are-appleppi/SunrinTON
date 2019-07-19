package com.example.sunrinton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Chatting_lobby extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mrefs = database.getReference("message");
    SharedPreferences prefs;
    TextView btn_chat;
    EditText et_chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_lobby);
        btn_chat = findViewById(R.id.chat_btn_send);
        et_chat = findViewById(R.id.chat_edt_message);



    }
}
