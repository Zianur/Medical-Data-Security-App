package com.medicalsecurityapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class RecNActivity extends AppCompatActivity {

    private ImageView reqImage;
    private TextView reqName;
    private Button acceptRecBtn;
    private FirebaseAuth mAuth;
    private DatabaseReference requestRef, permissionRef, UsersRef;
    private String currentUserId, userId, ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_n);


        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();//getting authentication unique id
        requestRef = FirebaseDatabase.getInstance().getReference().child("request");
        permissionRef = FirebaseDatabase.getInstance().getReference().child("permission");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        reqName = (TextView) findViewById(R.id.req_name_id);
        acceptRecBtn = (Button) findViewById(R.id.accept_req_id);

        //Changing the title of the actionbar
        setTitle("Request Notification");

        userId = getIntent().getStringExtra("key");

        requestRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    if (snapshot.hasChild("name"))
                    {
                        String name = snapshot.child("name").getValue().toString();
                        reqName.setText(name);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        acceptRecBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GivePermission();
                acceptRecBtn.setText("Request Accepted");
            }
        });

    }

    private void GivePermission()
    {
        permissionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                permissionRef.child(userId).setValue(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}