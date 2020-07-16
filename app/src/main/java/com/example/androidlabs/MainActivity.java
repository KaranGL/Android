package com.example.androidlabs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btn, btn2;
    public static final String ACTIVITY_NAME = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);
        btn = findViewById(R.id.button);
        btn.setOnClickListener( click -> {
            Intent goToProfile = new Intent(this, ProfileActivity.class);
            startActivityForResult(goToProfile, 9);
        });

        btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener( click->{
            Intent weather = new Intent(this, WeatherForecast.class);
            startActivityForResult(weather, 9);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(ACTIVITY_NAME, "In function: onActivityResult");
        if(resultCode==RESULT_CANCELED){
            Toast.makeText(this, "You hit the back button", Toast.LENGTH_LONG).show();
        }
    }
}