package Program1;
public class Board {
    private char[][] gameBoard;
    public Board() {
        gameBoard = new char[][] {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}};

    }

    public int playerMove(int x, int y, char c) {
        if (gameBoard[y-1][x-1] != ' ' || x > 3 || y > 3 || x <=0 || y <=0) {
            return -1;
        }
        gameBoard[y-1][x-1] = c;
        return 0;
    }
    

    public void printBoard() {
        String spaceOut = "               ";
        System.out.println(spaceOut+"--1---2---3--");
        for (int row = 0; row < gameBoard.length; row++) {
            System.out.printf("%s| %c | %c | %c | %d\n", spaceOut,gameBoard[row][0], gameBoard[row][1], gameBoard[row][2], row+1);
            if (row < gameBoard.length - 1) {
                System.out.println(spaceOut+"|---|---|---|");
            }
        }
        System.out.println(spaceOut+"-------------");
    }

    public int checkWin() { // 0 - no win, 1 or 2 - player win
        int returnVal = 0;

        returnVal = checkDiag(); // check diagonals
        if (returnVal!=0) {
            return returnVal;
        }
        for (int i = 0; i < 3; i++) { // check rows and columns
            returnVal = checkRow(i);
            if(returnVal!=0){
                return returnVal;
            }
            returnVal = checkCol(i);
            if(returnVal!=0){
                return returnVal;
            }
        }
        boolean isTie = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameBoard[i][j] == ' ') {
                    isTie = false;
                }
            }
        }
        if (isTie) {
            return -1;
        } 
        return returnVal;
    }

    // Helper functions for checking rows columns and diagonals
    private int checkDiag() {
        if (gameBoard[0][0]!=' '&&gameBoard[0][0]==gameBoard[1][1]&&gameBoard[1][1]==gameBoard[2][2]) {
            if (gameBoard[0][0]=='x') {
                return 1;
            } else {
                return 2;
            }
        }
        if (gameBoard[2][0]!=' '&&gameBoard[2][0]==gameBoard[1][1]&&gameBoard[1][1]==gameBoard[0][2]) {
            if (gameBoard[0][0]=='x') {
                return 1;
            } else {
                return 2;
            }
        }
        return 0;
    }

    private int checkRow(int y) {
        if (gameBoard[0][y]!=' '&&gameBoard[0][y]==gameBoard[1][y]&&gameBoard[1][y]==gameBoard[2][y]) {
            if (gameBoard[0][y]=='x') {
                return 1;
            } else {
                return 2;
            }
        }
        return 0;
    }

    private int checkCol(int x) {
        if (gameBoard[x][0]!=' '&&gameBoard[x][0]==gameBoard[x][1]&&gameBoard[x][1]==gameBoard[x][2]) {
            if (gameBoard[x][0]=='x') {
                return 1;
            } else {
                return 2;
            }
        }
        return 0;
    }

    public char[][] viewBoard() {
        char[][] boardClone = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j<3; j++) {
                boardClone[i][j] = gameBoard[i][j];
            }
        }
        return boardClone;
    }

}
