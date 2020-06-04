package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

        CheckBox ob1 = findViewById(R.id.checkBox);
        ob1.setOnCheckedChangeListener(( buttonView, isChecked)->Toast.makeText(MainActivity.this,getString(R.string.toast_message),Toast.LENGTH_LONG).show());

        Switch ob2 = findViewById(R.id.sswitch);
        ob2.setOnCheckedChangeListener((cb, b) ->{
            if(b){
                Snackbar.make(ob2,"The Switch is now on!", BaseTransientBottomBar.LENGTH_LONG).setAction("Undo", click -> cb.setChecked(!b)).show();
        }
            else{
                Snackbar.make(ob2,"The Switch is now off!", BaseTransientBottomBar.LENGTH_LONG).setAction("Undo", click -> cb.setChecked(!b)).show();
            }
        });

    }
}