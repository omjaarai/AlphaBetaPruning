import java.util.List;

public class AlphaBetaPruning {
    double score;
    int move;
    int numNodes;
    int numNodesEvaluated;
    int maxDepth;
    int branches;

    public AlphaBetaPruning() {
        score = 0;
        move = -1;
        numNodes = 0;
        numNodesEvaluated = 0;
        maxDepth = 0;
        branches = 0;
    }

    /**
     * This function will print out the information to the terminal, as specified in the homework
     * description.
     */
    public void printStats() {
        System.out.println("Move: " + move);
        System.out.println("Value: " + String.format("%.1f",score));
        System.out.println("Number of Nodes Visited: " + numNodes);
        System.out.println("Number of Nodes Evaluated: " + numNodesEvaluated);
        System.out.println("Max Depth Reached: " + maxDepth);
        System.out.println(
            "Avg Effective Branching Factor: " + String.format("%.1f", (float) branches / (numNodes - numNodesEvaluated)));
    }

    /**
     * This function will start the alpha-beta search
     * 
     * @param state This is the current game state
     * @param depth This is the specified search depth
     */
    public void run(GameState state, int depth) {
        int count = 0;
        for (int i = 1; i <= state.getSize(); i++) {
            if (!state.getStone(i)) {
                count++;
            }
        }

        if (count % 2 == 0)
            score =
                alphabeta(state, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);


        else
            score =
                alphabeta(state, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false);

    }

    /**
     * This method is used to implement alpha-beta pruning for both 2 players
     * 
     * @param state This is the current game state
     * @param depth Current depth of search
     * @param alpha Current Alpha value
     * @param beta Current Beta value
     * @param maxPlayer True if player is Max Player; Otherwise, false
     * @return int This is the number indicating score of the best next move
     */
    private double alphabeta(GameState state, int depth, double alpha, double beta,
        boolean maxPlayer) {
        int level = -1;
        numNodes++;
        if (!maxPlayer)
            return min(state, depth, alpha, beta, level);

        else
            return max(state, depth, alpha, beta, level);
    }

    private double max(GameState state, int depth, double alpha, double beta, int level) {
        level++;
        if (maxDepth < level)
            maxDepth = level;
        List<GameState> successors = state.getSuccessors();
        if (successors.size() == 0 || depth == 0) {
            numNodesEvaluated++;
            return state.evaluate();
        } else {
            state.setValue(Double.NEGATIVE_INFINITY);
            for (GameState successor : successors) {
                numNodes++;
                branches++;
                double minimum = min(successor, depth - 1, alpha, beta, level);
                if (minimum > state.getValue()) {
                    state.setValue(minimum);
                    if (level == 0) {
                        move = successor.getLastMove();
                    }
                }
                if (state.getValue() >= beta) {
                    return state.getValue();
                }
                alpha = alpha < state.getValue() ? state.getValue() : alpha;
            }
            return state.getValue();
        }

    }

    private double min(GameState state, int depth, double alpha, double beta, int level) {
        level++;
        if (maxDepth < level)
            maxDepth = level;
        List<GameState> successors = state.getSuccessors();
        if (successors.size() == 0 || depth == 0) {
            numNodesEvaluated++;
            return state.evaluate();
        } else {
            state.setValue(Double.POSITIVE_INFINITY);
            for (GameState successor : successors) {
                numNodes++;
                branches++;
                double maximum = max(successor, depth - 1, alpha, beta, level);
                if (maximum < state.getValue()) {
                    state.setValue(maximum);
                    if (level == 0) {
                        move = successor.getLastMove();
                    }
                }
                if (state.getValue() <= alpha) {
                    return state.getValue();
                }
                beta = beta > state.getValue() ? state.getValue() : beta;

            }
            return state.getValue();
        }
    }


}
