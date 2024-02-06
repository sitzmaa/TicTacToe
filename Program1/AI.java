package Program1;

import java.util.Scanner;

public class AI extends Player {
    private boolean[] occupied;
    public AI(int num) {
        this.playerNum = num;
        reader = new Scanner(System.in);
        occupied = new boolean[10];
    }

    public int[] Prompt(Board b) {
        int[]returnVal = new int[2];
        scanBoard(b);
        return returnVal;
    }

    // looks at available moves
    private int miniMax(Board b) {
        return 0;
    }

    // looks for empty spaces
    private void scanBoard(Board b) {
        int x = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                x++;
                occupied[x] = b.viewBoard(i, j);
            }
        }
    }

    private void trimSymmetry(Board b) {

    }

}
