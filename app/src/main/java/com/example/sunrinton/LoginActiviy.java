package com.example.sunrinton;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActiviy extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView joinButton = findViewById(R.id.joinButton);
        TextView loginButton = findViewById(R.id.loginButton);

        joinButton.setPaintFlags(joinButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent joinIntent = new Intent(LoginActiviy.this,Register.class);
                startActivity(joinIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(LoginActiviy.this,MainActivity.class);
                startActivity(loginIntent);
            }
        });

    }
}
