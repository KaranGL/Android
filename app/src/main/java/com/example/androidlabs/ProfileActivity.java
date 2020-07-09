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
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    private ImageButton imgbtn;
    private Button chatbtn;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(ACTIVITY_NAME,"In function: onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        chatbtn = findViewById(R.id.chatbtn);
        imgbtn = findViewById(R.id.imagebtn);

        chatbtn.setOnClickListener(click->{
            Intent GoToChat = new Intent(this, ChatRoomActivity.class);
            startActivityForResult(GoToChat, 9);
        });
        imgbtn.setOnClickListener(click -> dispatchTakePictureIntent());

    }

        private void dispatchTakePictureIntent() {
            Log.e(ACTIVITY_NAME, "In function: dispatchTakePictureIntent");
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            Log.e(ACTIVITY_NAME, "In function: onActivityResult");
            if(resultCode==RESULT_CANCELED){
                Toast.makeText(this, "You hit the back button", Toast.LENGTH_LONG).show();
            }
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imgbtn.setImageBitmap(imageBitmap);
            }
        }
}