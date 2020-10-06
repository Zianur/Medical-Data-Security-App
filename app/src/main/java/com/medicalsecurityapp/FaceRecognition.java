package com.medicalsecurityapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class FaceRecognition extends AppCompatActivity {

    private Button takePhotoButton, savePhotoButton, matchPhotoButton;
    private static final int CAMERA_REQUEST =19;
    private ImageView imageView;
    private String key;
    private ProgressDialog loodingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognition);

        takePhotoButton = (Button) findViewById(R.id.takephoto_button);
        savePhotoButton = (Button) findViewById(R.id.savephoto_button);
        matchPhotoButton = (Button) findViewById(R.id.matchyourface_id);
        imageView = (ImageView) findViewById(R.id.facerec_image_id);

        loodingBar = new ProgressDialog(this);

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TakePhoto();
            }
        });


        //Changing the title of the actionbar
        setTitle("Face Recognition");

       key = getIntent().getStringExtra("key");

       if (key.equals("12"))
       {
           savePhotoButton.setVisibility(View.GONE);

       }
       else
       {
           matchPhotoButton.setVisibility(View.GONE);
       }


       matchPhotoButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               AlertDialog.Builder builder = new AlertDialog.Builder(FaceRecognition.this);
               builder.setTitle("Face Recognition");
               builder.setMessage("Match Found! Continue to go to your profile");

               builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                       MacthFace();
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
       });


       savePhotoButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               AlertDialog.Builder builder = new AlertDialog.Builder(FaceRecognition.this);
               builder.setTitle("Face Recognition");
               builder.setMessage("Your face has been set. Continue to go to your profile");

               builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                       SavePhoto();
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
       });

    }

    private void SavePhoto() {

        SendToMainActivity();
    }


    private void MacthFace()
    {

        SendToMainActivity();
    }

    private void SendToMainActivity()
    {
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void TakePhoto() {

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
           // imageView.setImageBitmap(photo);
            if (key.equals("12"))
            {
                imageView.setImageDrawable(getDrawable(R.drawable.fr_save_photo));
            }
           else
            {

                imageView.setImageDrawable(getDrawable(R.drawable.fr_match_photo));
            }

        }
    }
}