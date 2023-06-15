package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Queue;

public class interpret_results extends AppCompatActivity {

    static Button buttonMove;
    static TextView textMove;
    static Queue<String> moves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpret_results);

        buttonMove = findViewById(R.id.buttonMove);
        textMove = findViewById(R.id.textMove);

        build_cube.matrix = initialiseMatrix2();
        char[][][] local_matrix = build_cube.matrix;

        rubik_cube_solver.setMatrix(local_matrix);
        rubik_cube_solver.solve();

        moves = rubik_cube_solver.moves;
        setText();

        buttonMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText();
            }
        });

    }

    public static void setText(){
        String popped = moves.remove();
        if(popped!=null){
            if(popped == "U"){
                textMove.setText("R1");
                buttonMove.setBackgroundResource(R.drawable.arrow_left);
            }
            else if(popped == "U'"){
                textMove.setText("R1");
                buttonMove.setBackgroundResource(R.drawable.arrow_right);
            }
            else if(popped == "D"){
                textMove.setText("R3");
                buttonMove.setBackgroundResource(R.drawable.arrow_right);
            }
            else if(popped == "D'"){
                textMove.setText("R3");
                buttonMove.setBackgroundResource(R.drawable.arrow_left);
            }
            else if(popped == "R"){
                textMove.setText("C3");
                buttonMove.setBackgroundResource(R.drawable.arrow_up);
            }
            else if(popped == "R'"){
                textMove.setText("C3");
                buttonMove.setBackgroundResource(R.drawable.arrow_down);
            }
            else if(popped == "L"){
                textMove.setText("C1");
                buttonMove.setBackgroundResource(R.drawable.arrow_down);
            }
            else if(popped == "L'"){
                textMove.setText("C1");
                buttonMove.setBackgroundResource(R.drawable.arrow_up);
            }
            else if(popped == "F"){
                textMove.setText("F1");
                buttonMove.setBackgroundResource(R.drawable.arow_backside_right);
            }
            else if(popped == "F'"){
                textMove.setText("F1");
                buttonMove.setBackgroundResource(R.drawable.arrow_backside_left);
            }
            else if(popped == "B"){
                textMove.setText("F3");
                buttonMove.setBackgroundResource(R.drawable.arrow_backside_left);
            }
            else if(popped == "B'"){
                textMove.setText("F3");
                buttonMove.setBackgroundResource(R.drawable.arow_backside_right);
            }
        }
    }

    public static char[][][] initialiseMatrix2(){

        char[][][] c_matrix = new char[6][3][3];

        c_matrix[0][0] = new char[]{'O', 'O', 'O'};
        c_matrix[0][1] = new char[]{'O', 'O', 'O'};
        c_matrix[0][2] = new char[]{'O', 'O', 'Y'};

        c_matrix[1][0] = new char[]{'G', 'G', 'G'};
        c_matrix[1][1] = new char[]{'G', 'G', 'G'};
        c_matrix[1][2] = new char[]{'O', 'G', 'G'};

        c_matrix[2][0] = new char[]{'R', 'R', 'R'};
        c_matrix[2][1] = new char[]{'R', 'R', 'R'};
        c_matrix[2][2] = new char[]{'R', 'R', 'B'};

        c_matrix[3][0] = new char[]{'B', 'B', 'B'};
        c_matrix[3][1] = new char[]{'B', 'B', 'B'};
        c_matrix[3][2] = new char[]{'Y', 'B', 'B'};

        c_matrix[4][0] = new char[]{'W', 'W', 'W'};
        c_matrix[4][1] = new char[]{'W', 'W', 'W'};
        c_matrix[4][2] = new char[]{'W', 'W', 'W'};

        c_matrix[5][0] = new char[]{'Y', 'Y', 'G'};
        c_matrix[5][1] = new char[]{'Y', 'Y', 'Y'};
        c_matrix[5][2] = new char[]{'R', 'Y', 'Y'};

        return c_matrix;
    }

}