import java.util.Arrays;

import static java.lang.Math.abs;

public class Main {

    public static char[][][] matrix = new char[6][3][3];
    public static String tiles_order = "OGRBWY";

    public static void main(String[] args) {
        matrix = initialiseMatrix2();
        char[][] face = matrix[3];
        //moveRowVertical(face, 2, true);
        //moveRowHorizontal(matrix[4], 2, false); // works
        moveRowHorizontalUpperFace(matrix[4], 1, true );
        System.out.println(matrix.toString());

    }

    //The orientation is ORANGE FACE in front of the user, WHITE FACE above and YELLOW FACE bellow
    public static char[][][] initialiseMatrix(){

        char[][][] c_matrix = new char[6][3][3];

        c_matrix[0][0] = new char[]{'O', 'O', 'O'};
        c_matrix[0][1] = new char[]{'O', 'O', 'O'};
        c_matrix[0][2] = new char[]{'O', 'O', 'O'};

        c_matrix[1][0] = new char[]{'G', 'G', 'G'};
        c_matrix[1][1] = new char[]{'G', 'G', 'G'};
        c_matrix[1][2] = new char[]{'G', 'G', 'G'};

        c_matrix[2][0] = new char[]{'R', 'R', 'R'};
        c_matrix[2][1] = new char[]{'R', 'R', 'R'};
        c_matrix[2][2] = new char[]{'R', 'R', 'R'};

        c_matrix[3][0] = new char[]{'B', 'B', 'B'};
        c_matrix[3][1] = new char[]{'B', 'B', 'B'};
        c_matrix[3][2] = new char[]{'B', 'B', 'B'};

        c_matrix[4][0] = new char[]{'W', 'W', 'W'};
        c_matrix[4][1] = new char[]{'W', 'W', 'W'};
        c_matrix[4][2] = new char[]{'W', 'W', 'W'};

        c_matrix[5][0] = new char[]{'Y', 'Y', 'Y'};
        c_matrix[5][1] = new char[]{'Y', 'Y', 'Y'};
        c_matrix[5][2] = new char[]{'Y', 'Y', 'Y'};

        return c_matrix;
    }

    public static char[][][] initialiseMatrix2(){

        char[][][] c_matrix = new char[6][3][3];

        c_matrix[0][0] = new char[]{'B', 'B', 'W'};
        c_matrix[0][1] = new char[]{'O', 'O', 'W'};
        c_matrix[0][2] = new char[]{'R', 'R', 'W'};

        c_matrix[1][0] = new char[]{'O', 'G', 'B'};
        c_matrix[1][1] = new char[]{'O', 'G', 'B'};
        c_matrix[1][2] = new char[]{'O', 'G', 'B'};

        c_matrix[2][0] = new char[]{'Y', 'G', 'G'};
        c_matrix[2][1] = new char[]{'Y', 'R', 'R'};
        c_matrix[2][2] = new char[]{'Y', 'O', 'O'};

        c_matrix[3][0] = new char[]{'R', 'R', 'R'};
        c_matrix[3][1] = new char[]{'B', 'B', 'B'};
        c_matrix[3][2] = new char[]{'G', 'G', 'G'};

        c_matrix[4][0] = new char[]{'W', 'W', 'O'};
        c_matrix[4][1] = new char[]{'W', 'W', 'R'};
        c_matrix[4][2] = new char[]{'W', 'W', 'G'};

        c_matrix[5][0] = new char[]{'Y', 'Y', 'B'};
        c_matrix[5][1] = new char[]{'Y', 'Y', 'O'};
        c_matrix[5][2] = new char[]{'Y', 'Y', 'R'};

        return c_matrix;
    }

    public static char getMiddleTile(char[][] face){
        return face[1][1];
    }

    public static int getPositionInOrder(char middle_tile){
        return tiles_order.indexOf(middle_tile);
    }

    public static char[] getColumn(char[][] face, int columnID){
        char[] columnColors = new char[3];

        for(int i = 0; i<3; i++){
            columnColors[i] = face[i][columnID];
        }
        return columnColors;
    }

    public static char[][] copyFace(char [][] face){
        char[][] face_copy = new char[face.length][face[0].length];
        for (int i = 0; i < face.length; i++) {
            System.arraycopy(face[i], 0, face_copy[i], 0, face[i].length);
        }
        return face_copy;
    }

    public static char[][] convertRowToColumn(char[][] face, char[] row, int columnID,  boolean leftToRight){

        char[][] face_copy = copyFace(face);
        char[] row_copy = row.clone();

        for(int i = 0; i<3; i++){
            if(leftToRight)
                face_copy[i][columnID] = row_copy[2-i];
            else
                face_copy[i][columnID] = row_copy[i];
        }

        return face_copy;
    }

    public static int getTargetPosition(int position_in_order, boolean left){
        if (left){
            if(position_in_order > 0 && position_in_order < 4){
                return position_in_order - 1;
            } else  {
                return 3;
            }
        }
        else{
            if(position_in_order < 3){
                return position_in_order +1;
            } else if (position_in_order == 3) {
                return 0;
            } else {
                return 1;
            }
        }
    }


    public static int getTargetPositionVertical(int position_in_order, boolean up){
        if (up){
            if(position_in_order == 0){
                return 4; // white
            } else if (position_in_order == 4) {
                return 2;
            } else if (position_in_order == 2) {
                return 5;
            } else
                return 0;
        }
        else{
            if(position_in_order == 0){
                return 5; // white
            } else if (position_in_order == 5) {
                return 2;
            } else if (position_in_order == 2) {
                return 4;
            } else
                return 0;
        }
    }

    public static int getTargetPositionUpperFacesMove(int position_in_order, boolean left){
        if (left){
            if(position_in_order == 4){
                return 3;
            } else if (position_in_order == 3) {
                return 5;
            } else if (position_in_order == 5) {
                return 1;
            } else if (position_in_order == 1) {
                return 4;
            }
        }
        else{
            if(position_in_order == 4){
                return 1;
            } else if (position_in_order == 1) {
                return 5;
            } else if (position_in_order == 5) {
                return 3;
            } else if (position_in_order == 3) {
                return 4;
            }
        }

        return 1000;
    }

    public static char[][] moveFaceForRow(char[][] face, boolean left){

        char[] column1 = getColumn(face, 0);
        char[] column2 = getColumn(face, 2);

        char[] row1 = face[0].clone();
        char[] row2 = face[2].clone();

        if(left){
            face = convertRowToColumn(face, row1, 0, true);
            face = convertRowToColumn(face, row2, 2, true);
            face[0][1] = column2[1];
            face[2][1] = column1[1];
        }
        else{
            face = convertRowToColumn(face, row1, 2, false);
            face = convertRowToColumn(face, row2, 0, false);
            face[0][1] = column1[1];
            face[2][1] = column2[1];
        }

        return face;
    }

    public static void moveRowHorizontal(char[][] face, int id_row, boolean left){

        int position_in_order = getPositionInOrder(getMiddleTile(face));

        int target_position;

        if (id_row == 1) {
            if(left){
                moveRowHorizontal(face, 0, false);
                moveRowHorizontal(face, 2, false);
            }
            else{
                moveRowHorizontal(face, 0, true);
                moveRowHorizontal(face, 2, true);
            }

        } else{
            char[] copy_row;

            target_position = getTargetPosition(position_in_order, left);

            copy_row = matrix[target_position][id_row];

            matrix[target_position][id_row] = face[id_row];

            for(int i = 0; i < 3; i++){
                position_in_order = getPositionInOrder(getMiddleTile(matrix[target_position]));
                target_position = getTargetPosition(position_in_order, left);

                char[] copy_row_interchange = copy_row;
                copy_row = matrix[target_position][id_row];
                matrix[target_position][id_row] = copy_row_interchange;
            }
        }

        if(id_row == 0){
            matrix[4] = moveFaceForRow(matrix[4], !left);
        } else if (id_row == 2) {
            matrix[5] = moveFaceForRow(matrix[5], left);
        }
    }

    public static void moveRowVertical(char[][] face, int id_column, boolean up){

        int position_in_order = getPositionInOrder(getMiddleTile(face));
        int target_position;

        if(position_in_order == 1 || position_in_order == 3) {
            //TODO
        } else if (id_column == 1) {
            if(up){
                moveRowVertical(face, 0, false);
                moveRowVertical(face, 2, false);
            }
            else{
                moveRowVertical(face, 0, true);
                moveRowVertical(face, 2, true);
            }
        }
        else {
            char[] copy_column;

            target_position = getTargetPositionVertical(position_in_order, up);

            copy_column = getColumn(matrix[target_position], id_column);

            for(int i = 0; i < 3; i++){
                matrix[target_position][i][id_column] = face[i][id_column];
            }

            for(int i = 0; i < 3; i++){
                position_in_order = getPositionInOrder(getMiddleTile(matrix[target_position]));
                target_position = getTargetPositionVertical(position_in_order, up);

                char[] copy_column_interchange = copy_column.clone();
                for(int j = 0; j < 3; j++){
                    if(getMiddleTile(matrix[target_position]) == 'R'){
                        int backFaceColumnID;
                        if(id_column == 2)
                            backFaceColumnID = 0;
                        else
                            backFaceColumnID = 2;
                        copy_column[j] = matrix[target_position][j][backFaceColumnID];
                        matrix[target_position][j][backFaceColumnID] = copy_column_interchange[j];
                    }
                    else{
                        copy_column[j] = matrix[target_position][j][id_column];
                        matrix[target_position][j][id_column] = copy_column_interchange[j];
                    }
                }
            }

            if(id_column == 0){
                moveFaceForRow(matrix[3], up);
            } else if (id_column == 2) {
                moveFaceForRow(matrix[1], !up);
            }
        }
    }

    public static void moveRowHorizontalUpperFace(char[][] face, int id_row, boolean left){

        int position_in_order = getPositionInOrder(getMiddleTile(face));

        int target_position;

        if (id_row == 1) {
            if(left){
                moveRowHorizontalUpperFace(face, 0, false);
                moveRowHorizontalUpperFace(face, 2, false);
            }
            else{
                moveRowHorizontalUpperFace(face, 0, true);
                moveRowHorizontalUpperFace(face, 2, true);
            }

        } else{
            char[] copy_column;

            target_position = getTargetPositionUpperFacesMove(position_in_order, left);

            if(left){
                copy_column = getColumn(matrix[target_position], abs(2-id_row));
                matrix[target_position] = convertRowToColumn(matrix[target_position], face[abs(2-id_row)], abs(2-id_row), true);
            }
            else {
                copy_column = getColumn(matrix[target_position], id_row);
                matrix[target_position] = convertRowToColumn(matrix[target_position], face[abs(2-id_row)], id_row, false);
            }


            position_in_order = getPositionInOrder(getMiddleTile(matrix[target_position]));
            target_position = getTargetPositionUpperFacesMove(position_in_order, left);

            char[] copy_interchange = copy_column;
            copy_column = matrix[target_position][id_row];
            matrix[target_position][id_row] = copy_interchange;

            position_in_order = getPositionInOrder(getMiddleTile(matrix[target_position]));
            target_position = getTargetPositionUpperFacesMove(position_in_order, left);

            if(left){
                copy_interchange = copy_column;
                copy_column = getColumn(matrix[target_position], id_row);
                matrix[target_position] = convertRowToColumn(matrix[target_position], copy_interchange, id_row, true);
            }
            else{
                copy_interchange = copy_column;
                copy_column = getColumn(matrix[target_position], abs(2-id_row));
                matrix[target_position] = convertRowToColumn(matrix[target_position], copy_interchange, abs(2-id_row), false);
            }


            position_in_order = getPositionInOrder(getMiddleTile(matrix[target_position]));
            target_position = getTargetPositionUpperFacesMove(position_in_order, left);

            copy_interchange = copy_column;


            if(left){
                copy_column = matrix[target_position][abs(id_row-2)];
                matrix[target_position][abs(id_row-2)] = copy_interchange;
            }
            else {
                copy_column = matrix[target_position][abs(id_row-2)];
                for(int i=0; i<3;i++){
                    matrix[target_position][abs(id_row-2)][i] = copy_interchange[2-i];
                }
            }
        }

        if(id_row == 0){
            matrix[0] = moveFaceForRow(matrix[0], left);
        } else if (id_row == 2) {
            matrix[2] = moveFaceForRow(matrix[2], !left);
        }
    }
}