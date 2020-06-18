package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {
    private ImageButton imgbtn;
    private EditText emailedtxt, nameedtxt;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(ACTIVITY_NAME,"In function: onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameedtxt = findViewById(R.id.enterName);
        emailedtxt =findViewById(R.id.enterEmail);
        imgbtn = findViewById(R.id.imagebtn);

        Intent fromMain = getIntent();
        String emailtxt = fromMain.getStringExtra("EMAIL");
        emailedtxt.setText(emailtxt);

        imgbtn.setOnClickListener(click -> dispatchTakePictureIntent());
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(ACTIVITY_NAME, "In function: onActivityResult");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgbtn.setImageBitmap(imageBitmap);
        }
    }

    @Override
    protected void onStart() {
        Log.e(ACTIVITY_NAME,"In function: onStart");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.e(ACTIVITY_NAME,"In function: onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.e(ACTIVITY_NAME,"In function: onResume");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.e(ACTIVITY_NAME,"In function: onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e(ACTIVITY_NAME,"In function: onDestroy");
        super.onDestroy();
    }
}