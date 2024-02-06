package Program1;

public class Main {
    public static void main(String[] args) {
        int debug = 0;
        GameManager gm = new GameManager();
        // Debug check
        if (args.length >= 1) {
            if (args[0].equals("DEBUG")) {
                debug = 1;
            }
        }
        
        if (debug == 0) {
            gm.RunStandard();
        }
        else {
            gm.RunDebug();
        }
    }
}