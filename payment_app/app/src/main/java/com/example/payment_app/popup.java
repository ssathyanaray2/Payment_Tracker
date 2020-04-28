package com.example.payment_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

public class popup extends AppCompatActivity {

    private EditText namev;
    private EditText datev;
    private EditText descv;
    private EditText intervalv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_popup);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.7),(int)(height*0.7));
        namev= findViewById(R.id.name);
        descv = findViewById(R.id.desc);
        datev = findViewById(R.id.date);
        intervalv = findViewById(R.id.interval);
        Button add=findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_reminder();
            }
            });



    }

    private void add_reminder(){
        // If you want the start times to show up, you have to set them
        Calendar calendar = Calendar.getInstance();

        // Here we set a start time of Tuesday the 17th, 6pm
        calendar.set(2015, Calendar.MARCH, 17, 18, 0, 0);
        calendar.setTimeZone(TimeZone.getDefault());

        long start = calendar.getTimeInMillis();
        // add three hours in milliseconds to get end time of 9pm
        long end = calendar.getTimeInMillis() + 3 * 60 * 60 * 1000;

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .setType("vnd.android.cursor.item/event")
                .putExtra(CalendarContract.Events.TITLE, "Tuesdays")
                .putExtra(CalendarContract.Events.DESCRIPTION, "Tuesday Specials")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Lixious Bench")
                .putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY;BYDAY=TU;UNTIL=20150428")

                // to specify start time use "beginTime" instead of "dtstart"
                //.putExtra(Events.DTSTART, calendar.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, start)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end)

                // if you want to go from 6pm to 9pm, don't specify all day
                //.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
                .putExtra(CalendarContract.Events.HAS_ALARM, 1)
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

        startActivity(intent);

    }



    /*String name= namev.getText().toString();
        String desc= descv.getText().toString();
        String date= datev.getText().toString();
        String temp=intervalv.getText().toString();
        int interval=Integer.parseInt(temp);
        interval+=2;
        String u=String.valueOf(interval);
        String t=name+desc+date+u;
        Toast.makeText(popup.this,t,Toast.LENGTH_LONG).show(); */

}
