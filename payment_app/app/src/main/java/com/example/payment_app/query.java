package com.example.payment_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import java.util.Calendar;
import java.util.HashMap;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Build;
import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class query extends popup {
    public static final String[] INSTANCE_PROJECTION = new String[] {
            CalendarContract.Instances.EVENT_ID,      // 0
    };

    private static final int PROJECTION_ID_INDEX = 0;
    FirebaseUser user;
    HashMap<Long, Integer> map = new HashMap<>();
    Double balance=0.0;
    Double income=0.0;
    Double expense=0.0;
    private EditText sdatev;
    private EditText edatev;
    private EditText balancev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
    }

    public void getevents(){
        user=FirebaseAuth.getInstance().getCurrentUser();
        String email=user.getEmail().toString();
        sdatev=findViewById(R.id.startd);
        edatev=findViewById(R.id.endd);
        String sdate=sdatev.getText().toString();
        String edate=edatev.getText().toString();
        int[] sdt = strtodmy(sdate);
        int[] edt = strtodmy(edate);
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(sdt[2], sdt[1], sdt[0], 8, 0);
        long startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(edt[2], edt[1], edt[0], 12, 0);
        long endMillis = endTime.getTimeInMillis();

        ContentResolver cr = getContentResolver();

        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);
        long calid=userid(email);
        String selection = CalendarContract.Events.CALENDAR_ID + " = ?";
        String[] selectionArgs = new String[] {Long.toString(calid)};

        Cursor eventCursor = cr.query(builder.build(),
                INSTANCE_PROJECTION, selection,
                selectionArgs,null);
        int i;
        while (eventCursor.moveToNext()) {
            long eventID;
            eventID = eventCursor.getLong(PROJECTION_ID_INDEX);

            if(map.containsKey(eventID)){
                i=map.get(eventID)+1;
                map.put(eventID,i);
            }
            else{
                map.put(eventID,1);
            }
        }


    }

    public void qry(View view){
        getevents();
        balancev=findViewById(R.id.balance);
        balance=Double.parseDouble(balancev.getText().toString());
        user=FirebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid().toString();
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference().child(uid);
        mRef.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot datasnapshot) {
                adapter amt;
                for(DataSnapshot ds: datasnapshot.getChildren()) {
                    amt = ds.getValue(adapter.class);
                    if(map.containsKey(amt.getEventid())){
                        balance+=(amt.getAmount()*amt.getCrdr()*map.get(amt.getEventid()));
                        if(amt.getCrdr()==1){
                            income+=amt.getAmount()*map.get(amt.getEventid());
                        }
                        else{
                            expense+=amt.getAmount()*map.get(amt.getEventid());
                        }
                    }
                }
                String res="Total income :"+income+"\n"+"Total Expenditure: "+expense+"\n"+"Remaining balance: "+balance+"\n";
                TextView result=findViewById(R.id.results);
                result.setText(res);
            }

            public void onCancelled(DatabaseError dataerr) {
                Toast.makeText(query.this, "No events in given time range", Toast.LENGTH_LONG).show();
            }
        });
    }
    }


