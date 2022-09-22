package kalah;

import java.util.Scanner;

public class humanPlayer implements gamePlayer {
    public int move(game state){
        int move;
        Scanner scanner = new Scanner(System.in);
        try {
            move = scanner.nextInt();
        } catch (Exception e) {
            scanner.close();
            return -1;
        }
        scanner.close();
        return move;
    }
}
