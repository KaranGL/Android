package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);

        EditText object = findViewById(R.id.edit);

        Button ob0 = findViewById(R.id.button);
        ob0.setOnClickListener(obj ->Toast.makeText(MainActivity.this,getString(R.string.toast_message),Toast.LENGTH_LONG).show());

        CheckBox ob1 = findViewById(R.id.checkBox);
        ob1.setOnCheckedChangeListener(( buttonView, isChecked)-> {
            if(isChecked){
                Snackbar.make(ob1,getString(R.string.snackbar_on), BaseTransientBottomBar.LENGTH_LONG).setAction((R.string.Undo), click -> buttonView.setChecked(!isChecked)).show();
            }
            else{
                Snackbar.make(ob1,getString(R.string.snackbar_off), BaseTransientBottomBar.LENGTH_LONG).setAction((R.string.Undo), click -> buttonView.setChecked(!isChecked)).show();
            }
        });

        Switch ob2 = findViewById(R.id.sswitch);
        ob2.setOnCheckedChangeListener((bv, isC) ->{
            if(isC){
                Snackbar.make(ob2,getString(R.string.snackbar_on), BaseTransientBottomBar.LENGTH_LONG).setAction((R.string.Undo), click -> bv.setChecked(!isC)).show();
            }
            else{
                Snackbar.make(ob2,getString(R.string.snackbar_off), BaseTransientBottomBar.LENGTH_LONG).setAction((R.string.Undo), click -> bv.setChecked(!isC)).show();
            }
        });

    }
}