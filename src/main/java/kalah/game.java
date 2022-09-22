package kalah;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

public class game {
    int[] board = new int[14];
    int playerToMove = 1;
    boolean rulesBroken = false;

    game() {
        resetState();
    }

    public void resetState() {
        for (int i = 0; i < 14; i++) {
            board[i] = 4;
        }

        board[6] = 0;
        board[13] = 0;
        playerToMove = 1;
        rulesBroken = false;
    }

    public ArrayList<Integer> getLegalMoves() {
        ArrayList<Integer> legalMoves = new ArrayList<>();
        if (playerToMove == 1) {
            for (int i = 0; i < 6; i++) {
                if (board[i] > 0) {
                    legalMoves.add(i);
                }
            }
        } else {
            for (int i = 7; i < 13; i++) {
                if (board[i] > 0) {
                    legalMoves.add(i - 7);
                }
            }
        }
        return legalMoves;
    }

    public HashMap<Integer, Integer> isTerminal() {
        HashMap<Integer, Integer> results = new HashMap<>();
        if (rulesBroken == true) {
            results.put(0, 1);
            results.put(1, -playerToMove);
            return results;
        }

        int sumPlayerOne = 0;
        int sumePlayerTwo = 0;
        for (int i = 0; i < 6; i++) {
            sumPlayerOne += board[i];
            sumePlayerTwo += board[i + 7];
        }

        if (playerToMove == 1 && sumPlayerOne == 0) {
            board[13] += sumePlayerTwo;
            for (int i = 7; i < 13; i++) {
                board[i] = 0;
            }
        } else if (playerToMove == -1 && sumePlayerTwo == 0) {
            board[6] += sumPlayerOne;
            for (int i = 0; i < 6; i++) {
                board[i] = 0;
            }
        }

        if (board[6] > 24) {
            results.put(0, 1);
            results.put(1, 1);
        } else if (board[13] > 24) {
            results.put(0, 1);
            results.put(1, -1);
        } else if (board[6] + board[13] == 48) {
            results.put(0, 1);
            results.put(1, 0);
        } else {
            results.put(0, 0);
            results.put(1, 0);
        }

        return results;
    }

    public void move(int place) {
        if (playerToMove == -1) {
            place += 7;
        }

        if (board[place] == 0) {
            rulesBroken = true;
            return;
        }

        int balls = board[place];
        board[place] = 0;
        while (balls > 0) {
            place = (place + 1) % 14;
            if (!((place == 6 && playerToMove == -1) || (place == 13 && playerToMove == 1))) {
                board[place] += 1;
                balls -= 1;
            }
        }

        if (board[place] == 1 && playerToMove == 1) {
            if (place >= 0 && place < 6 && board[12 - place] > 0) {
                board[6] += (board[place] + board[12 - place]);
                board[place] = 0;
                board[12 - place] = 0;
            }
        } else if (board[place] == 1 && playerToMove == -1) {
            if (place >= 7 && place < 13 && board[12 - place] > 0) {
                board[13] += (board[place] + board[12 - place]);
                board[place] = 0;
                board[12 - place] = 0;
            }
        }

        if (!((place == 6 && playerToMove == 1) || (place == 13 && playerToMove == -1))) {
            playerToMove *= -1;
        }
    }

    public void printBoard() {
        System.out.println("================ Player -1 ==============");
        System.out.printf("|    | %2d | %2d | %2d | %2d | %2d | %2d |    |%n", board[12], board[11], board[10],
                board[9],
                board[8], board[7]);
        System.out.printf("| %2d |====|====|====|====|====|====| %2d |%n", board[13], board[6]);
        System.out.printf("|    | %2d | %2d | %2d | %2d | %2d | %2d |    |%n", board[0], board[1], board[2], board[3],
                board[4], board[5]);
        System.out.println("================ Player 1 ===============");
        System.out.printf("Player to move: %d%n", playerToMove);
    }

    public game copy() {
        game newGame = new game();
        newGame.board = new int[14];
        for (int i = 0; i < 14; i++) {
            newGame.board[i] = board[i];
        }
        newGame.playerToMove = playerToMove;
        return newGame;
    }

    public boolean equals(game g){
        if(playerToMove == g.playerToMove && Arrays.equals(board, g.board)){
            return true;
        }
        return false;
    }
}
