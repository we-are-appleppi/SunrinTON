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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


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

                Random random = new Random();
                int key = 0;

                for(;;) {
                    key = random.nextInt(10000);
                    if(key > 1000)
                        break;
                }

                datas.put("name", str_name);
                datas.put("password", str_passwd);
                datas.put("key", Integer.toString(key));



                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
                    Toast.makeText(Register.this, "잘못된 이메일형식 입니다", Toast.LENGTH_SHORT).show();
                } else {
                    DocumentReference docRef = db.collection("accounts").document(str_email);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if(!document.exists()) {
                                    if(str_name.isEmpty() || str_name == null) {
                                        Toast.makeText(Register.this, "이름을 입력해 주세요", Toast.LENGTH_SHORT).show();
                                        return;
                                    }


                                    if(str_passwd.isEmpty() || str_passwd == null) {
                                        Toast.makeText(Register.this, "비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
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
                                                    Toast.makeText(Register.this, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    Toast.makeText(Register.this, "이미 등록된 계정입니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });

    }

}
