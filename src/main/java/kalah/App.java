package kalah;

import java.util.HashMap;

public class App {
    public static void main(String[] args) {
        int move = 0;
        game game = new game();
        HashMap<Integer, Integer> results = new HashMap<>();

        MM_minimax player1 = new MM_minimax(1);
        MM_minimax player2 = new MM_minimax(1);

        while (true) {
            results = game.isTerminal();

            game.printBoard();
            if (results.get(0) == 1) {
                break;
            }
            if (game.playerToMove == 1) {
                move = player1.move(game);
            } else {
                move = player2.move(game);
            }
            game.move(move);
        }
    }
}