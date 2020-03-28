package com.example.payment_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthMultiFactorException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.MultiFactorResolver;
import android.widget.Button;
import android.widget.Toast;
import android.text.TextUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


public class MainActivity extends AppCompatActivity {

    private EditText EmailField;
    private EditText Password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

       /* mAuthListener = new FirebaseAuth.AuthStateListener() {
            public void onAuthStateChanged(FirebaseAuth firebaseauth) {
                if (firebaseauth.getCurrentUser() != null) {
                    startActivity(new Intent(MainActivity.this, reminder.class));
                }

            }
        };*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        EmailField=findViewById(R.id.Email);
        Password= findViewById(R.id.Password);
        Button signup= findViewById(R.id.Submit);

        signup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startSignUp();
            }
        });
    }


    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {
            startActivity(new Intent(MainActivity.this, reminder.class));
        }
    }

    private void startSignUp(){
        String email=EmailField.getText().toString().trim();
        String pass=Password.getText().toString().trim();
        if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(pass)){
            Toast.makeText(MainActivity.this, "Fields are empty", Toast.LENGTH_LONG).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Registered", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(MainActivity.this, reminder.class));

                            } else {
                                Toast.makeText(MainActivity.this, "Registration Problem", Toast.LENGTH_LONG).show();

                            }


                        }
                    });
        }

    }


    public void next(View view){
        Intent intent = new Intent(this, test.class);
        startActivity(intent);
    }


}
