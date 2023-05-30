public class Main {

    public static char[][][] matrix = new char[6][3][3];
    public static String tiles_order = "OGRBWY";

    public static void main(String[] args) {
        matrix = initialiseMatrix();
        char[][] face = matrix[0];
        //moveRow(face, 2, false);
        moveFace(face, false);
    }

    //The orientation is ORANGE FACE in front of the user, WHITE FACE above and YELLOW FACE bellow
    public static char[][][] initialiseMatrix(){

        char c_matrix[][][] = new char[6][3][3];

        c_matrix[0][0] = new char[]{'R', 'O', 'W'};
        c_matrix[0][1] = new char[]{'R', 'O', 'W'};
        c_matrix[0][2] = new char[]{'R', 'O', 'W'};

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

    public static char getMiddleTile(char face[][]){
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

    public static char[][] convertRowToColumn(char[][] face, int columnID, int rowID, boolean leftToRight){

        char[] row = face[rowID].clone();

        for(int i = 0; i<3; i++){
            if(leftToRight)
                face[i][columnID] = row[2-i];
            else
                face[i][columnID] = row[i];
        }

        return face;
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

    public static void moveRow(char face[][], int id_row, boolean left){

        int position_in_order = getPositionInOrder(getMiddleTile(face));

        int target_position;

        if(position_in_order >= 4){
            moveFace(face, left);
        } else if (id_row == 1) {
            if(left){
                moveRow(face, 0, false);
                moveRow(face, 2, false);
            }
            else{
                moveRow(face, 0, true);
                moveRow(face, 2, true);
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
            System.out.println(matrix);
        }

        if(id_row != 1){
            moveFace(face, left);
        }
    }

    public static void moveFace(char face[][], boolean left){

        char[] column1 = getColumn(face, 0);
        char[] column2 = getColumn(face, 2);

        char[][] face_copy;

        face_copy = convertRowToColumn(face, 0,0, left);
        face_copy = convertRowToColumn(face_copy, 2, 2, left);

        if(left){
            face_copy[0][1] = column2[1];
            face_copy[2][1] = column1[1];
        }
        else{
            face_copy[0][1] = column1[1];
            face_copy[2][1] = column2[1];
        }
        System.out.println(face_copy);
    }
}