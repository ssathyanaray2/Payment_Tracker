package com.example.payment_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthMultiFactorException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.MultiFactorResolver;
import com.google.firebase.auth.AuthResult;

public class reminder extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        Button signout=findViewById(R.id.button2);
        mAuth=FirebaseAuth.getInstance();
        /*String TAG="user";
        int a= getIntent().getIntExtra("uid",0);
        String u=Integer.toString(a);
        if( a != 0) {
            Log.i(TAG,u);
        }
        TextView t=findViewById(R.id.user);
        t.setText(u);
*/

        signout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Toast.makeText(reminder.this, "Signed out", Toast.LENGTH_LONG).show();
                mAuth.signOut();
                startActivity(new Intent(reminder.this, test.class));
            }
        });

    }
    public void pop(View view){
        Intent intent = new Intent(this, popup.class);
        startActivity(intent);
    }

}
