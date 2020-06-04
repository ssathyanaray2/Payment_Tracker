package com.example.payment_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.io.*;
import java.util.*;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import androidx.core.app.NotificationCompat;
import android.app.PendingIntent;
import android.app.NotificationManager;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthMultiFactorException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.MultiFactorResolver;
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
    DatabaseReference mRef;
    FirebaseUser user;
    ListView liv;
    ArrayList<String> list;
    ArrayAdapter<String> aad;
    adapter fuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        Button signout=findViewById(R.id.button2);
        mAuth=FirebaseAuth.getInstance();
        liv=findViewById(R.id.listv);
        list=new ArrayList<>();
        fuser=new adapter();
        aad=new ArrayAdapter<String>(this,R.layout.reminder_info,R.id.txtviw,list);
        lineup();
        signout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Toast.makeText(reminder.this, "Signed out", Toast.LENGTH_LONG).show();
                mAuth.signOut();
                Intent intent=new Intent(reminder.this, test.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        AES aes = new AES();
        String st="Mystr";
        String key="abc123";
        String TAG="encrypt";
        String encr=aes.encrypt(st,key);
        String decr=aes.decrypt(encr,key);
        //Toast.makeText(reminder.this, decr, Toast.LENGTH_LONG).show();
        Log.i(TAG,st);
        Log.i(TAG,encr);
        Log.i(TAG,decr);


    }

    private void lineup() {
        database = FirebaseDatabase.getInstance();
        user=mAuth.getCurrentUser();
        String uid=user.getUid().toString();
        mRef = database.getReference().child(uid);

        mRef.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot datasnapshot) {
                long l= datasnapshot.getChildrenCount();
                int n=(int)l,i=0;
                final String [] temp=new String[n];
                for(DataSnapshot ds: datasnapshot.getChildren()) {
                    fuser = ds.getValue(adapter.class);
                    String str=ds.getKey().toString();
                    temp[i]=str;
                    i+=1;
                    list.add("\n"+"  "+"Name :"+fuser.getName().toString()+"\n"+"  "+"For :"+fuser.getDescription().toString()+"\n"+"  "+"Date :"+fuser.getDate()+"  "+"\n".toString());
                }
                liv.setAdapter(aad);
                liv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                        //Toast.makeText(reminder.this,"U clicked:"+position,Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(reminder.this,display.class);
                        intent.putExtra("key", temp[position]);
                        startActivity(intent);
                    }
                });

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

    public void query(View view){
        Intent intent = new Intent(this, query.class);
        startActivity(intent);
    }
    

}

 /*String TAG="user";
        int a= getIntent().getIntExtra("uid",0);
        String u=Integer.toString(a);
        if( a != 0) {
            Log.i(TAG,u);
        }
        TextView t=findViewById(R.id.user);
        t.setText(u);*/

