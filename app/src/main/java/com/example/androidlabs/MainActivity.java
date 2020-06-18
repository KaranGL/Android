package com.example.androidlabs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs = null;
    private EditText editMail;
    private EditText editPass;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);

        editMail = findViewById(R.id.editemail);
        editPass = findViewById(R.id.editpassword);
        btn = findViewById(R.id.button);

        prefs = getSharedPreferences("Karan_Lab_3", MODE_PRIVATE);
        String email = prefs.getString("email", "");
        editMail.setText(email);

        btn.setOnClickListener( click -> {
            Intent goToProfile = new Intent(this, ProfileActivity.class);
            goToProfile.putExtra("EMAIL", editMail.getText().toString());
            startActivityForResult(goToProfile, 9);
        });
//        Intent nextPage = new Intent(this, ProfileActivity.class);
//        btn.setOnClickListener( click -> startActivity( nextPage ));
    }

    @Override
    protected void onPause() {
        super.onPause();
        prefs = getSharedPreferences("Karan_Lab_3", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", editMail.getText().toString());
        editor.commit();
//        super.onPause();
//        prefs = getSharedPreferences("NewFile", Context.MODE_PRIVATE);
//        String savedString = prefs.getString("ReserveName", "");
//        EditText typeField = findViewById(R.id.editemail);
//        typeField.setText(savedString);
//        Button saveButton = findViewById(R.id.button);
//        saveButton.setOnClickListener(bt -> onCreate(typeField.getText().toString()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_CANCELED){
            Toast.makeText(this, "You hit the back button", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}