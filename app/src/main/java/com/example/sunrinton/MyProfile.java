package com.example.sunrinton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.sunrinton.UserManager;

public class MyProfile extends AppCompatActivity {

    TextView name, email, key;
    Button resetbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        name = findViewById(R.id.myprofile_name);
        email = findViewById(R.id.myprofile_email);
        key = findViewById(R.id.myprofile_key);
        resetbtn = findViewById(R.id.myprofile_resetbtn);

        name.setText(UserManager.name);
        key.setText(UserManager.key);
        email.setText(UserManager.email);
    }
}
