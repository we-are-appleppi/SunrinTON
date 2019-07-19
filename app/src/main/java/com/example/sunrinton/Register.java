package com.example.sunrinton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    String email, str_name, str_passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> datas = new HashMap<>();
        datas.put("name", str_name);
        datas.put("password", str_passwd);
        //datas.put("key", timestamp);

// Add a new document with a generated ID
        db.collection("accounts").document(email)
                .set(datas)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

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
