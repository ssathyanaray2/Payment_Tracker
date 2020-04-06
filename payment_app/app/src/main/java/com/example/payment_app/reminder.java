package com.example.payment_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthMultiFactorException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.MultiFactorResolver;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;


public class reminder extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    TextView test;
    DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        Button signout=findViewById(R.id.button2);
        mAuth=FirebaseAuth.getInstance();
        test=(TextView)findViewById(R.id.testview);
        /*String TAG="user";
        int a= getIntent().getIntExtra("uid",0);
        String u=Integer.toString(a);
        if( a != 0) {
            Log.i(TAG,u);
        }
        TextView t=findViewById(R.id.user);
        t.setText(u);
*/
        lineup();
        signout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Toast.makeText(reminder.this, "Signed out", Toast.LENGTH_LONG).show();
                mAuth.signOut();
                startActivity(new Intent(reminder.this, test.class));
            }
        });

    }

    private void lineup(){
        test=findViewById(R.id.testview);
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference().child("eX55zTXs0QduJqzikh8RGUl7vDy2").child("1");

        mRef.addValueEventListener(new ValueEventListener(){
            public void onDataChange(DataSnapshot datasnapshot){
                String value=datasnapshot.child("name").getValue().toString();
                test.setText(value);
                Toast.makeText(reminder.this, "Value displayed", Toast.LENGTH_LONG).show();
            }
           public void onCancelled(DatabaseError dataerr) {
               Toast.makeText(reminder.this, "Value not displayed", Toast.LENGTH_LONG).show();
           }
        });
    }
    public void pop(View view){
        Intent intent = new Intent(this, popup.class);
        startActivity(intent);
    }

  /*  private Firebase mRef;
    private add=(Button) findViewById(R.id.add);
    mRef=new Firebase("https://payment-tracker-af8ff.firebaseio.com/");
    add.setOnClickListener(new View.OnClickListener(){
        public void onClick(View.view){

        }
    });*/
}
