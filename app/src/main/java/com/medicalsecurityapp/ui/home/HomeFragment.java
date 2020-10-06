package com.medicalsecurityapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicalsecurityapp.AddMRActivity;
import com.medicalsecurityapp.DoctorsDatabase;
import com.medicalsecurityapp.LoginActivity;
import com.medicalsecurityapp.PatientRecordActivity;
import com.medicalsecurityapp.R;
import com.medicalsecurityapp.RecNActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    private String CurrentUserid, userId;
    private Button searchMr, addMr, searchPr, notification;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

          mAuth = FirebaseAuth.getInstance();
         CurrentUserid = mAuth.getCurrentUser().getUid();//getting authentication unique id

        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");


        searchMr = (Button) root.findViewById(R.id.search_data);
        addMr = (Button) root.findViewById(R.id.add_medical_records);
        searchPr = (Button) root.findViewById(R.id.search_patient_records);
        notification = (Button) root.findViewById(R.id.request_id);


        UsersRef.child(CurrentUserid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if(snapshot.exists()) {


                    if (snapshot.hasChild("id")) {
                        userId = snapshot.child("id").getValue().toString();//getting the name from the database

                    }
                }
            }
             @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


                     addMr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addMRIntent = new Intent(getContext(), AddMRActivity.class);
                startActivity(addMRIntent);
            }
        });

        searchMr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent patientRecordIntent = new Intent(getContext(), PatientRecordActivity.class);
                patientRecordIntent.putExtra("key", userId);
                startActivity(patientRecordIntent);
            }
        });


        searchPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent doctorDatabaseIntent = new Intent(getContext(), DoctorsDatabase.class);
                startActivity(doctorDatabaseIntent);
            }
        });


        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent recNIntent = new Intent(getContext(), RecNActivity.class);
                recNIntent.putExtra("key", userId);
                startActivity(recNIntent);
            }
        });

        return root;





    }



//    private void SendToLoginActivity() {
//
//        //this is how intent should be done in fragment
//        startActivity(new Intent(getContext(),LoginActivity.class));
//    }
}