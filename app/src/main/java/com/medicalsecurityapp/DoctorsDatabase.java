package com.medicalsecurityapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DoctorsDatabase extends AppCompatActivity {

    private Button requestButton, seeDetailsbtn;
    private EditText patientID;
    private DatabaseReference requestRef, permissionRef, UsersRef, doctorDatabase;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private String currentUserId, userId, savecurrentdate, savecurrentTime, postrandomName, downloadUrl, fullName;
    private static final int CAMERA_REQUEST = 7;
    private TextView ddTextView;
    private ArrayList<MedicalRecordModule> recordModuleArrayList;
    private AdapterPatientsRecords adapterPatientsRecords;
    private RecyclerView ddPRRecyclerView;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docotors_database);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();//getting authentication unique id
        requestRef = FirebaseDatabase.getInstance().getReference().child("request");
        permissionRef = FirebaseDatabase.getInstance().getReference().child("permission");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        doctorDatabase = FirebaseDatabase.getInstance().getReference().child("doctordatabase");
        mStorageRef = FirebaseStorage.getInstance().getReference();

        requestButton = (Button) findViewById(R.id.request_button);
        seeDetailsbtn = (Button) findViewById(R.id.see_details_btn);
        patientID = (EditText) findViewById(R.id.dd_patient_id);
        ddTextView = (TextView) findViewById(R.id.dd_text_id);
        ddPRRecyclerView = (RecyclerView) findViewById(R.id.dd_recyclerview);
        ddPRRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        ddPRRecyclerView.setLayoutManager(linearLayoutManager);

        recordModuleArrayList = new ArrayList<>();


        //Changing the title of the actionbar
        setTitle("Doctor's Database");


        userId = patientID.getText().toString();




        seeDetailsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckPermission();
            }
        });

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SendRequest();
            }
        });


        UsersRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if(snapshot.exists()) {

                    if (snapshot.hasChild("userfullname")) {
                         fullName = snapshot.child("userfullname").getValue().toString();//getting the name from the database

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DisplayAllPatient();



    }

    private void DisplayAllPatient()
    {

        ClearAll();

        Query query = doctorDatabase;


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    MedicalRecordModule medicalRecordModule = new MedicalRecordModule();

                    medicalRecordModule.setDate(dataSnapshot.child("userfullname").getValue().toString());
                    medicalRecordModule.setIssues(dataSnapshot.child("age").getValue().toString());
                    medicalRecordModule.setDoctor(dataSnapshot.child("id").getValue().toString());

                    recordModuleArrayList.add(medicalRecordModule);

                }

                adapterPatientsRecords = new AdapterPatientsRecords(DoctorsDatabase.this,recordModuleArrayList);
                ddPRRecyclerView.setAdapter(adapterPatientsRecords);
                adapterPatientsRecords.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void ClearAll() {

        if (recordModuleArrayList != null)
        {
            recordModuleArrayList.clear();

            if (adapterPatientsRecords != null)
            {
                adapterPatientsRecords.notifyDataSetChanged();
            }
        }
    }

    private void SendRequest()
    {
       // TakePhoto();
        SavingInfoToDatabase();

    }

    private void TakePhoto()
    {

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

           uri = data.getData();

            Calendar callForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
            savecurrentdate = currentDate.format(callForDate.getTime());

            Calendar callForTime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
            savecurrentTime = currentTime.format(callForTime.getTime());

            postrandomName = savecurrentdate + savecurrentTime;

            try {
                StorageReference FilePath = mStorageRef.child("requestimage").child(postrandomName + ".jpg");

                FilePath.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful())
                        {
                            downloadUrl = task.getResult().getStorage().getDownloadUrl().toString();
                            Toast.makeText(DoctorsDatabase.this, "Image uploaded to storage", Toast.LENGTH_LONG).show();

                            SavingInfoToDatabase();
                        }
                        else
                        {
                            String messege = task.getException().getMessage();
                            Toast.makeText(DoctorsDatabase.this, "Error" + messege, Toast.LENGTH_LONG).show();
                        }


                    }
                });

            }
            catch(NullPointerException e) {

                Toast.makeText(this, "error" + e, Toast.LENGTH_SHORT).show();
            }




        }
    }

    private void SavingInfoToDatabase()
    {

        userId = patientID.getText().toString();

        requestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                requestRef.child(userId).child("name").setValue(fullName);
                //requestRef.child(userId).child("image").setValue(downloadUrl);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        requestButton.setText("Requested");

    }

    private void CheckPermission() {

        permissionRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    SendToDetailsPage();


                }
                else
                {
                    requestButton.setVisibility(View.VISIBLE);
                    ddTextView.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void DeletePermission()
    {

        permissionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                permissionRef.child(userId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        requestRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                requestRef.child(userId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        Toast.makeText(DoctorsDatabase.this, "Permission and Request are deleted", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void SendToDetailsPage()
    {

        userId = patientID.getText().toString();

        DeletePermission();

        Intent detailIntent = new Intent(this,PatientRecordActivity.class);
        detailIntent.putExtra("key", userId);
        startActivity(detailIntent);

    }
}