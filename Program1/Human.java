package Program1;

import java.util.Scanner;

public class Human extends Player {

    public Human(int num) {
        this.playerNum = num;
        reader = new Scanner(System.in);
    }
    
    public int[] Prompt(Board b) {
        b.printBoard();
        System.out.printf("Player%d: where would you like to place? x y\n", playerNum);
        int[] returnVal = new int[2];
        // Flag to track if input is valid
        boolean isValidInput = false;

        while (!isValidInput) {
            String cmd = reader.nextLine();
            System.out.println();
            // Split the input string by spaces
            String[] cmdSplit = cmd.trim().split("\\s+");

            // Check if there are exactly two integers
            if (cmdSplit.length == 2) {
                try {
                    // Parse the integers
                    returnVal[0] = Integer.parseInt(cmdSplit[0]);
                    returnVal[1] = Integer.parseInt(cmdSplit[1]);
                    if ((returnVal[0]<4&&returnVal[0]>0)&&(returnVal[1]<4&&returnVal[1]>0)) {
                        isValidInput = true; // Set flag to true if input is valid
                    } else {
                        b.printBoard();
                        System.out.println("Invalid input. Coordinates must be within the board");
                    }
                } catch (NumberFormatException e) {
                    b.printBoard();
                    System.out.println("Invalid input. Please enter two integers separated by a space.");
                }
            } else {
                b.printBoard();
                System.out.println("Invalid input. Please enter two integers separated by a space.");
            }
        }
        return returnVal;
    }
}
