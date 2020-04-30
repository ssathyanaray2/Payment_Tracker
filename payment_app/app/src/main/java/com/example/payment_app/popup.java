package com.example.payment_app;
import java.util.HashMap;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        user=mAuth.getCurrentUser();
        final String uid=user.getUid().toString();
        final String email=user.getEmail().toString();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String,Object> map = new HashMap<>();
                map.put("name",namev.getText().toString());
                map.put("description",descv.getText().toString());
                map.put("date",datev.getText().toString());
                map.put("period",intervalv.getText().toString());
                FirebaseDatabase.getInstance().getReference().child(uid).push().setValue(map);
                Toast.makeText(popup.this,"reminder created",Toast.LENGTH_LONG).show();
                startActivity(new Intent(popup.this, reminder.class));

            }
            });

    }
}
