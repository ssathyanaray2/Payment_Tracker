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
    private int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR=0;
    boolean permission_granted=false;
    int startYear,startMonth,startDay, startHour, startMinut;
    long eventID;
    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,                           // 0
    };
    // The indices for the projection array above.
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
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.7),(int)(height*0.7));
        mAuth=FirebaseAuth.getInstance();
        namev= findViewById(R.id.name);
        descv = findViewById(R.id.desc);
        datev = findViewById(R.id.date);
        intervalv = findViewById(R.id.interval);
        add=findViewById(R.id.add);
        final String name= namev.getText().toString();
        final String desc= descv.getText().toString();
        final String date= datev.getText().toString();
        final String interval=intervalv.getText().toString();
        final String eveid=Long.toString(eventID);
        final int int_interval=Integer.parseInt(interval);
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
                HashMap<String,Object> map = new HashMap<>();
                map.put("name",name);
                map.put("description",desc);
                map.put("date",date);
                map.put("period",interval);
                map.put("eventid",eveid);
                FirebaseDatabase.getInstance().getReference().child(uid).push().setValue(map);
                Toast.makeText(popup.this,"reminder created",Toast.LENGTH_LONG).show();
                if(permission_granted) {
                    reminder(email,date,edate,name,desc,int_interval);
                }
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

    /*private void add_reminder(){
        String TAG="add_rem",u="add_reminder called",v="not called";
        if(permission_granted) {
                    reminder(startYear=2020,startMonth=4,startDay=5, startHour=10, startMinut=0);
                    Log.i(TAG,u);
                }
        else{
            Log.i(TAG,v);
        }
    }*/

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
    

    public void reminder(String mailid,String stdate,String endate,String title,String desc,int interval){
        long calid=userid(mailid);
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(startYear, startMonth, startDay, startHour, startMinut);
        long startMillis = beginTime.getTimeInMillis();

        ContentValues eventValues = new ContentValues();
        eventValues.put(CalendarContract.Events.CALENDAR_ID, calid);
        eventValues.put(CalendarContract.Events.TITLE, title);
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
