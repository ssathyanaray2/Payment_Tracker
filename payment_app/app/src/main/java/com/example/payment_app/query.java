package com.example.payment_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import java.util.Calendar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Build;
import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class query extends popup {
    public static final String[] INSTANCE_PROJECTION = new String[] {
            CalendarContract.Instances.EVENT_ID,      // 0
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    FirebaseUser user;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
    }


    // Specify the date range you want to search for recurring
// event instances
    public void qry(View view){
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        String email=user.getEmail().toString();
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2020, 4, 23, 8, 0);
        long startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2020, 6, 24, 8, 0);
        long endMillis = endTime.getTimeInMillis();

        ContentResolver cr = getContentResolver();

        // The ID of the recurring event whose instances you are searching
// for in the Instances table
    /*String selection = CalendarContract.Instances.EVENT_ID + " = ?";
    String[] selectionArgs = new String[] {"207"};*/

        // Construct the query with the desired date range.
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);
        long calid=userid(email);
        String selection = CalendarContract.Events.CALENDAR_ID + " = ?";
        String[] selectionArgs = new String[] {Long.toString(calid)};

// Submit the query
        Cursor eventCursor = cr.query(builder.build(),
                INSTANCE_PROJECTION, selection,
                selectionArgs,null);
        // For a full list of available columns see http://tinyurl.com/yfbg76w
        while (eventCursor.moveToNext()) {
            long eventID = 0;
            eventID = eventCursor.getLong(PROJECTION_ID_INDEX);
            Log.i("eventID : ", Long.toString(eventID));

        }

    }

    }


