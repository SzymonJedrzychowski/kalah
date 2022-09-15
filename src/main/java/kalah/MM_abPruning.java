package kalah;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;

public class MM_abPruning {
    int globalDepth;
    int moveCount;

    MM_abPruning(int globalDepth) {
        this.globalDepth = globalDepth;
    }

    public int move(game state) {
        int bestScore = -200 * state.playerToMove;
        int newScore;
        int bestMovePlace = 0;
        int alpha = -200;
        int beta = 200;

        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

        HashSet<Integer> legalMoves = state.getLegalMoves();
        game stateCopy;

        moveCount = 0;

        for (int moveIndex : legalMoves) {
            stateCopy = state.copy();
            stateCopy.move(moveIndex);
            moveCount += 1;

            newScore = deepMove(stateCopy, stateCopy.playerToMove == state.playerToMove, globalDepth - 1, alpha, beta);
            if ((newScore > bestScore && state.playerToMove == 1)
                    || (newScore < bestScore && state.playerToMove == -1)) {
                bestScore = newScore;
                bestMovePlace = moveIndex;
            }

            if (state.playerToMove == 1) {
                if (newScore >= beta) {
                    break;
                }
                alpha = Math.max(alpha, newScore);
            } else {
                if (newScore <= alpha) {
                    break;
                }
                beta = Math.min(beta, newScore);
            }
        }

        Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
        System.out.printf("%-30s time: %10d moveCount: %10d %n", "abPruning",
                timestamp2.getTime() - timestamp1.getTime(), moveCount);

        return bestMovePlace;
    }

    public int deepMove(game state, boolean samePlayer, int depth, int alpha, int beta) {
        int bestScore = -200 * state.playerToMove;
        int newScore;

        HashMap<Integer, Integer> results = state.isTerminal();

        if (results.get(0) == 1) {
            return results.get(1) * 100;
        }

        if (depth <= 0 && !samePlayer) {
            return state.board[6] - state.board[13];
        }
        
        HashSet<Integer> legalMoves = state.getLegalMoves();
        game stateCopy;
        
        for (int moveIndex : legalMoves) {
            stateCopy = state.copy();
            stateCopy.move(moveIndex);
            moveCount += 1;

            newScore = deepMove(stateCopy, stateCopy.playerToMove == state.playerToMove, depth - 1, alpha, beta);
            if ((newScore > bestScore && state.playerToMove == 1)
                    || (newScore < bestScore && state.playerToMove == -1)) {
                bestScore = newScore;
            }

            if (state.playerToMove == 1) {
                if (newScore >= beta) {
                    break;
                }
                alpha = Math.max(alpha, newScore);
            } else {
                if (newScore <= alpha) {
                    break;
                }
                beta = Math.min(beta, newScore);
            }
        }

        return bestScore;
    }
}
