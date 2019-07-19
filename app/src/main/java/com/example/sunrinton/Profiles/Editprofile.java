package com.example.sunrinton.Profiles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunrinton.MainActivity;
import com.example.sunrinton.R;
import com.example.sunrinton.Register;
import com.example.sunrinton.UserManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Editprofile extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText editemail, editlocate, editdivision,editpart;
    ImageButton apply;
    Map<String, Object> datas = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        editemail = findViewById(R.id.edit_email);
        editlocate = findViewById(R.id.edit_address);
        editpart = findViewById(R.id.edit_part);
        editdivision = findViewById(R.id.edit_belong);
        apply = findViewById(R.id.modified_clear);

        editemail.setText(UserManager.email);
        editlocate.setText(UserManager.locate == null ? null : UserManager.locate );
        editpart.setText(UserManager.part == null ? null : UserManager.part );
        editdivision.setText(UserManager.division == null ? null : UserManager.division );

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("password", UserManager.password);
                datas.put("name", UserManager.name);
                datas.put("key", UserManager.key);
                datas.put("email", editemail.getText().toString());
                UserManager.email = editemail.getText().toString();
                datas.put("adress", editlocate.getText().toString());
                UserManager.locate = editlocate.getText().toString();
                datas.put("part", editpart.getText().toString());
                UserManager.part = editpart.getText().toString();
                datas.put("belong", editdivision.getText().toString());
                UserManager.division = editdivision.getText().toString();
                DocumentReference docRef = db.collection("accounts").document(UserManager.email);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                                db.collection("accounts").document(UserManager.email)
                                        .set(datas)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                finish();
                                                Intent intent = new Intent(Editprofile.this, MainActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Editprofile.this, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                        }
                    }
                });
            }
        });
    }
}
