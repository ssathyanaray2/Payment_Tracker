package com.example.payment_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class notify extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        TextView tv=findViewById(R.id.notify);
        String message=getIntent().getStringExtra("message");
        tv.setText(message);
    }
}
