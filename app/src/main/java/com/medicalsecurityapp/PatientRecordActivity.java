package com.medicalsecurityapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PatientRecordActivity extends AppCompatActivity implements AdapterMedicalRecords.OnRecordClickListener {

    private DatabaseReference patientDatabase, easyUserRef, doctorDatabase;
    private AdapterMedicalRecords adapterMedicalRecords;
    private TextView nameText, ageText, addressText, emailText;
    private ArrayList<MedicalRecordModule> medicalRecordModuleArrayList;
    private RecyclerView recordRecyclerView;
    private FirebaseAuth mAuth;
    private String currentUserId, userId, userName, userAge, userAddress, record_key, medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_record);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();//getting authentication unique id
        patientDatabase = FirebaseDatabase.getInstance().getReference().child("patientdatabase");
        easyUserRef = FirebaseDatabase.getInstance().getReference().child("useriddatabse");

        nameText = (TextView) findViewById(R.id.patient_name);
        ageText = (TextView) findViewById(R.id.patient_age);
        addressText = (TextView) findViewById(R.id.patient_address);


        recordRecyclerView = (RecyclerView) findViewById(R.id.patient_record_recyclerview);
        recordRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recordRecyclerView.setLayoutManager(linearLayoutManager);

        medicalRecordModuleArrayList = new ArrayList<>();


        userId = getIntent().getStringExtra("key");


        //Changing the title of the actionbar
        setTitle("Patient's Database");

        DisplayAllRecords();

        easyUserRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    if (snapshot.hasChild("userfullname"))
                    {
                        userName = snapshot.child("userfullname").getValue().toString();
                        nameText.setText("Name - " + userName);
                    }

                    if (snapshot.hasChild("address"))
                    {
                        userAddress = snapshot.child("address").getValue().toString();
                        addressText.setText("Address - " + userAddress);
                    }

                    if (snapshot.hasChild("age"))
                    {
                        userAge = snapshot.child("age").getValue().toString();
                        ageText.setText("Age - " + userAge);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void DisplayAllRecords()
    {

        ClearAll();

        Query query = patientDatabase.child(userId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    MedicalRecordModule medicalRecordModule = new MedicalRecordModule();

                    medicalRecordModule.setDate(dataSnapshot.child("date").getValue().toString());
                    medicalRecordModule.setIssues(dataSnapshot.child("issues").getValue().toString());
                    medicalRecordModule.setDoctor(dataSnapshot.child("doctor").getValue().toString());
                    medicalRecordModule.setMedicine(dataSnapshot.child("medicine").getValue().toString());
                    medicalRecordModule.setTestreports(dataSnapshot.child("testreports").getValue().toString());

                    medicalRecordModuleArrayList.add(medicalRecordModule);

                }

                adapterMedicalRecords = new AdapterMedicalRecords(PatientRecordActivity.this,medicalRecordModuleArrayList);
                recordRecyclerView.setAdapter(adapterMedicalRecords);
                adapterMedicalRecords.setOnRecordClickListener(PatientRecordActivity.this);
                adapterMedicalRecords.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void ClearAll()
    {

        if (medicalRecordModuleArrayList != null)
        {
            medicalRecordModuleArrayList.clear();

            if (adapterMedicalRecords != null)
            {
                adapterMedicalRecords.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void OnRecordClick(int position) {

        MedicalRecordModule selected_record = medicalRecordModuleArrayList.get(position);

        record_key = selected_record.getRecordkey();
        medicine  = selected_record.getMedicine();


        EditRecord();



    }

    private void EditRecord()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(PatientRecordActivity.this);
        builder.setTitle("Edit your post");

        final EditText inputField = new EditText(PatientRecordActivity.this);
        inputField.setText(medicine);
        builder.setView(inputField);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                patientDatabase.child(userId).child(record_key).child("medicine").setValue(inputField.getText().toString());

                Intent selfIntent = new Intent(PatientRecordActivity.this,PatientRecordActivity.class);
                startActivity(selfIntent);

                Toast.makeText(PatientRecordActivity.this, "Your post is updated", Toast.LENGTH_LONG).show();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_blue_dark);

    }
}