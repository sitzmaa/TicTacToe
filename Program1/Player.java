package Program1;
import java.util.Scanner;

public abstract class Player {
    public int playerNum; // is this player x
    public Scanner reader;
    public Player() {}
    public Player(int num) {
        this.playerNum = num;
        reader = new Scanner(System.in);
    }

    public abstract int[] Prompt(Board b);
}
