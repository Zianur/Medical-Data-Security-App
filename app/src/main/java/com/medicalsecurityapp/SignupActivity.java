package com.medicalsecurityapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    private Button saveBtn;
    private EditText userId, userName, userAge, userDob, ssNumber, userAddress, email, password;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef, easyUserRef;
    private static int dp;
    private String type, CurrentUserid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        CurrentUserid = mAuth.getCurrentUser().getUid();//getting authentication unique id
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUserid);
        easyUserRef = FirebaseDatabase.getInstance().getReference().child("useriddatabse");

        saveBtn = (Button) findViewById(R.id.save_button);
        userAddress = (EditText) findViewById(R.id.address_id);
        userId = (EditText) findViewById(R.id.user_id);
        userAge = (EditText) findViewById(R.id.user_age_id);
        userDob = (EditText) findViewById(R.id.dateofbirth);
        ssNumber = (EditText) findViewById(R.id.ss_number_id);
        userName = (EditText) findViewById(R.id.user_name_id);



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            SendData();

            }
        });
    }



    private void SendData()
    {

        String username = userName.getText().toString();
        String ID = userId.getText().toString();
        String address = userAddress.getText().toString();
        String age = userAge.getText().toString();
        String dob = userDob.getText().toString();
        String ssnumber = ssNumber.getText().toString();




        HashMap userMap = new HashMap();
        userMap.put("userfullname",username);
        userMap.put("id",ID);
        userMap.put("address",address);
        userMap.put("age",age);
        userMap.put("dob",dob);
        userMap.put("ssnumber",ssnumber);
        userMap.put("type",type);

        easyUserRef.child(ID).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if (task.isSuccessful())
                {
                    Toast.makeText(SignupActivity.this, "Data is saved in database 1", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(SignupActivity.this, "Error-Data was not saved in database 1", Toast.LENGTH_SHORT).show();
                }

            }
        });

        UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if(task.isSuccessful())
                {
                    SendToFaceRecognition();
                    Toast.makeText(SignupActivity.this, "Your Account is created successfully", Toast.LENGTH_LONG).show();
                }
                else
                {
                    String message = task.getException().getMessage();
                    Toast.makeText(SignupActivity.this, "Error Occured" + " " + message, Toast.LENGTH_LONG).show();

                }
            }
        });


    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_patient:
                if (checked)
                {

                    type ="patient";

                }
                break;
            case R.id.checkbox_admin:
                if (checked)
                {

                    type ="admin";

                }
                break;
            case R.id.checkbox_doctor:
                if (checked)
                {


                    type ="doctor";

                }
                break;
            // TODO: Veggie sandwich
        }
    }

    private void SendToFaceRecognition()
    {
        Intent faceRecIntent = new Intent(this,FaceRecognition.class);
        faceRecIntent.putExtra("key", "14");
        startActivity(faceRecIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {

            SendToStartActivity();
        }
    }

    private void SendToStartActivity()
    {
        Intent startIntent = new Intent(this,StartingActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startIntent);
        finish();

    }
}