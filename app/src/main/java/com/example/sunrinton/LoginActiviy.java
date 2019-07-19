package com.example.sunrinton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActiviy extends AppCompatActivity {

    EditText idtext, passwordtext;
    String email, password;
    String r_email, r_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        idtext=findViewById(R.id.idText);
        passwordtext=findViewById(R.id.passwordText);
        TextView joinButton = findViewById(R.id.joinButton);
        TextView loginButton = findViewById(R.id.loginButton);

        SharedPreferences mprefs = getSharedPreferences("Profile", MODE_PRIVATE);
        Boolean Auto_login =mprefs.getBoolean("AutoLogin", false);
        if (Auto_login){
            Intent intent = new Intent(LoginActiviy.this, MainActivity.class);
            startActivityForResult(intent, 123);
            finish();

        }




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
    void login(String id, String password){

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== RESULT_OK){
            if (requestCode==123){
                r_email = data.getStringExtra("email");
                r_password = data.getStringExtra("password");
            }
        }
    }
    private void SaveProfileDatas(){
        SharedPreferences mprefs = getSharedPreferences("Profile", MODE_PRIVATE);
        SharedPreferences.Editor mEditer = mprefs.edit();
        mEditer.putString("s_name", email);
        mEditer.putString("s_password", password);
        mEditer.putBoolean("AutoLogin", true);
    }
}
