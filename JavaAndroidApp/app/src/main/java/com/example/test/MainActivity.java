package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;


public class MainActivity extends AppCompatActivity {

    private Button buton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buton = findViewById(R.id.button);
        textView = findViewById(R.id.textview);

        if (!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }

        Python py = Python.getInstance();
        PyObject pyObject = py.getModule("script");
        PyObject obj = pyObject.callAttr("main");

        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(obj.toString());
            }
        });

    }



}