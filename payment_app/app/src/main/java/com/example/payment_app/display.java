package com.example.payment_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ImageView;
import android.graphics.Bitmap;
import java.io.File;
import java.io.FileOutputStream;
import android.graphics.BitmapFactory;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import android.content.Intent;
import android.content.ContentResolver;
import android.net.Uri;
import android.content.ContentUris;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class display extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference mRef;
    FirebaseUser user;
    adapter detail;
    ImageView img;
    long event_id;
    final String key = getIntent().getStringExtra("key");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        database = FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        final String uid=user.getUid().toString();
        mRef = database.getReference().child(uid).child(key);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                detail = datasnapshot.getValue(adapter.class);
                String repeat;
                String s=detail.getDmy();
                String str;
                String imgstring=detail.getImg();
                if("No repetition".equals(s)) {
                    repeat = s;
                    //str="Name: "+detail.getName()+"\n"+"Description: "+detail.getDescription()+"\n"+"Start date: "+detail.getDate()+"\n"+"End Date: "+detail.getEdate()+"\n";
                }
                else{
                        repeat="Repeats every "+detail.getPeriod()+" "+s;
                }
                str="Description: "+detail.getDescription()+"\n"+"Start date: "+detail.getDate()+"\n"+"End Date: "+detail.getEdate()+"\n"+repeat+"\n"+detail.getAmount();
                int i=detail.getCrdr();
                if(i==1){
                    str=str+"(+)";
                }
                else{
                    str=str+"(-)";

                }
                event_id=detail.getEventid();
                String name=detail.getName();
                TextView title=findViewById(R.id.title);
                TextView t=findViewById(R.id.details);
                img=findViewById(R.id.imageView2);
                title.setText(name);
                t.setText(str);
                    try {
                        byte[] decodedString = Base64.decode(imgstring, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        img.setImageBitmap(decodedByte);
                    }
                    catch(IllegalArgumentException iae){
                        Toast.makeText(display.this,"no image",Toast.LENGTH_LONG).show();
                    }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(display.this, "Value not displayed", Toast.LENGTH_LONG).show();

            }

        });

    }
    public void back(View view){
        Intent intent = new Intent(this, reminder.class);
        startActivity(intent);
    }

    public void deleteeve(long eveid){

        ContentResolver cr = getContentResolver();
        Uri deleteUri = null;
        deleteUri = ContentUris.withAppendedId(Events.CONTENT_URI, eveid);
        int rows = cr.delete(deleteUri, null, null);
        database = FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        String uid=user.getUid().toString();
        mRef = database.getReference().child(uid).child(key);
        mRef.setValue(null);
    }
    public void delete(View view){
        deleteeve(event_id);
        Intent intent = new Intent(this, reminder.class);
        startActivity(intent);
    }
}
/*
addOnCompleteListener(new OnCompleteListener<Void>(){
            public void onComplete(Task<Void> task){

            }
        });
 */