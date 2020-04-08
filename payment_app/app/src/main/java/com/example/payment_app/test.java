package com.example.payment_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class test extends AppCompatActivity {
    private EditText EmailField;
    private EditText Password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mAuth = FirebaseAuth.getInstance();

        EmailField= findViewById(R.id.Email);
        Password= findViewById(R.id.Password);
        Button signin =  findViewById(R.id.Submit);
        signin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startSignIn();
            }
        });
        //Intent intent = getIntent();
    }
    private void startSignIn(){
        String email=EmailField.getText().toString().trim();
        String pass=Password.getText().toString().trim();
        if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(pass)){
            Toast.makeText(test.this, "Enter data correctly", Toast.LENGTH_LONG).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(test.this, "SignIn Problem", Toast.LENGTH_LONG).show();
                    }
                    else{
                        finish();
                        Toast.makeText(test.this, "SignedIn", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(test.this, reminder.class));
                    }
                }
            });
        }

    }
}
