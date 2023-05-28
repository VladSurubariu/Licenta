package com.example.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    public Button buttonSwitch;
    public TextView textView;

    public char matrix[][][] = new char[6][3][3];

    int face_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSwitch = findViewById(R.id.buttonSwitch);
        textView = findViewById(R.id.textView);

        buttonSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchIntent = new Intent(v.getContext(), CameraActivity.class);
                startActivityForResult(switchIntent, 1000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000){
            if(resultCode == RESULT_OK){
                String tiles_colors = data.getStringExtra("tiles_colors");
                char[][] tiles_colors_char = convertToMatrix(tiles_colors);
                matrix[face_id] = tiles_colors_char;
                textView.setText(convertCharArrayToString(matrix[face_id]));

                face_id++;

            }
        }
    }

    public char[][] convertToMatrix(String s){
        char matrix[][] = new char[3][3];
        int i = 0, j = 0;
        String colors = "WBGROY";

        for (char ch : s.toCharArray()){
            if (colors.indexOf(ch) >= 0){
                matrix[i][j] = ch;
                if ( j == 2) {
                    i++;
                    j = 0;
                }
                else{
                    j++;
                }
            }
        }

        return matrix;
    }

    public String convertCharArrayToString(char[][] ch){
        String str = "";

        for (char[] c : ch){
            for(char cc : c){
                str = str + String.valueOf(cc);
            }
        }

        return str;
    }
}

