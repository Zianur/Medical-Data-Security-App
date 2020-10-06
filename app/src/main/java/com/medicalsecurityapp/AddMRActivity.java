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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddMRActivity extends AppCompatActivity {

    private EditText userName, userId, date, issues, doctorName, testReports, userAge, pMedicine;
    private Button saveButton;
    private DatabaseReference patientDatabase, doctorDatabase;
    private String nodeChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_m_r);

        patientDatabase = FirebaseDatabase.getInstance().getReference().child("patientdatabase");
        doctorDatabase = FirebaseDatabase.getInstance().getReference().child("doctordatabase");

        userName = (EditText) findViewById(R.id.mr_user_name_id);
        userId = (EditText) findViewById(R.id.mr_user_id);
        userAge = (EditText) findViewById(R.id.mr_user_age);
        date = (EditText) findViewById(R.id.mr_date);
        issues = (EditText) findViewById(R.id.mr_issues);
        doctorName = (EditText) findViewById(R.id.mr_doctor);
        testReports = (EditText) findViewById(R.id.mr_testreports);
        pMedicine = (EditText) findViewById(R.id.mr_medicine);

        saveButton = (Button) findViewById(R.id.mr_save);


        //Changing the title of the actionbar
        setTitle("Add Medical Record");


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = userName.getText().toString();
                final String id = userId.getText().toString();
                String age = userAge.getText().toString();
                String dateinfo = date.getText().toString();
                String issuesinfo = issues.getText().toString();
                String doctorname = doctorName.getText().toString();
                String testreports = testReports.getText().toString();
                String medicine = pMedicine.getText().toString();


                HashMap userMap = new HashMap();
                userMap.put("userfullname",name);
                userMap.put("id",id);
                userMap.put("date",dateinfo);
                userMap.put("issues",issuesinfo);
                userMap.put("doctor",doctorname);
                userMap.put("testreports",testreports);
                userMap.put("age",age);
                userMap.put("medicine",medicine);




              doctorDatabase.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {

                      if (!snapshot.hasChild(id))
                      {
                         nodeChecker = "true";

                      }

                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {

                  }
              });


              if (nodeChecker.equals("true"))
              {
                  doctorDatabase.child(id).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                      @Override
                      public void onComplete(@NonNull Task task) {

                          if(task.isSuccessful())
                          {
                              Toast.makeText(AddMRActivity.this, "Data has been added", Toast.LENGTH_LONG).show();
                          }
                          else
                          {
                              String message = task.getException().getMessage();
                              Toast.makeText(AddMRActivity.this, "Error Occured" + " " + message, Toast.LENGTH_LONG).show();

                          }
                      }
                  });
              }

                String uniqueKey = patientDatabase.child(id).push().getKey(); //getting a unique key

                patientDatabase.child(id).child(uniqueKey).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        if(task.isSuccessful())
                        {
                            SendToMainActivity();
                            Toast.makeText(AddMRActivity.this, "Data has been added", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            String message = task.getException().getMessage();
                            Toast.makeText(AddMRActivity.this, "Error Occured" + " " + message, Toast.LENGTH_LONG).show();

                        }
                    }
                });





            }
        });

    }

    private void SendToMainActivity() {

        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }
}