package Program1;

import java.util.Scanner;

public class AI extends Player {
    private char[][] gameState;
    public AI(int num) {
        this.playerNum = num;
        reader = new Scanner(System.in);
    }

    public int[] Prompt(Board b) {
        scanBoard(b);
        return heuristic();
    }

    // looks for empty spaces
    private void scanBoard(Board b) {
        gameState = b.viewBoard();
    }

    // Must be used after scan board
    private int[] heuristic() {
        StateTree st = new StateTree(gameState, null, false, playerNum, 0, null);
        st.populate();
        return st.getMove();
    }

}
