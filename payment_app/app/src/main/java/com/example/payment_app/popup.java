package com.example.payment_app;
import java.util.HashMap;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.util.Calendar;
import java.util.TimeZone;

public class popup extends AppCompatActivity {

    private EditText namev;
    private EditText datev;
    private EditText descv;
    private EditText intervalv;
    private EditText endd;
    RadioGroup radiogrp;
    RadioButton radiobutton;

    private int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR=0;
    boolean permission_granted=false;
    int startHour=10, startMinut=0;
    long eventID;
    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,
    };
    private static final int PROJECTION_ID_INDEX = 0;
    private Button add;
    FirebaseUser user;
    private FirebaseAuth mAuth;
    DatabaseReference mRef;
    FirebaseDatabase database;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_popup);
        mAuth=FirebaseAuth.getInstance();
        namev= findViewById(R.id.name);
        descv = findViewById(R.id.desc);
        datev = findViewById(R.id.date);
        intervalv = findViewById(R.id.interval);
        endd=findViewById(R.id.enddate);
        add=findViewById(R.id.add);
        radiogrp=findViewById(R.id.radioGroup);

        user=mAuth.getCurrentUser();
        final String uid=user.getUid().toString();
        final String email=user.getEmail().toString();

        if (ContextCompat.checkSelfPermission(popup.this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(popup.this,
                    Manifest.permission.WRITE_CALENDAR)) {
                Toast.makeText(popup.this, "Permission required to set reminder", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(popup.this,
                        new String[]{Manifest.permission.WRITE_CALENDAR},
                        MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);


            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(popup.this,
                        new String[]{Manifest.permission.WRITE_CALENDAR},
                        MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);

                // MY_PERMISSIONS_REQUEST_WRITE_CALENDAR is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else{
            permission_granted= true;
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String name= namev.getText().toString();
                 String desc= descv.getText().toString();
                 String date= datev.getText().toString();
                 String endda=endd.getText().toString();
                 String interval=intervalv.getText().toString();

                /* int int_interval;
                try{
                    int_interval=Integer.parseInt(interval);
                }
                catch(NumberFormatException e){
                    int_interval=0;
                }*/
                int radioId=radiogrp.getCheckedRadioButtonId();
                int radionum=1;
                switch (radioId){
                    case R.id.day:
                        radionum=1;
                        break;
                    case R.id.month:
                        radionum=2;
                        break;
                    case R.id.year:
                        radionum=3;
                        break;
                }
                if(permission_granted) {
                    reminder(email,date,endda,name,desc,interval,radionum);
                }




                String eveid=Long.toString(eventID);
                HashMap<String,Object> map = new HashMap<>();
                map.put("name",name);
                map.put("description",desc);
                map.put("date",date);
                map.put("period",interval);
                map.put("eventid",eveid);
                map.put("End date",endda);
                FirebaseDatabase.getInstance().getReference().child(uid).push().setValue(map);

                startActivity(new Intent(popup.this, reminder.class));
                
            }
            });
        
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if(requestCode==MY_PERMISSIONS_REQUEST_WRITE_CALENDAR) {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    permission_granted= true;

                } else {
                    Toast.makeText(popup.this, "Permission required to set reminder", Toast.LENGTH_LONG).show();
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
            }
    }
    int[] strtodmy(String str){
        char[] chars = str.toCharArray();
        int[] dmy = new int[3];
        int len=str.length();
        if(len==10) {
            String day = Character.toString(chars[0]).concat(Character.toString(chars[1]));
            dmy[0] = Integer.parseInt(day);
            String month = Character.toString(chars[3]).concat(Character.toString(chars[4]));
            dmy[1] = Integer.parseInt(month);
            String year = Character.toString(chars[6]).concat(Character.toString(chars[7])).concat(Character.toString(chars[8])).concat(Character.toString(chars[9]));
            dmy[2] = Integer.parseInt(year);
        }
        return dmy;
    }

    public long userid(String mailid){
        long calID = 0;
        Cursor cur = null;
        ContentResolver cr = getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[] {mailid, mailid};
        cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
        while (cur.moveToNext()) {
            calID = cur.getLong(PROJECTION_ID_INDEX);
        }
        return calID;
    }
    public String addzero(int n){
        String str;
        if(n<10){
            str="0"+n;
        }
        else{
            str=Integer.toString(n);
        }
        return str;
    }

    public void reminder(String mailid,String stdate,String endate,String title,String desc,String interval,int radio){
        String rrule="";
        long calid=userid(mailid);
        int[] sta = strtodmy(stdate);
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(sta[2],(sta[1]-1),sta[0], startHour, startMinut);
        long startMillis = beginTime.getTimeInMillis();
        if(endate.length()>0){
            int[] end = strtodmy(endate);
            switch(radio){
                case 1:
                    //int eday=end[0]+1
                    rrule="FREQ=DAILY;INTERVAL="+interval+";UNTIL="+addzero(end[2])+addzero(end[1])+addzero(end[0]);
                    break;
                case 2:
                    rrule="FREQ=MONTHLY;BYMONTHDAY="+addzero(sta[0])+";INTERVAL="+interval+";UNTIL="+addzero(end[2])+addzero(end[1])+addzero(end[0]);
                    break;
                case 3:
                    rrule="FREQ=YEARLY;BYMONTH="+addzero(sta[1])+";BYMONTHDAY="+addzero(sta[0])+";INTERVAL="+interval+";UNTIL="+addzero(end[2])+addzero(end[1])+addzero(end[0]);

            }
        }
        else{
            switch(radio){
                case 1:
                    rrule="FREQ=DAILY;INTERVAL="+interval;
                    break;
                case 2:
                    rrule="FREQ=MONTHLY;BYMONTHDAY="+addzero(sta[0])+";INTERVAL="+interval;
                    break;
                case 3:
                    rrule="FREQ=YEARLY;BYMONTH="+addzero(sta[1])+";BYMONTHDAY="+addzero(sta[0])+";INTERVAL="+interval;

            }
        }

        ContentValues eventValues = new ContentValues();
        eventValues.put(CalendarContract.Events.CALENDAR_ID, calid);
        eventValues.put(CalendarContract.Events.TITLE, title);
        eventValues.put(CalendarContract.Events.DESCRIPTION, desc);
        eventValues.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Calcutta");
        eventValues.put(CalendarContract.Events.DTSTART, startMillis);
        eventValues.put(CalendarContract.Events.DURATION, "PT3H");
        eventValues.put(CalendarContract.Events.HAS_ALARM, 1);
        eventValues.put(CalendarContract.Events.RRULE, rrule);
        eventValues.put("eventStatus", 1);

        ContentResolver cr = getContentResolver();
        Uri eventUri = cr.insert(CalendarContract.Events.CONTENT_URI, eventValues);
        eventID = Long.parseLong(eventUri.getLastPathSegment());
        ContentResolver remcr = getContentResolver();
        ContentValues remindervalues = new ContentValues();
        remindervalues.put(CalendarContract.Reminders.MINUTES, 5);
        remindervalues.put(CalendarContract.Reminders.EVENT_ID, eventID);
        remindervalues.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        Uri reminderuri = remcr.insert(CalendarContract.Reminders.CONTENT_URI, remindervalues);

        Toast.makeText(popup.this, "reminder has been set", Toast.LENGTH_LONG).show();

    }

}
