package com.example.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public Button buttonSwitch;
    public Button buttonHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSwitch = findViewById(R.id.buttonStart);
        buttonHelp = findViewById(R.id.buttonHelp);

        buttonSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchIntent = new Intent(v.getContext(), build_cube.class);
                startActivityForResult(switchIntent, 1000);
            }
        });

        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_help_dialog);
        dialog.show();
    }
}

