package kalah;

import java.sql.Timestamp;
import java.util.HashMap;

public class MCTS_mcts implements gamePlayer {
    int simulationLimit;
    float explorationValue;
    boolean simulationLimitIsMoves;

    MCTS_mcts(int simulationLimit, boolean simulationLimitIsMoves, float explorationValue) {
        this.simulationLimit = simulationLimit;
        this.explorationValue = explorationValue;
        this.simulationLimitIsMoves = simulationLimitIsMoves;
    }

    @Override
    public int move(game state) {
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        Timestamp timestamp2;
        int moveCount = 0;

        MCTS_node currentNode = new MCTS_node(state, null);
        MCTS_node selectedNode;

        if (simulationLimitIsMoves == true) {
            while (moveCount < simulationLimit) {
                selectedNode = currentNode.select(explorationValue);
                while (selectedNode != null) {
                    selectedNode = selectedNode.select(explorationValue);
                }
                moveCount += 1;
            }
        } else {
            do {
                timestamp2 = new Timestamp(System.currentTimeMillis());
                selectedNode = currentNode.select(explorationValue);
                while (selectedNode != null) {
                    selectedNode = selectedNode.select(explorationValue);
                }
                moveCount += 1;
            } while (timestamp2.getTime() - timestamp1.getTime() < simulationLimit);
        }

        HashMap<Integer, Float> UCB = new HashMap<>();
        MCTS_node child;

        for (int moveIndex : currentNode.children.keySet()) {
            child = currentNode.children.get(moveIndex);
            if (state.playerToMove == 1) {
                UCB.put(moveIndex, ((float) child.reward / (float) child.visits));
            } else {
                UCB.put(moveIndex, ((float) -child.reward / (float) child.visits));
            }
        }

        float bestValue = Float.NEGATIVE_INFINITY;
        int bestMovePlace = -1;
        float moveValue;

        for (int moveIndex : UCB.keySet()) {
            moveValue = UCB.get(moveIndex);
            if (moveValue > bestValue) {
                bestValue = moveValue;
                bestMovePlace = moveIndex;
            }
        }

        timestamp2 = new Timestamp(System.currentTimeMillis());
        System.out.printf("%-30s time: %10d moveCount: %10d %n", "MCTS",
                timestamp2.getTime() - timestamp1.getTime(), moveCount);

        return bestMovePlace;
    }
}