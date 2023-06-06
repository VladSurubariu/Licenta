import java.util.Arrays;

import static java.lang.Math.abs;

public class Main {

    public static char[][][] matrix = new char[6][3][3];
    public static String tiles_order = "OGRBWY";

    public static void main(String[] args) {
        matrix = initialiseMatrix2();

        solve();

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

        c_matrix[0][0] = new char[]{'O', 'O', 'O'};
        c_matrix[0][1] = new char[]{'O', 'O', 'O'};
        c_matrix[0][2] = new char[]{'Y', 'G', 'G'};

        c_matrix[1][0] = new char[]{'G', 'G', 'G'};
        c_matrix[1][1] = new char[]{'G', 'G', 'G'};
        c_matrix[1][2] = new char[]{'R', 'O', 'O'};

        c_matrix[2][0] = new char[]{'R', 'R', 'R'};
        c_matrix[2][1] = new char[]{'R', 'R', 'R'};
        c_matrix[2][2] = new char[]{'G', 'R', 'Y'};

        c_matrix[3][0] = new char[]{'B', 'B', 'B'};
        c_matrix[3][1] = new char[]{'B', 'B', 'B'};
        c_matrix[3][2] = new char[]{'R', 'B', 'O'};

        c_matrix[4][0] = new char[]{'W', 'W', 'W'};
        c_matrix[4][1] = new char[]{'W', 'W', 'W'};
        c_matrix[4][2] = new char[]{'W', 'W', 'W'};

        c_matrix[5][0] = new char[]{'B', 'Y', 'Y'};
        c_matrix[5][1] = new char[]{'Y', 'Y', 'Y'};
        c_matrix[5][2] = new char[]{'B', 'Y', 'Y'};

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

    public static void solve(){
        whiteCrossStep();
        whiteCornersStep();
        secondLayer();
        yellowCrossStep();
        swapEdges();
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
                        copy_column[2-j] = matrix[target_position][j][backFaceColumnID];
                        matrix[target_position][j][backFaceColumnID] = copy_column_interchange[2-j];
                    }
                    else{
                        copy_column[j] = matrix[target_position][j][id_column];
                        matrix[target_position][j][id_column] = copy_column_interchange[j];
                    }
                }
            }

            if(id_column == 0){
                matrix[3] = moveFaceForRow(matrix[3], up);
            } else if (id_column == 2) {
                matrix[1] = moveFaceForRow(matrix[1], !up);
            }
        }
    }

    public static void moveRowHorizontalUpperFace(char[][] face, int id_row, boolean left){

        if (id_row == 0){
            id_row = 2;
        }
        else if (id_row == 2){
            id_row = 0;
        }

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
            copy_column = matrix[target_position][id_row].clone();

            if(left) {
                matrix[target_position][id_row] = copy_interchange;
            } else {
                for(int i=0; i<3; i++){
                    matrix[target_position][id_row][i] = copy_interchange[2-i];
                }
            }

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
                matrix[target_position][abs(id_row-2)] = copy_interchange;
            }
            else {
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

    public static void yellowCrossStep(){
        while(matrix[5][0][1] != 'Y' || matrix[5][1][0] !='Y' || matrix[5][1][2] != 'Y' || matrix[5][2][1] != 'Y'){
            if(matrix[5][0][1] == 'Y' && matrix[5][1][2] == 'Y' && matrix[5][1][0] != 'Y' && matrix[5][2][1] != 'Y'){
                moveRowHorizontal(matrix[0], 2, true);
            } else if (matrix[5][2][1] == 'Y' && matrix[5][1][2] == 'Y' && matrix[5][1][0] != 'Y' && matrix[5][0][1] != 'Y') {
                moveRowHorizontal(matrix[0], 2, true);
                moveRowHorizontal(matrix[0], 2, true);
            } else if (matrix[5][2][1] == 'Y' && matrix[5][1][0] == 'Y' && matrix[5][1][2] != 'Y' && matrix[5][0][1] != 'Y') {
                moveRowHorizontal(matrix[0], 2, false);
            }

            if(matrix[5][0][1] == 'Y' && matrix[5][1][1] == 'Y' && matrix[5][2][1] == 'Y'){
                moveRowHorizontal(matrix[0], 2, false);
            }

            moveRowHorizontalUpperFace(matrix[4], 2, false);
            moveRowVertical(matrix[0], 0, false);
            moveRowHorizontal(matrix[0], 2, false);
            moveRowVertical(matrix[0], 0, true);
            moveRowHorizontal(matrix[0], 2, true);
            moveRowHorizontalUpperFace(matrix[4], 2, true);
        }
    }

    public static void secondLayer(){
        int[] sidePiece = new int[3];
        int yellow_face_x, yellow_face_y, lateral_face_id;
        boolean leftMoves = false;

        while(!checkSecondLayer()){
            sidePiece = findNonYellowTileOnYellowFace();
            yellow_face_x = sidePiece[0];
            yellow_face_y = sidePiece[1];
            lateral_face_id = sidePiece[2];

            char lateral_color = matrix[lateral_face_id][2][1];
            char yellow_face_color = matrix[5][yellow_face_x][yellow_face_y];

            if(lateral_color != matrix[lateral_face_id][1][1]){
                if(getTargetPosition(getPositionInOrder(getMiddleTile(matrix[lateral_face_id])), true) == getPositionInOrder(lateral_color)){
                    moveRowHorizontal(matrix[0], 2, true );
                } else if (getTargetPosition(getPositionInOrder(getMiddleTile(matrix[lateral_face_id])), false) == getPositionInOrder(lateral_color)) {
                    moveRowHorizontal(matrix[0], 2, false );
                }
                else{
                    moveRowHorizontal(matrix[0], 2, true );
                    moveRowHorizontal(matrix[0], 2, true );
                }
            }

            if(getTargetPosition(getPositionInOrder(lateral_color), true) == getPositionInOrder(yellow_face_color)){
                leftMoves = false; //its false because you are actually keeping the yellow face on top, so it is translated
            }
            else{
                leftMoves = true;
            }

            secondLayerTrick(matrix[getPositionInOrder(lateral_color)], leftMoves);
        }
    }

    private static void secondLayerTrick(char[][] face, boolean left){
        int pos_x = 2;
        int pos_y = 1;
        char color = face[pos_x][pos_y];
        char[][] orange_face = matrix[0];
        char[][] white_face = matrix[4];

        if(!left){
            //dreapta
            if(face == matrix[0]){
                moveRowHorizontal(matrix[0], 2, false);
                moveRowVertical(matrix[0], 0, false);
                moveRowHorizontal(matrix[0], 2, true);
                moveRowVertical(matrix[0], 0, true);
                moveRowHorizontal(matrix[0], 2, true);
                moveRowHorizontalUpperFace(matrix[4], 2, true);
                moveRowHorizontal(matrix[0], 2, false);
                moveRowHorizontalUpperFace(matrix[4], 2, false);
            }
            else if(face == matrix[1]){
                moveRowHorizontal(matrix[0], 2, false);
                moveRowHorizontalUpperFace(matrix[4], 2, false);
                moveRowHorizontal(matrix[0], 2, true);
                moveRowHorizontalUpperFace(matrix[4], 2, true);
                moveRowHorizontal(matrix[0], 2, true);
                moveRowVertical(matrix[0], 2, false);
                moveRowHorizontal(matrix[0], 2, false);
                moveRowVertical(matrix[0], 2, true);
            }
            else if(face == matrix[2]){
                moveRowHorizontal(matrix[0], 2, false);
                moveRowVertical(matrix[0], 2, true);
                moveRowHorizontal(matrix[0], 2, true);
                moveRowVertical(matrix[0], 2, false);
                moveRowHorizontal(matrix[0], 2, true);
                moveRowHorizontalUpperFace(matrix[4], 0, false);
                moveRowHorizontal(matrix[0], 2, false);
                moveRowHorizontalUpperFace(matrix[4], 0, true);
            }
            else if(face == matrix[3]){ //works
                moveRowHorizontal(matrix[0], 2, false);
                moveRowHorizontalUpperFace(matrix[4], 0, true);
                moveRowHorizontal(matrix[0], 2, true);
                moveRowHorizontalUpperFace(matrix[4], 0, false);
                moveRowHorizontal(matrix[0], 2, true);
                moveRowVertical(matrix[0], 0, true);
                moveRowHorizontal(matrix[0], 2, false);
                moveRowVertical(matrix[0], 0, false);
            }
        }
        else{
            //stanga
            if(face == matrix[0]){
                moveRowHorizontal(matrix[0], 2, true);
                moveRowVertical(matrix[0], 2, false);
                moveRowHorizontal(matrix[0], 2, false);
                moveRowVertical(matrix[0], 2, true);
                moveRowHorizontal(matrix[0], 2, false);
                moveRowHorizontalUpperFace(matrix[4], 2, false);
                moveRowHorizontal(matrix[0], 2, true);
                moveRowHorizontalUpperFace(matrix[4], 2, true);
            }
            else if(face == matrix[1]){
                moveRowHorizontal(matrix[0], 2, true);
                moveRowHorizontalUpperFace(matrix[4], 0, false);
                moveRowHorizontal(matrix[0], 2, false);
                moveRowHorizontalUpperFace(matrix[4], 0, true);
                moveRowHorizontal(matrix[0], 2, false);
                moveRowVertical(matrix[0], 2, true);
                moveRowHorizontal(matrix[0], 2, true);
                moveRowVertical(matrix[0], 2, false);

            }
            else if(face == matrix[2]){
                moveRowHorizontal(matrix[0], 2, true);
                moveRowVertical(matrix[0], 0, true);
                moveRowHorizontal(matrix[0], 2, false);
                moveRowVertical(matrix[0], 0, false);
                moveRowHorizontal(matrix[0], 2, false);
                moveRowHorizontalUpperFace(matrix[4], 0, true);
                moveRowHorizontal(matrix[0], 2, true);
                moveRowHorizontalUpperFace(matrix[4], 0, false);
            }
            else if(face == matrix[3]){ //works
                moveRowHorizontal(matrix[0], 2, true);
                moveRowHorizontalUpperFace(matrix[4], 2, true);
                moveRowHorizontal(matrix[0], 2, false);
                moveRowHorizontalUpperFace(matrix[4], 2, false);
                moveRowHorizontal(matrix[0], 2, false);
                moveRowVertical(matrix[0], 0, false);
                moveRowHorizontal(matrix[0], 2, true);
                moveRowVertical(matrix[0], 0, true);
            }
        }
    }

    public static boolean checkSecondLayer(){
        for(int i=0;i<4; i++){
            if(matrix[i][1][0] != matrix[i][1][1] || matrix[i][1][1] != matrix[i][1][2]){
                return false;
            }
        }
        return true;
    }

    public static int[] findNonYellowTileOnYellowFace(){
        int[] returnValue = new int[3];
        Arrays.fill(returnValue, 1000);
        char[][] yellow_face = matrix[5];

        if(yellow_face[0][1] != 'Y' && matrix[0][2][1] != 'Y'){
            returnValue[0] = 0; // yellow face x
            returnValue[1] = 1; // yellow face y
            returnValue[2] = 0; // lateral face id
        } else if (yellow_face[1][0] != 'Y' && matrix[3][2][1] != 'Y') {
            returnValue[0] = 1; // yellow face x
            returnValue[1] = 0; // yellow face y
            returnValue[2] = 3; // lateral face id
        }  else if (yellow_face[1][2] != 'Y' && matrix[1][2][1] != 'Y') {
            returnValue[0] = 1; // yellow face x
            returnValue[1] = 2; // yellow face y
            returnValue[2] = 1; // lateral face id
        } else if (yellow_face[2][1] != 'Y' && matrix[2][2][1] != 'Y') {
            returnValue[0] = 2; // yellow face x
            returnValue[1] = 0; // yellow face y
            returnValue[2] = 2; // lateral face id
        }

        return returnValue;

    }

    public static void whiteCornersStep(){
        while(!checkWhiteCorners()){
            int[] xyzCorner;
            char second_color, third_color, aux;
            int[] second_color_coords = new int[3], third_color_coords = new int[3];
            int second_color_face_middle, third_color_face_middle, aux_face_middle;

            xyzCorner = getWhiteCornersBottomLayer();

            if(xyzCorner[0] != 1000){
                if(xyzCorner[0] == 5){
                    if(xyzCorner[1] == 0){
                        if(xyzCorner[2] == 0)
                        {
                            second_color_face_middle = getPositionInOrder('B');
                            third_color_face_middle = getPositionInOrder('O');
                        }
                        else{
                            second_color_face_middle = getPositionInOrder('O');
                            third_color_face_middle = getPositionInOrder('G');
                        }
                    }
                    else{
                        if(xyzCorner[2] == 0)
                        {
                            second_color_face_middle = getPositionInOrder('R');
                            third_color_face_middle = getPositionInOrder('B');
                        }
                        else{
                            second_color_face_middle = getPositionInOrder('G');
                            third_color_face_middle = getPositionInOrder('R');
                        }
                    }
                    second_color_coords[0] = second_color_face_middle;
                    third_color_coords[0] = third_color_face_middle;

                    second_color_coords[1] = 2;
                    third_color_coords[1] = 2;

                    second_color_coords[2] = 2;
                    third_color_coords[2] = 0;
                }
                else{
                    third_color_face_middle = getPositionInOrder('Y');

                    if(xyzCorner[2] == 0){
                        second_color_face_middle = getTargetPosition(getPositionInOrder(getMiddleTile(matrix[xyzCorner[0]])), true);
                        second_color_coords[2] = 2;

                        if(xyzCorner[0] == 0){
                            third_color_coords[1] = 0;
                            third_color_coords[2] = 0;
                        } else if(xyzCorner[0] == 1) {
                            third_color_coords[1] = 0;
                            third_color_coords[2] = 2;
                        } else if(xyzCorner[0] == 2) {
                            third_color_coords[1] = 2;
                            third_color_coords[2] = 2;
                        } else if(xyzCorner[0] == 3) {
                            third_color_coords[1] = 2;
                            third_color_coords[2] = 0;
                        }

                    }else{
                        second_color_face_middle = getTargetPosition(getPositionInOrder(getMiddleTile(matrix[xyzCorner[0]])), false);
                        second_color_coords[2] = 0;

                        if(xyzCorner[0] == 0){
                            third_color_coords[1] = 0;
                            third_color_coords[2] = 2;
                        } else if(xyzCorner[0] == 1) {
                            third_color_coords[1] = 2;
                            third_color_coords[2] = 2;
                        } else if(xyzCorner[0] == 2) {
                            third_color_coords[1] = 2;
                            third_color_coords[2] = 0;
                        } else if(xyzCorner[0] == 3) {
                            third_color_coords[1] = 0;
                            third_color_coords[2] = 0;
                        }
                    }
                    second_color_coords[0] = second_color_face_middle;
                    second_color_coords[1] = 2;

                    third_color_coords[0] = third_color_face_middle;
                }

                second_color = matrix[second_color_coords[0]][second_color_coords[1]][second_color_coords[2]];
                third_color = matrix[third_color_coords[0]][third_color_coords[1]][third_color_coords[2]];

                if(third_color_face_middle == getPositionInOrder('Y')){
                    aux = matrix[xyzCorner[0]][xyzCorner[1]][xyzCorner[2]];
                    aux_face_middle =  getPositionInOrder(matrix[xyzCorner[0]][1][1]);
                }
                else{
                    aux = third_color;
                    aux_face_middle = third_color_face_middle;
                }

                if(getPositionInOrder(second_color) == second_color_face_middle || getPositionInOrder(third_color) == second_color_face_middle){
                    if(getPositionInOrder(second_color) == aux_face_middle || getPositionInOrder(third_color) == aux_face_middle){
                        while(!checkCorrectCorner(second_color_face_middle, aux_face_middle) && !checkCorrectCorner(aux_face_middle, second_color_face_middle)){
                            if(aux_face_middle != getPositionInOrder('Y')){
                                if(getTargetPosition(getPositionInOrder(matrix[aux_face_middle][1][1]), false) == getPositionInOrder(second_color)){
                                    fourMoves(matrix[getPositionInOrder(second_color)][1][1]);
                                }
                                else{
                                    fourMoves(matrix[aux_face_middle][1][1]);
                                }
                            }
                            else{
                                fourMoves(matrix[xyzCorner[0]][1][1]);
                            }
                        }
                    }
                    else{
                        moveRowHorizontal(matrix[0], 2, true);
                    }
                }
                else{
                    if(getPositionInOrder(second_color) == aux_face_middle || getPositionInOrder(third_color) == aux_face_middle){
                        moveRowHorizontal(matrix[0], 2, false);
                    }
                    else{
                        moveRowHorizontal(matrix[0], 2, false);
                        moveRowHorizontal(matrix[0], 2, false);
                    }
                }
            }
            else{
                if(!checkCorrectCorner(0, 1)){
                    moveRowVertical(matrix[0], 2, false);
                    moveRowHorizontal(matrix[0], 2, false);
                    moveRowVertical(matrix[0], 2, true);
                }
                else if(!checkCorrectCorner(1, 2)){
                    moveRowHorizontal(matrix[4], 0, false);
                    moveRowHorizontal(matrix[0], 2, false);
                    moveRowHorizontal(matrix[4], 0, true);
                }
                else if(!checkCorrectCorner(2, 3)){
                    moveRowHorizontal(matrix[4], 0, true);
                    moveRowHorizontal(matrix[0], 2, false);
                    moveRowHorizontal(matrix[4], 0, false);
                }
                else if(!checkCorrectCorner(3, 0)){
                    moveRowVertical(matrix[0], 0, false);
                    moveRowHorizontal(matrix[0], 2, false);
                    moveRowVertical(matrix[0], 0, true);
                }
            }
        }
    }

    public static boolean checkCorrectCorner(int middle_color1, int middle_color2){
        if(middle_color1 == 3 && middle_color2 == 0){
            if((getPositionInOrder(matrix[3][0][2])) == middle_color1 && (getPositionInOrder(matrix[0][0][0])) == middle_color2){
                return true;
            }
        } else if (middle_color1 == 0 && middle_color2 == 1) {
            if((getPositionInOrder(matrix[0][0][2])) == middle_color1 && (getPositionInOrder(matrix[1][0][0])) == middle_color2){
                return true;
            }
        } else if (middle_color1 == 1 && middle_color2 == 2) {
            if((getPositionInOrder(matrix[1][0][2])) == middle_color1 && (getPositionInOrder(matrix[2][0][0])) == middle_color2){
                return true;
            }
        } else if (middle_color1 == 2 && middle_color2 == 3) {
            if((getPositionInOrder(matrix[2][0][2])) == middle_color1 && (getPositionInOrder(matrix[3][0][0])) == middle_color2){
                return true;
            }
        }

        return false;
    }

    public static int[] getWhiteCornersBottomLayer(){
        int[] returnValue = new int[3];
        Arrays.fill(returnValue, 1000);

        // Search the bottom face for white corners
        for(int i = 0; i<=2; i=i+2){
            for(int j=0; j<=2; j=j+2){
                if(matrix[5][i][j] == 'W'){
                    returnValue[0] = 5;
                    returnValue[1] = i;
                    returnValue[2] = j;

                    return returnValue;
                }
            }
        }

        //Search the bottom rows for white corners
        for(int i = 0; i<=3; i++){
            for(int z=0; z<=2; z=z+2){
                if(matrix[i][2][z] == 'W'){
                    returnValue[0] = i;
                    returnValue[1] = 2;
                    returnValue[2] = z;

                    return returnValue;
                }
            }
        }




        return returnValue;
    }

    public static void fourMoves(char middle_of_face){

        if(middle_of_face == 'O'){
            //TODO: sus, stanga, jos, dreapta
            moveRowVertical(matrix[0], 0, false);
            moveRowHorizontal(matrix[0], 2, false);
            moveRowVertical(matrix[0], 0, true);
            moveRowHorizontal(matrix[0], 2, true);
        } else if (middle_of_face == 'R') {
            moveRowVertical(matrix[0], 2, true);
            moveRowHorizontal(matrix[0], 2, false);
            moveRowVertical(matrix[0], 2, false);
            moveRowHorizontal(matrix[0], 2, true);
        } else if (middle_of_face == 'B') {
            moveRowHorizontalUpperFace(matrix[4], 0, true); // era 2 inainte
            moveRowHorizontal(matrix[0], 2, false);
            moveRowHorizontalUpperFace(matrix[4], 0, false);
            moveRowHorizontal(matrix[0], 2, true);
        } else if (middle_of_face == 'G') {
            moveRowHorizontalUpperFace(matrix[4], 2, false); // era 0 inainte
            moveRowHorizontal(matrix[0], 2, false);
            moveRowHorizontalUpperFace(matrix[4], 2, true);
            moveRowHorizontal(matrix[0], 2, true);
        }
    }

    public static int[] getWhiteFaceCorners(){
        int[] returnValue = new int[3];
        Arrays.fill(returnValue, 1000);

        for(int i=0; i<6;i++){
            for(int j=0; j<=2; j=j+2){
                for(int z=0; z<=2; z=z+2){
                    if (matrix[i][j][z] == 'W') {
                        returnValue[0] = i;
                        returnValue[1] = j;
                        returnValue[2] = z;
                        return returnValue;
                    }
                }
            }
        }
        return returnValue;
    }

    public static boolean checkWhiteCorners(){
        char[][] white_face = matrix[4];

        for(int i=0; i<3;i++){
            for(int j=0; j<3; j++){
                if (white_face[i][j] != 'W'){
                    return false;
                }
            }
        }

        for(int i=0;i<4;i++){
            for(int j=0; j<3; j++){
                if(matrix[i][0][j] != matrix[i][1][1]){
                    return false;
                }
            }
        }

        return true;
    }

    public static void whiteCrossStep(){

        char[][] bottom_face;
        char secondary_color_target_middle = 'W';
        int[] white_piece_on_face;

        while(!checkWhiteCross()){
            bottom_face = matrix[5];
            white_piece_on_face = searchForSidePiece(bottom_face, 'W');

            if(white_piece_on_face[0] != 1000 && white_piece_on_face[1]!=1000){
                if(white_piece_on_face[0] == 1){
                    if(white_piece_on_face[1] == 0){
                        secondary_color_target_middle = 'B';
                    } else if (white_piece_on_face[1] == 2) {
                        secondary_color_target_middle = 'G';
                    }
                }
                else if(white_piece_on_face[0] == 0){
                    secondary_color_target_middle = 'O';
                }
                else {
                    secondary_color_target_middle = 'R';
                }

                int secondary_color_target_position = getPositionInOrder(secondary_color_target_middle);
                char secondary_color_target = matrix[secondary_color_target_position][2][1];

                if(getTargetPosition(getPositionInOrder(secondary_color_target_middle), true) == getPositionInOrder(secondary_color_target)){
                    moveRowHorizontal(matrix[0], 2, true);
                }
                else if (getTargetPosition(getPositionInOrder(secondary_color_target_middle), false) == getPositionInOrder(secondary_color_target)){
                    moveRowHorizontal(matrix[0], 2, false);
                }
                else{
                    moveRowHorizontal(matrix[0], 2, false);
                    moveRowHorizontal(matrix[0], 2, false);
                }

                if(secondary_color_target == 'B'){
                    moveRowVertical(matrix[0], 0, true);
                    moveRowVertical(matrix[0], 0, true);
                }
                else if(secondary_color_target == 'G'){
                    moveRowVertical(matrix[0], 2, true);
                    moveRowVertical(matrix[0], 2, true);
                }
                else if(secondary_color_target == 'R'){
                    moveRowHorizontalUpperFace(matrix[4], 0, true);
                    moveRowHorizontalUpperFace(matrix[4], 0, true);
                }
                else if(secondary_color_target == 'O'){
                    moveRowHorizontalUpperFace(matrix[4], 2, true);
                    moveRowHorizontalUpperFace(matrix[4], 2, true);
                }

            }
            else{
                for(int i=0; i<4; i++){
                    char middle_tile = getMiddleTile(matrix[i]);
                    white_piece_on_face = searchForSidePiece(matrix[i], 'W');
                    if(white_piece_on_face[0] != 1000 && white_piece_on_face[1] != 1000){
                        char origin_middle_color = getMiddleTile(matrix[i]);
                        char white_piece_on_face_side_color = 'X';

                        if(white_piece_on_face[0] == 0){
                            if(origin_middle_color == 'B'){
                                white_piece_on_face_side_color = matrix[4][1][0];
                            } else if (origin_middle_color == 'O') {
                                white_piece_on_face_side_color = matrix[4][2][1];
                            } else if (origin_middle_color == 'G') {
                                white_piece_on_face_side_color = matrix[4][1][2];
                            } else if (origin_middle_color == 'R') {
                                white_piece_on_face_side_color = matrix[4][0][1];
                            }

                            if(white_piece_on_face_side_color == origin_middle_color){ // the pieces are reversed
                                if(white_piece_on_face_side_color == 'O'){
                                    moveRowHorizontalUpperFace(matrix[4], 2, false);
                                    moveRowHorizontal(matrix[0], 0, false);
                                    moveRowVertical(matrix[0], 2, true);
                                    moveRowHorizontal(matrix[0], 0, true);

                                } else if (white_piece_on_face_side_color == 'R') {
                                    moveRowHorizontalUpperFace(matrix[4], 0, true);
                                    moveRowHorizontal(matrix[0], 0, false);
                                    moveRowVertical(matrix[0], 0, false);
                                    moveRowHorizontal(matrix[0], 0, true);
                                } else if (white_piece_on_face_side_color == 'B') {
                                    moveRowVertical(matrix[0], 0, false );
                                    moveRowHorizontal(matrix[0], 0, false);
                                    moveRowHorizontalUpperFace(matrix[4], 2, false);
                                    moveRowHorizontal(matrix[0], 0, true);
                                } else if (white_piece_on_face_side_color == 'G') {
                                    moveRowVertical(matrix[0], 2, true );
                                    moveRowHorizontal(matrix[0], 0, false);
                                    moveRowHorizontalUpperFace(matrix[4], 0, true);
                                    moveRowHorizontal(matrix[0], 0, true);
                                }
                            }
                            else{
                                int left_face_positionInOrder = getTargetPosition(getPositionInOrder(origin_middle_color), true);
                                if (left_face_positionInOrder == getPositionInOrder(white_piece_on_face_side_color)){
                                    moveRowHorizontal(matrix[0], 0, true);
                                }
                                else{
                                    left_face_positionInOrder = getTargetPosition(getPositionInOrder(origin_middle_color), false);
                                    if (left_face_positionInOrder == getPositionInOrder(white_piece_on_face_side_color)){
                                        moveRowHorizontal(matrix[0], 0, false);
                                    }
                                    else{
                                        moveRowHorizontal(matrix[0], 0, false);
                                        moveRowHorizontal(matrix[0], 0, false);
                                    }
                                }
                            }
                            System.out.println(Arrays.deepToString(matrix));
                        }
                        else if (white_piece_on_face[0] == 1){
                            if(middle_tile == 'O'){
                                if (white_piece_on_face[1] == 0) {
                                    moveRowVertical(matrix[0], 0, false);
                                    moveRowHorizontal(matrix[0], 2, false);
                                    moveRowVertical(matrix[0], 0, true);
                                } else {
                                    moveRowVertical(matrix[0], 2, false);
                                    moveRowHorizontal(matrix[0], 2, false);
                                    moveRowVertical(matrix[0], 2, true);
                                }
                            } else if (middle_tile == 'R') {
                                if (white_piece_on_face[1] == 0){
                                    moveRowVertical(matrix[0], 2, true);
                                    moveRowHorizontal(matrix[0], 2, false);
                                    moveRowVertical(matrix[0], 2, false);
                                } else {
                                    moveRowVertical(matrix[0], 0, true);
                                    moveRowHorizontal(matrix[0], 2, false);
                                    moveRowVertical(matrix[0], 0, false);
                                }
                            } else if (middle_tile == 'B') {
                                if (white_piece_on_face[1] == 0){
                                    moveRowHorizontalUpperFace(matrix[4], 0, true);
                                    moveRowHorizontal(matrix[0], 2, false);
                                    moveRowHorizontalUpperFace(matrix[4], 0, false);
                                } else {
                                    moveRowHorizontalUpperFace(matrix[4], 2, true);
                                    moveRowHorizontal(matrix[0], 2, false);
                                    moveRowHorizontalUpperFace(matrix[4], 2, false);
                                }
                            } else if (middle_tile == 'G') {
                                if (white_piece_on_face[1] == 0){
                                    moveRowHorizontalUpperFace(matrix[4], 2, false);
                                    moveRowHorizontal(matrix[0], 2, false);
                                    moveRowHorizontalUpperFace(matrix[4], 2, true);
                                } else {
                                    moveRowHorizontalUpperFace(matrix[4], 0, false);
                                    moveRowHorizontal(matrix[0], 2, false);
                                    moveRowHorizontalUpperFace(matrix[4], 0, true);
                                }
                            }
                        }
                        else if(white_piece_on_face[0] == 2){
                            if (middle_tile == 'O'){
                                white_piece_on_face_side_color = matrix[5][0][1];
                            } else if (middle_tile == 'B') {
                                white_piece_on_face_side_color = matrix[5][1][0];
                            } else if (middle_tile == 'R') {
                                white_piece_on_face_side_color = matrix[5][2][1];
                            } else if (middle_tile == 'G') {
                                white_piece_on_face_side_color = matrix[5][1][2];
                            }

                            int target = getTargetPosition(getPositionInOrder(middle_tile), true);
                            if(target == getPositionInOrder(white_piece_on_face_side_color)){
                                moveRowHorizontal(matrix[0], 2, true);
                            } else if (getPositionInOrder(middle_tile) != getPositionInOrder(white_piece_on_face_side_color)){
                                target = getTargetPosition(getPositionInOrder(middle_tile), false);
                                if(target == getPositionInOrder(white_piece_on_face_side_color)){
                                    moveRowHorizontal(matrix[0], 2, false);
                                }
                                else{
                                    moveRowHorizontal(matrix[0], 2, false);
                                    moveRowHorizontal(matrix[0], 2, false);
                                }
                            }

                            if(white_piece_on_face_side_color == 'O'){
                                moveRowHorizontalUpperFace(matrix[4], 2, true);
                                moveRowHorizontalUpperFace(matrix[4], 2, true);
                            } else if (white_piece_on_face_side_color == 'R') {
                                moveRowHorizontalUpperFace(matrix[4], 0, true);
                                moveRowHorizontalUpperFace(matrix[4], 0, true);
                            } else if (white_piece_on_face_side_color == 'G') {
                                moveRowVertical(matrix[0], 2, true);
                                moveRowVertical(matrix[0], 2, true);
                            } else if (white_piece_on_face_side_color == 'B') {
                                moveRowVertical(matrix[0], 0, true);
                                moveRowVertical(matrix[0], 0, true);
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    public static boolean checkWhiteCross(){
        char[][] white_face = matrix[4];

        if(white_face[0][1] == 'W' && white_face[2][1] == 'W' && white_face[1][0] == 'W' && white_face[1][2] == 'W'){
            return matrix[2][0][1] == 'R' && matrix[1][0][1] == 'G' && matrix[3][0][1] == 'B' && matrix[0][0][1] == 'O';
        }
        return false;
    }

    public static int[] searchForSidePiece(char[][] face, char color){
        int[] returnValue = new int[2];
        Arrays.fill(returnValue, 1000);

        if(face[0][1] == color){
            returnValue[0] = 0;
            returnValue[1] = 1;
        } else if (face[1][0] == color) {
            returnValue[0] = 1;
            returnValue[1] = 0;
        } else if (face[1][2] == color) {
            returnValue[0] = 1;
            returnValue[1] = 2;
        } else if (face[2][1] == color) {
            returnValue[0] = 2;
            returnValue[1] = 1;
        }

        return returnValue;
    }

    public static void swapEdges(){
        while(!checkEdges()){
            for(int i=0;i<4;i++){
                if(matrix[i][2][1] != matrix[i][1][1]){
                    int neighbour_tile_middle_id = getTargetPosition(getPositionInOrder(getMiddleTile(matrix[i])), true);
                    if(matrix[neighbour_tile_middle_id][2][1] == matrix[i][1][1]){
                        changePlaces(neighbour_tile_middle_id, i);
                    }
                    else{
                        neighbour_tile_middle_id = getTargetPosition(getPositionInOrder(getMiddleTile(matrix[i])), false);
                        if(matrix[neighbour_tile_middle_id][2][1] == matrix[i][1][1]){
                            changePlaces(i, neighbour_tile_middle_id);
                        }
                        else{
                            int neighbour_of_neighbour = getTargetPosition(neighbour_tile_middle_id, false);
                            changePlaces(neighbour_of_neighbour, neighbour_tile_middle_id);
                            changePlaces(neighbour_tile_middle_id, i);
                            changePlaces(neighbour_of_neighbour, neighbour_tile_middle_id);
                        }
                    }
                }
            }
        }
    }

    public static boolean checkEdges(){
        for(int i = 0; i<4; i++){
            char color = matrix[i][2][1];
            if(color != matrix[i][1][1]){
                return false;
            }
        }
        return true;
    }

    public static void changePlaces(int origin_middle, int destination_middle){
        if (origin_middle == 0){
            moveRowVertical(matrix[0], 0, false);
            moveRowHorizontal(matrix[0], 2, false);
            moveRowVertical(matrix[0], 0, true);
            moveRowHorizontal(matrix[0], 2, false);
            moveRowVertical(matrix[0], 0, false);
            moveRowHorizontal(matrix[0], 2, false);
            moveRowHorizontal(matrix[0], 2, false);
            moveRowVertical(matrix[0], 0, true);
            moveRowHorizontal(matrix[0], 2, false);
        }
        else if (origin_middle == 1){
            moveRowHorizontalUpperFace(matrix[4], 2, false);
            moveRowHorizontal(matrix[0], 2, false);
            moveRowHorizontalUpperFace(matrix[4], 2, true);
            moveRowHorizontal(matrix[0], 2, false);
            moveRowHorizontalUpperFace(matrix[4], 2, false);
            moveRowHorizontal(matrix[0], 2, false);
            moveRowHorizontal(matrix[0], 2, false);
            moveRowHorizontalUpperFace(matrix[4], 2, true);
            moveRowHorizontal(matrix[0], 2, false);
        }
        else if (origin_middle == 2){
            moveRowVertical(matrix[0], 2, true);
            moveRowHorizontal(matrix[0], 2, false);
            moveRowVertical(matrix[0], 2, false);
            moveRowHorizontal(matrix[0], 2, false);
            moveRowVertical(matrix[0], 2, true);
            moveRowHorizontal(matrix[0], 2, false);
            moveRowHorizontal(matrix[0], 2, false);
            moveRowVertical(matrix[0], 2, false);
            moveRowHorizontal(matrix[0], 2, false);
        }
        else if (origin_middle == 3){
            moveRowHorizontalUpperFace(matrix[4], 0, true);
            moveRowHorizontal(matrix[0], 2, false);
            moveRowHorizontalUpperFace(matrix[4], 0, false);
            moveRowHorizontal(matrix[0], 2, false);
            moveRowHorizontalUpperFace(matrix[4], 0, true);
            moveRowHorizontal(matrix[0], 2, false);
            moveRowHorizontal(matrix[0], 2, false);
            moveRowHorizontalUpperFace(matrix[4], 0, false);
            moveRowHorizontal(matrix[0], 2, false);
        }
    }
}