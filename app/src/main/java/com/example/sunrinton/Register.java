package com.example.sunrinton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity {

    String str_email, str_name, str_passwd;
    EditText et_email, et_name, et_passwd;
    TextView start;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> datas = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_email = findViewById(R.id.et_email);
        et_name = findViewById(R.id.et_name);
        et_passwd = findViewById(R.id.et_password);
        start = findViewById(R.id.start);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_email = et_email.getText().toString();
                str_passwd = et_passwd.getText().toString();
                str_name = et_name.getText().toString();

                datas.put("name", str_name);
                datas.put("password", str_passwd);
                //datas.put("key", timestamp);

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
                    Toast.makeText(Register.this, "잘못된 이메일형식 입니다", Toast.LENGTH_SHORT).show();
                } else {
                    db.collection("accounts").document(str_email)
                            .set(datas)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Intent intent = new Intent();
                                    intent.putExtra("email", str_email);
                                    intent.putExtra("password", str_passwd);
                                    intent.putExtra("name", str_name);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Register.this, "오류가 발생하였습니다.", Toast.LENGTH_SHORT);
                                }
                            });
                }
            }
        });

    }

}
