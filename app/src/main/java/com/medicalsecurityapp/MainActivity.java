package com.medicalsecurityapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private TextView navUserName, navUserType;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    private String CurrentUserid, userId;
    private Button searchMr, addMr, searchPr, notification;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

             mAuth = FirebaseAuth.getInstance();
//         CurrentUserid = mAuth.getCurrentUser().getUid();//getting authentication unique id
//
//        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
//
//
//        searchMr = (Button) findViewById(R.id.search_data);
//        addMr = (Button) findViewById(R.id.add_medical_records);
//        searchPr = (Button) findViewById(R.id.search_patient_records);
//        notification = (Button) findViewById(R.id.request_id);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



//        View navview = navigationView.inflateHeaderView(R.layout.nav_header_main);
//        navUserName = (TextView) navview.findViewById(R.id.header_username_id);
//        navUserType = (TextView) navview.findViewById(R.id.header_usertype_id);
//
//
//        UsersRef.child(CurrentUserid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//
//                if(snapshot.exists()) {
//
//                    if (snapshot.hasChild("userfullname")) {
//                        String fullname = snapshot.child("userfullname").getValue().toString();//getting the name from the database
//                        //setting the full name
//                        navUserName.setText(fullname);
//                    }
//
//                    if (snapshot.hasChild("type")) {
//                        String fullname = snapshot.child("type").getValue().toString();//getting the name from the database
//                        //setting the full name
//                        navUserType.setText(fullname);
//                    }
//
//                    if (snapshot.hasChild("id")) {
//                        userId = snapshot.child("id").getValue().toString();//getting the name from the database
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


//        addMr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                  Intent addMRIntent = new Intent(MainActivity.this, AddMRActivity.class);
//                  startActivity(addMRIntent);
//            }
//        });
//
//        searchMr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent patientRecordIntent = new Intent(MainActivity.this, PatientRecordActivity.class);
//               patientRecordIntent.putExtra("key", userId);
//              startActivity(patientRecordIntent);
//            }
//        });
//
//
//        searchPr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent doctorDatabaseIntent = new Intent(MainActivity.this, DoctorsDatabase.class);
//                startActivity(doctorDatabaseIntent);
//            }
//        });
//
//
//        notification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent recNIntent = new Intent(MainActivity.this, RecNActivity.class);
//                startActivity(recNIntent);
//            }
//        });



    }


//    private void SendToReqNActivity() {
//
//
//    }
//
//    private void SendToDoctorsDatabase()
//    {
//
//
//    }
//
//    private void SendToPatientRecordActivity()
//    {
//
//
//    }
//
//
//    private void SendToAddMRActivity()
//    {
//
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:

                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();

                return true;

            case R.id.log_out:

                mAuth.signOut();
                SendToStartActivity();
                Toast.makeText(MainActivity.this, "You have logged out successfully", Toast.LENGTH_SHORT).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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