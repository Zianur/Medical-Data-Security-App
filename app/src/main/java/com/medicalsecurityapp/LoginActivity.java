package com.medicalsecurityapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText UserEmail, UserPassword;
    private FirebaseAuth mAuth;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginButton = (Button) findViewById(R.id.login_button);
        UserEmail = (EditText) findViewById(R.id.login_email_edittext);
        UserPassword = (EditText) findViewById(R.id.login_password_edittext);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                LogIn();

            }
        });



    }

    private void LogIn() {

        email = UserEmail.getText().toString();
        password = UserPassword.getText().toString();


        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                           SendToFaceRecActivity();
                            Toast.makeText(LoginActivity.this, "You have been logged in successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String message = task.getException().getMessage();
                            Toast.makeText(LoginActivity.this, "Error " + " " + message, Toast.LENGTH_LONG).show();
                        }

                    }
                });


    }

    private void SendToFaceRecActivity()
    {
        Intent faceRecIntent = new Intent(this,FaceRecognition.class);
        faceRecIntent.putExtra("key", "12");
        startActivity(faceRecIntent);
    }


//    private void SendToMainActivity()
//    {
//        Intent mainIntent = new Intent(this,MainActivity.class);
//        startActivity(mainIntent);
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        if (currentUser != null) {
//
//            SendToMainActivity()
//        }
//    }
}