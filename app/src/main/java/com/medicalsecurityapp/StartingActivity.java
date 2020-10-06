package com.medicalsecurityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SizeF;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartingActivity extends AppCompatActivity {

    private TextView register, login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        mAuth = FirebaseAuth.getInstance();

        register = (TextView) findViewById(R.id.register_textview);
        login = (TextView) findViewById(R.id.login_textview);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendToCreateAcc();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendToLoginActivity();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null)
        {
            SendUserToMainActivity();
        }

    }

    private void SendUserToMainActivity()
    {

        Intent loginIntent = new Intent(this, MainActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();

    }

    private void SendToLoginActivity() {

        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);

    }

    private void SendToCreateAcc() {

        Intent createAccIntent = new Intent(this, CreateAccountActivity.class);
        startActivity(createAccIntent);
    }

}