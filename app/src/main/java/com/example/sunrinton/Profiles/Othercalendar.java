package com.example.sunrinton.Profiles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunrinton.LoginActiviy;
import com.example.sunrinton.MainActivity;
import com.example.sunrinton.R;
import com.example.sunrinton.Register;
import com.example.sunrinton.UserManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

public class Othercalendar extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressDialog asyncDialog;
    public String name;
    public String key;
    public String email;
    public String locate;
    public String part;
    public String division;

    TextView tvname, tvemail, tvlocate, tvpart, tvdivision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_othercalendar);

        Intent intent = getIntent();

        tvname = findViewById(R.id.othername);
        tvemail = findViewById(R.id.otheremail);
        tvlocate = findViewById(R.id.otheraddress);
        tvpart = findViewById(R.id.otherpart);
        tvdivision = findViewById(R.id.otherbelong);
        asyncDialog = new ProgressDialog(this);
        email = intent.getStringExtra("email");
        tvemail.setText(email);

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(Othercalendar.this, "잘못된 이메일형식 입니다", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        final DocumentReference docRef = db.collection("accounts").document(intent.getStringExtra("email"));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        String datas = String.valueOf(document.getData());
                        try {
                            JSONObject jsonObject = new JSONObject(datas);
                            Intent loginIntent = new Intent(Othercalendar.this, MainActivity.class);
                            tvname.setText(jsonObject.getString("name"));
                            try {
                                tvlocate.setText(jsonObject.getString("locate"));
                                tvpart.setText(jsonObject.getString("part"));
                                tvdivision.setText(jsonObject.getString("division"));
                            } catch(Exception e) {

                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Othercalendar.this, "계정정보가 올바르지 않습니다", Toast.LENGTH_SHORT).show();
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
