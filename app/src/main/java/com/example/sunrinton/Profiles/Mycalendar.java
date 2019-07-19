package com.example.sunrinton.Profiles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.sunrinton.R;
import com.example.sunrinton.Register;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

public class Mycalendar extends AppCompatActivity implements OnDateSelectedListener {

    MaterialCalendarView calendarView;
    String day;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences spref = getSharedPreferences("Profile", MODE_PRIVATE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycalendar);
        calendarView=findViewById(R.id.my_calendar);
        calendarView.setOnDateChangedListener(this);
    }


    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
//        day=date.getDay()+"";
////        String email=spref.getString("email", "");
//        db.collection("accounts").document(email)
//                .update("selectedDay", day);

    }
}
