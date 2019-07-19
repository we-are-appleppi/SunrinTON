package com.example.sunrinton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActiviy extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText idtext, passwordtext;
    String _email, _password, _name;
    String r_email, r_password;
    ProgressDialog asyncDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        idtext=findViewById(R.id.idText);
        passwordtext=findViewById(R.id.passwordText);
        TextView joinButton = findViewById(R.id.joinButton);
        TextView loginButton = findViewById(R.id.loginButton);
        asyncDialog = new ProgressDialog(this);

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
                Login(idtext.getText().toString(), passwordtext.getText().toString(), "");

            }
        });



    }
    void Login(final String email, final String password ,final String name){
        if (email =="" || password == ""){
            Toast.makeText(this, "이메일이나 비밀번호를 입력를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        else{
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                asyncDialog.setMessage("요청중입니다");
                asyncDialog.show();
                final DocumentReference docRef = db.collection("accounts").document(email);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()){
                                String datas = String.valueOf(document.getData());
                                try {
                                    JSONObject jsonObject = new JSONObject(datas);
                                    if(jsonObject.getString("password").equals(password)){
                                        if (name.equals("")){
                                            SaveProfileDatas(idtext.getText().toString(), passwordtext.getText().toString(),name);
                                        }
                                        else{
                                            SaveProfileDatas(email, password, name);
                                        }
                                        Intent loginIntent = new Intent(LoginActiviy.this,MainActivity.class);
                                        UserManager.email = email;
                                        UserManager.name = name;
                                        UserManager.key = jsonObject.getString("key");
                                        startActivity(loginIntent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(LoginActiviy.this, "잘못입력하셧습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else{
                                Toast.makeText(LoginActiviy.this, "계정정보가 올바르지 않습니다", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{
                            Log.e("DB", "failed with"+ task.getException());
                        }
                        asyncDialog.dismiss();
                    }
                });

            }
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== RESULT_OK){
            if (requestCode==123) {
                r_email = data.getStringExtra("email");
                r_password = data.getStringExtra("password");

                Login(r_email, r_password, _name);
            }
        }
    }
    private void SaveProfileDatas(String s, String toString, String name){
        SharedPreferences mprefs = getSharedPreferences("Profile", MODE_PRIVATE);
        SharedPreferences.Editor mEditer = mprefs.edit();
        mEditer.putString("s_name", _email);
        mEditer.putString("s_password", _password);
        mEditer.putString("s_name", _name);
        mEditer.putBoolean("AutoLogin", true);
    }
}
