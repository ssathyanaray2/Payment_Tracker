package com.example.payment_app;

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
    private Button signin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        EmailField=(EditText) findViewById(R.id.Email);
        Password=(EditText) findViewById(R.id.Password);
        signin=(Button) findViewById(R.id.Submit);
        signin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startSignIn();
            }
        });
    }

    private void startSignIn(){
        String email=EmailField.getText().toString().trim();
        String pass=Password.getText().toString().trim();
        if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(pass)){
            Toast.makeText(MainActivity.this, "Enter data correctly", Toast.LENGTH_LONG).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                public void onComplete(Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Signin Problem", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "SignedIn", Toast.LENGTH_LONG).show();
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
