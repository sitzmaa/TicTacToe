package Program1;

import java.util.Scanner;

public class GameManager {
    private Scanner reader;
    private Player player1, player2;
    public GameManager(){
        reader = new Scanner(System.in);
    }
    public void RunStandard() {
        // Regular Game
        boolean p1 = false,p2 = false; // False = human, true = AI
        // Who goes first
        int coinFlip = (int)Math.random(); // equals 1 or 0
        if (coinFlip==0) {
            // Human is player 1
            p2 = true;
        } else {
            // Human is player 2
            p1 = true;
        }
        StartGame(p1, p2, false);
    }
    public void RunDebug() {
        // Debug game
        System.out.printf("Welcome to Debug Mode\n"+
        "Please select game options:\n");

        // Determine who is an AI
        boolean debugP1 = false, debugP2 = false;
        System.out.printf("Who is an AI?\nnone - empty string\nplayer 1 - 1\nplayer 2 - 2\nboth - 12 or 21\n");
        String cmd = reader.nextLine().toLowerCase();
        if(!cmd.isEmpty()) {
            if(cmd.contains("1")) {
                debugP1 = true;
            }
            if(cmd.contains("2")) {
                debugP2 = true;
            }
        }

        StartGame(debugP1, debugP2, true);
    }

    public void StartGame(boolean p1, boolean p2, boolean debug) {
        if (p1) {
            player1 = new AI(1);
        } else {player1 = new Human(1);}

        if (p2) {
            player2 = new AI(2);
        } else {player2 = new Human(2);}

        Board board = new Board();
        int gameLoop = 0;
        int[] move;
        while (gameLoop == 0) {
            move = player1.Prompt(board);
            while (board.playerMove(move[0], move[1], 'x') == -1) {
                System.out.println("Illigal move: please retry");
                move = player1.Prompt(board);
            }
            gameLoop = board.checkWin();
            if (gameLoop!=0)
                break;
            move = player2.Prompt(board);
            while (board.playerMove(move[0], move[1], 'o') == -1) {
                System.out.println("Illigal move: please retry");
                move = player2.Prompt(board);
            }
            gameLoop = board.checkWin();
        }
        board.printBoard();
        System.out.printf("Game Over: Player%d wins!\n", gameLoop);
    }
}
