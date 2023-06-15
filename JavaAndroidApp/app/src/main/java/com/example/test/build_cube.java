package com.example.test;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class build_cube extends AppCompatActivity {

    public Button buttonScan, buttonSolve;
    public ImageView[][][] matrix_imageviews = new ImageView[6][3][3];
    public char matrix[][][] = new char[6][3][3];
    int face_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_cube);

        buttonScan = findViewById(R.id.buttonScan);
        buttonSolve = findViewById(R.id.buttonSolve);

        populateMatrixImageView();

        buttonSolve.setClickable(false);

        buttonScan.setOnClickListener(new View.OnClickListener() {
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

                for(int i=0; i<3; i++){
                    for(int j=0; j<3; j++){
                        if(matrix[face_id][i][j] == 'G'){
                            matrix_imageviews[face_id][i][j].setBackgroundResource(R.drawable.tile_green);
                        } else if(matrix[face_id][i][j] == 'O'){
                            matrix_imageviews[face_id][i][j].setBackgroundResource(R.drawable.tile_orange);
                        } else if(matrix[face_id][i][j] == 'R'){
                            matrix_imageviews[face_id][i][j].setBackgroundResource(R.drawable.tile_red);
                        } else if(matrix[face_id][i][j] == 'B'){
                            matrix_imageviews[face_id][i][j].setBackgroundResource(R.drawable.tile_blue);
                        } else if(matrix[face_id][i][j] == 'W'){
                            matrix_imageviews[face_id][i][j].setBackgroundResource(R.drawable.tile_white);
                        } else if(matrix[face_id][i][j] == 'Y'){
                            matrix_imageviews[face_id][i][j].setBackgroundResource(R.drawable.tile_yellow);
                        }
                    }
                }

                face_id++;
                if(face_id == 6){
                    buttonSolve.setClickable(true);
                }
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

    public void populateMatrixImageView(){
        matrix_imageviews[0][0][0] = findViewById(R.id.top_right_tile_placeholder0);
        matrix_imageviews[0][0][1] = findViewById(R.id.top_right_tile_placeholder1);
        matrix_imageviews[0][0][2] = findViewById(R.id.top_right_tile_placeholder2);
        matrix_imageviews[0][1][0] = findViewById(R.id.top_right_tile_placeholder3);
        matrix_imageviews[0][1][1] = findViewById(R.id.top_right_tile_placeholder4);
        matrix_imageviews[0][1][2] = findViewById(R.id.top_right_tile_placeholder5);
        matrix_imageviews[0][2][0] = findViewById(R.id.top_right_tile_placeholder6);
        matrix_imageviews[0][2][1] = findViewById(R.id.top_right_tile_placeholder7);
        matrix_imageviews[0][2][2] = findViewById(R.id.top_right_tile_placeholder8);

        matrix_imageviews[1][0][0] = findViewById(R.id.top_right_tile_placeholder9);
        matrix_imageviews[1][0][1] = findViewById(R.id.top_right_tile_placeholder10);
        matrix_imageviews[1][0][2] = findViewById(R.id.top_right_tile_placeholder11);
        matrix_imageviews[1][1][0] = findViewById(R.id.top_right_tile_placeholder12);
        matrix_imageviews[1][1][1] = findViewById(R.id.top_right_tile_placeholder13);
        matrix_imageviews[1][1][2] = findViewById(R.id.top_right_tile_placeholder14);
        matrix_imageviews[1][2][0] = findViewById(R.id.top_right_tile_placeholder15);
        matrix_imageviews[1][2][1] = findViewById(R.id.top_right_tile_placeholder16);
        matrix_imageviews[1][2][2] = findViewById(R.id.top_right_tile_placeholder17);

        matrix_imageviews[2][0][0] = findViewById(R.id.top_right_tile_placeholder18);
        matrix_imageviews[2][0][1] = findViewById(R.id.top_right_tile_placeholder19);
        matrix_imageviews[2][0][2] = findViewById(R.id.top_right_tile_placeholder20);
        matrix_imageviews[2][1][0] = findViewById(R.id.top_right_tile_placeholder21);
        matrix_imageviews[2][1][1] = findViewById(R.id.top_right_tile_placeholder22);
        matrix_imageviews[2][1][2] = findViewById(R.id.top_right_tile_placeholder23);
        matrix_imageviews[2][2][0] = findViewById(R.id.top_right_tile_placeholder24);
        matrix_imageviews[2][2][1] = findViewById(R.id.top_right_tile_placeholder25);
        matrix_imageviews[2][2][2] = findViewById(R.id.top_right_tile_placeholder26);

        matrix_imageviews[3][0][0] = findViewById(R.id.top_right_tile_placeholder27);
        matrix_imageviews[3][0][1] = findViewById(R.id.top_right_tile_placeholder28);
        matrix_imageviews[3][0][2] = findViewById(R.id.top_right_tile_placeholder29);
        matrix_imageviews[3][1][0] = findViewById(R.id.top_right_tile_placeholder30);
        matrix_imageviews[3][1][1] = findViewById(R.id.top_right_tile_placeholder31);
        matrix_imageviews[3][1][2] = findViewById(R.id.top_right_tile_placeholder32);
        matrix_imageviews[3][2][0] = findViewById(R.id.top_right_tile_placeholder33);
        matrix_imageviews[3][2][1] = findViewById(R.id.top_right_tile_placeholder34);
        matrix_imageviews[3][2][2] = findViewById(R.id.top_right_tile_placeholder35);

        matrix_imageviews[4][0][0] = findViewById(R.id.top_right_tile_placeholder36);
        matrix_imageviews[4][0][1] = findViewById(R.id.top_right_tile_placeholder37);
        matrix_imageviews[4][0][2] = findViewById(R.id.top_right_tile_placeholder38);
        matrix_imageviews[4][1][0] = findViewById(R.id.top_right_tile_placeholder39);
        matrix_imageviews[4][1][1] = findViewById(R.id.top_right_tile_placeholder40);
        matrix_imageviews[4][1][2] = findViewById(R.id.top_right_tile_placeholder41);
        matrix_imageviews[4][2][0] = findViewById(R.id.top_right_tile_placeholder42);
        matrix_imageviews[4][2][1] = findViewById(R.id.top_right_tile_placeholder43);
        matrix_imageviews[4][2][2] = findViewById(R.id.top_right_tile_placeholder44);

        matrix_imageviews[5][0][0] = findViewById(R.id.top_right_tile_placeholder45);
        matrix_imageviews[5][0][1] = findViewById(R.id.top_right_tile_placeholder46);
        matrix_imageviews[5][0][2] = findViewById(R.id.top_right_tile_placeholder47);
        matrix_imageviews[5][1][0] = findViewById(R.id.top_right_tile_placeholder48);
        matrix_imageviews[5][1][1] = findViewById(R.id.top_right_tile_placeholder49);
        matrix_imageviews[5][1][2] = findViewById(R.id.top_right_tile_placeholder50);
        matrix_imageviews[5][2][0] = findViewById(R.id.top_right_tile_placeholder51);
        matrix_imageviews[5][2][1] = findViewById(R.id.top_right_tile_placeholder52);
        matrix_imageviews[5][2][2] = findViewById(R.id.top_right_tile_placeholder53);

    }
}