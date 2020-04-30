package com.example.payment_app;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.TimeZone;

public class popup extends AppCompatActivity {

    private EditText namev;
    private EditText datev;
    private EditText descv;
    private EditText intervalv;
    private int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR=0;
    boolean permission_granted=false;
    int startYear,startMonth,startDay, startHour, startMinut;
    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,                           // 0
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;

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
        if (ContextCompat.checkSelfPermission(popup.this,
                Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {

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



    private void add_reminder(){
        String TAG="add_rem",u="add_reminder called",v="not called";
        if(permission_granted) {
            Log.i(TAG,u);
            reminder(startYear=2020,startMonth=4,startDay=5, startHour=10, startMinut=0);
        }
        else{
            Log.i(TAG,v);
        }
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
    

    public void reminder(int startYear, int startMonth, int startDay, int startHour, int startMinut){
        String mailid="sindhura.l.bhat@gmail.com";
        long calid=userid(mailid);
        long eventID;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(startYear, startMonth, startDay, startHour, startMinut);
        long startMillis = beginTime.getTimeInMillis();

        String name= namev.getText().toString();
        String desc= descv.getText().toString();
        String date= datev.getText().toString();
        String temp=intervalv.getText().toString();
        int interval=Integer.parseInt(temp);

        ContentValues eventValues = new ContentValues();
        eventValues.put(CalendarContract.Events.CALENDAR_ID, calid);
        eventValues.put(CalendarContract.Events.TITLE, name);
        eventValues.put(CalendarContract.Events.DESCRIPTION, desc);
        eventValues.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Calcutta");
        eventValues.put(CalendarContract.Events.DTSTART, startMillis);
        eventValues.put(CalendarContract.Events.DURATION, "PT3H");
        eventValues.put(CalendarContract.Events.HAS_ALARM, 1);
        eventValues.put(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=5;INTERVAL=1");
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
