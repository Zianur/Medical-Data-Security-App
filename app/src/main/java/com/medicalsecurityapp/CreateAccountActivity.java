package com.medicalsecurityapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccountActivity extends AppCompatActivity {

    private Button createAccountButton;
    private FirebaseAuth mAuth;
    private EditText password1, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();

        createAccountButton = (Button) findViewById(R.id.signup_button);
        email = (EditText) findViewById(R.id.signup_email_edittext);
        password = (EditText) findViewById(R.id.signup_password_edittext);
        password1 = (EditText) findViewById(R.id.signup_confirm_password);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AuthenticateUser();
                
            }
        });
    }



    private void AuthenticateUser()
    {

        String useremail = email.getText().toString();
        String userpassword = password.getText().toString();
        String confirmPassword = password1.getText().toString();

        if (userpassword.equals(confirmPassword))
        {

            mAuth.createUserWithEmailAndPassword(useremail,userpassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful())
                            {
                                SendToSignUp();
                                Toast.makeText(CreateAccountActivity.this, "You have been Authenticated", Toast.LENGTH_LONG).show();

                            }
                            else
                            {
                                String message = task.getException().getMessage();
                                Toast.makeText(CreateAccountActivity.this, "Error Occured" + " " + message, Toast.LENGTH_LONG).show();

                            }

                        }
                    });

        }
        else
        {
            Toast.makeText(this, "password did not match", Toast.LENGTH_SHORT).show();
        }

    }

    private void SendToSignUp() {

        Intent signUpIntent = new Intent(this,SignupActivity.class);
        startActivity(signUpIntent);
    }


}