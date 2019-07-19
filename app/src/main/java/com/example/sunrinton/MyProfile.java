package com.example.sunrinton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

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

        MaterialCalendarView materialCalendarView = findViewById(R.id.calendarView);

        materialCalendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return false;
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new DotSpan(5, R.color.colorRed));
            }

        });

    }
}
