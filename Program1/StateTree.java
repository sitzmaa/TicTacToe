package Program1;

import java.util.*;

public class StateTree {
    private char[][] gameState;
    private int[] move;
    private char[][] childGameState;
    private int[][] heuristicValue;
    private boolean[][] legalMoves;
    private LinkedList<StateTree> children;
    private StateTree parent;
    private int nodeValue;
    private boolean isLeaf;
    private final int maxDepth = 4;
    private int depth;
    private int playerNum;
    private int terminalVal;
    public StateTree(char[][] state, StateTree parent, boolean leaf, int num, int depth, int[] move) {
        this.gameState = state.clone();
        this.parent = parent;
        nodeValue = 0;
        isLeaf = leaf;
        children = new LinkedList<StateTree>();
        playerNum = num;
        this.depth = depth;
        this.move = new int[2];
        this.move = move;
        terminalVal = 0;
        legalMoves = new boolean[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                legalMoves[i][j] = false;
            }
        }
        heuristicValue = new int[3][3];
    }

    public int[] getMove() {
        return move;
    }

    /*Main Tree Logic*/
    public void populate() {
        
        legality(); // establish legality
        if ((terminalVal = checkTerminal()) != 0) {
            isLeaf = true;
        }
        boolean childLeaves = false;
        if (!isLeaf) { // if I am not a leaf I create children
            if (depth == maxDepth-1) { // if I am one above max depth my children are leaves
                childLeaves = true;
            }
            int[] nextMove = new int[2];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (legalMoves[i][j]) {
                        // clone the child
                        childGameState = new char[][]{
                            {' ', ' ', ' '},
                            {' ', ' ', ' '},
                            {' ', ' ', ' '}};
                        for (int a = 0; a < 3; a++) {
                            for (int b = 0; b<3; b++) {
                                childGameState[a][b] = gameState[a][b];
                            }
                        }

                        if (playerNum == 1) {
                            childGameState[i][j] = 'x';
                            nextMove = new int[]{i,j};
                            // the child has reverse player num as it is the other player's move
                            children.add(new StateTree(childGameState, this, childLeaves, 2, depth+1, nextMove)); 
                        } else {
                            childGameState[i][j] = 'o';
                            nextMove = new int[]{i,j};
                            children.add(new StateTree(childGameState, this, childLeaves, 1, depth+1, nextMove));
                        }
                    }
                }
            }
        }
        // At this point children are established and the heuristic function is allowed to be called if not a leaf
        
        evaluate();
    }
    private void evaluate() {
        if (isLeaf) {
            // Terminal case
            leafHeuristic();
        } else {
            // Recursive case
            // ensure that all children have evaluated themselves
            for (StateTree child : children) {
                child.populate();
            }
            // Now evaluate yourself
            heuristic();
            minMax();
        }
    }
    private void minMax() {
        int bestI = -1, bestJ=-1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (legalMoves[i][j]) {
                    if (playerNum == 1)
                        nodeValue = Integer.max(nodeValue, heuristicValue[i][j]);
                    else
                        nodeValue = Integer.min(nodeValue, heuristicValue[i][j]);
                    if (nodeValue == heuristicValue[i][j]) {
                        bestI = i;
                        bestJ = j;
                    }
                }
            }
        }
        if (parent == null) {
            if (bestI==-1&&bestJ==-1) {
                System.out.println("Something is wrong");
                // pick the first legal move, the game is unwinable
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if(legalMoves[i][j]) {
                            bestI = i;
                            bestJ = j;
                            i = 4;
                            j = 4;
                        }
                    }
                }
            }
            move = new int[]{bestI,bestJ};
        }
    }

    //Heuristic Expression
    private void leafHeuristic() {
        // establish the value of the board
        // set min and max to the value
        int boardValue = 0;
        // Our expression v = 10 or -10 if terminal state, v = 3X_2 + X_1 - (3O_2 + O_1)
        boardValue = terminalVal;
        if (boardValue == 0) {
            boardValue+=checkRows();
            boardValue+=checkCols();
            boardValue+=checkDiag();
        }
        if (terminalVal!=0 && depth==3) {
            if (playerNum == 1)
                nodeValue = Integer.MIN_VALUE+1;
            else
                nodeValue = Integer.MAX_VALUE-1;
        } else
            nodeValue = boardValue;
    }
    // Bubble up
    private void heuristic() {
        int[] childPos;
        // if player one heuristic for max
        if (playerNum == 1) {
            for (StateTree child : children) {
                childPos = child.getMove();
                heuristicValue[childPos[0]][childPos[1]] = child.nodeValue;
            }
        }
        // if player two heuristic for min
        else {
            for (StateTree child : children) {
                childPos = child.getMove();
                heuristicValue[childPos[0]][childPos[1]] = child.nodeValue;
            }
        }
    }

    private void legality() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameState[i][j] == ' ') {
                    legalMoves[i][j] = true;
                } else {
                    legalMoves[i][j] = false;
                }
            }
        }
    }

    // Helper Function returns the value of each row, column or diag
    private int checkRows() {
        int returnVal = 0; // value of all rows
        int xRows = 0, oRows = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameState[i][j]=='x')
                    xRows++;
                if (gameState[i][j]=='o')
                    oRows++;
            }
            if (xRows>0&&oRows>0) {
                //Nothing
            } else if (xRows>0) {
                if (xRows==2)
                    xRows++;
                returnVal+=xRows;
            } else if (oRows>0) {
                if (oRows==2)
                    oRows++;
                returnVal-=oRows;
            }
            xRows = 0;
            oRows = 0;
        }
        return returnVal; // return the vlaue of all rows
    }
    // j and i in gamestate are switched from previous function --- make note if this really is a solution
    private int checkCols() {
        int returnVal = 0; // value of all rows
        int xCols = 0, oCols = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameState[i][j]=='x')
                    xCols++;
                if (gameState[i][j]=='o')
                    oCols++;
            }
            if (xCols>0&&oCols>0) {
                //Nothing
            } else if (xCols>0) {
                if (xCols==2)
                    xCols++;
                returnVal+=xCols;
            } else if (oCols>0) {
                if (oCols==2)
                    oCols++;
                returnVal-=oCols;
            }
            xCols = 0;
            oCols = 0;
        }
        return returnVal; // return the vlaue of all cols
    }
    private int checkDiag() {
        int returnVal = 0;
        int xDia=0,oDia=0;
        for (int i = 0; i < 3; i++) {
            if (gameState[i][i] == 'x') {
                xDia++;
            }
            if (gameState[i][i] == 'o') {
                oDia++;
            }
        }
        if (xDia>0&&oDia>0) {
            //Nothing
        } else if (xDia>0) {
            if (xDia==2)
                xDia++;
            returnVal+=xDia;
        } else if (oDia>0) {
            if (oDia==2)
                oDia++;
            returnVal-=oDia;
        }
        xDia = 0;
        oDia = 0;
        for (int i = 0; i < 3; i++) {
            if (gameState[i][2-i] == 'x') {
                xDia++;
            }
            if (gameState[i][2-i] == 'o') {
                oDia++;
            }
        }
        if (xDia>0&&oDia>0) {
            //Nothing
        } else if (xDia>0) {
            if (xDia==2)
                xDia++;
            returnVal+=xDia;
        } else if (oDia>0) {
            if (oDia==2)
                oDia++;
            returnVal-=oDia;
        }
        
        return returnVal;
    }
    // return 1 if x, -1 if o, 0 if nonterminal
    public int checkTerminal() {
        int returnVal = 0;

        returnVal = checkTerminalDiag(); // check diagonals
        if (returnVal!=0) {
            return returnVal;
        }
        for (int i = 0; i < 3; i++) { // check rows and columns
            returnVal = checkTerminalRow(i);
            if(returnVal!=0){
                return returnVal;
            }
            returnVal = checkTerminalCol(i);
            if(returnVal!=0){
                return returnVal;
            }
        }
        return returnVal;
    }
    // This is a copy of the check win code from board - may change the code to make one obsolete
    // Helper functions for checking rows columns and diagonals
    private int checkTerminalDiag() {
        if (gameState[0][0]!=' '&&gameState[0][0]==gameState[1][1]&&gameState[1][1]==gameState[2][2]) {
            if (gameState[0][0]=='x') {
                return 10;
            } else {
                return -10;
            }
        }
        if (gameState[2][0]!=' '&&gameState[2][0]==gameState[1][1]&&gameState[1][1]==gameState[0][2]) {
            if (gameState[0][0]=='x') {
                return 10;
            } else {
                return -10;
            }
        }
        return 0;
    }

    private int checkTerminalRow(int y) {
        if (gameState[0][y]!=' '&&gameState[0][y]==gameState[1][y]&&gameState[1][y]==gameState[2][y]) {
            if (gameState[0][y]=='x') {
                return 10;
            } else {
                return -10;
            }
        }
        return 0;
    }

    private int checkTerminalCol(int x) {
        if (gameState[x][0]!=' '&&gameState[x][0]==gameState[x][1]&&gameState[x][1]==gameState[x][2]) {
            if (gameState[x][0]=='x') {
                return 10;
            } else {
                return -10;
            }
        }
        return 0;
    }
}
