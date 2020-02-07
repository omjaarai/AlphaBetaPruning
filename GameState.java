import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class GameState {
    // size( number of stones)
    private int size;
    // array to store the game state
    private boolean[] stones;
    // the last move
    private int lastMove;
    private double value;

    /**
     * Class constructor specifying the number of stones.
     */
    public GameState(int size) {

        this.size = size;

        // For convenience, we use 1-based index, and set 0 to be unavailable
        this.stones = new boolean[this.size + 1];
        this.stones[0] = false;

        // Set default state of stones to available
        for (int i = 1; i <= this.size; ++i) {
            this.stones[i] = true;
        }

        // Set the last move be -1
        this.lastMove = -1;
    }

    /**
     * Copy constructor
     */
    public GameState(GameState other) {
        this.size = other.size;
        this.stones = Arrays.copyOf(other.stones, other.stones.length);
        this.lastMove = other.lastMove;
    }


    /**
     * This method is used to compute a list of legal moves
     *
     * @return This is the list of state's moves
     */
    public List<Integer> getMoves() {

        List<Integer> moves = new ArrayList<>();
        int counter = 0;
        for (int i = 1; i < stones.length; i++) {
            if (!stones[i]) {
                counter++;
            }
        }

        if (counter == 0) {
            for (int i = 1; i < (float) size / 2; i++) {
                if (i % 2 != 0) {
                    moves.add(i);
                }
            }
        } else {
            for (int i = 1; i < stones.length; i++) {
                if (stones[i] && i % lastMove == 0) {
                    moves.add(i);
                } else if (stones[i] && lastMove % i == 0) {
                    moves.add(i);
                }
            }
        }
        return moves;

    }


    /**
     * This method is used to generate a list of successors using the getMoves() method
     *
     * @return This is the list of state's successors
     */
    public List<GameState> getSuccessors() {
        return this.getMoves().stream().map(move -> {
            var state = new GameState(this);
            state.removeStone(move);
            state.setLastMove(move);
            return state;
        }).collect(Collectors.toList());
    }


    /**
     * This method is used to evaluate a game state based on the given heuristic function
     *
     * @return int This is the static score of given state
     */
    public double evaluate() {

        // when the number of stones occupied are even then it's max's turn
        // when the number of stones occupied are odd then it's min's turn
        // variable to keep track of how many stones are taken
        int numStonesTaken = 0;

        // loop that counts how many stones are taken
        for (int i = 1; i < stones.length; i++) {
            if (stones[i] == false) {
                numStonesTaken++;
            }
        }

        // if no moves and stones taken are even return -1
        List<Integer> moves = getMoves();
        if (moves.size() == 0 && numStonesTaken % 2 == 0)
            return -1.0;
        // if no moves and stones taken are odd return 1
        if (moves.size() == 0 && numStonesTaken % 2 != 0)
            return 1.0;

        // if number of stones taken are even
        if (numStonesTaken % 2 == 0) {
            // if the stone[1] is occupied then return 0
            if (stones[1] == true)
                return 0;
            // if the last move is 1
            else if (lastMove == 1) {
                // and successors size is even return -0.5
                if (getSuccessors().size() % 2 == 0) {
                    return -0.5;
                } else
                    // otherwise return 0.5
                    return 0.5;

                // if the last move is prime
            } else if (Helper.isPrime(lastMove)) {
                // counter
                int c = 0;
                // loop to count all the moves that are factors of the last move
                for (int i = 0; i < moves.size(); i++) {
                    if (moves.get(i) % lastMove == 0) {
                        c++;
                    }
                }
                // if counter is even then return -0.7 else 0.7
                if (c % 2 == 0) {
                    return -0.7;
                } else
                    return 0.7;
            } else {
                //get the largest prime factor using the helper class
                int factor = Helper.getLargestPrimeFactor(lastMove);

                int c = 0;
                for (int i = 0; i < moves.size(); i++) {
                    if (moves.get(i) % factor == 0) {
                        c++;
                    }
                }
                if (c % 2 == 0) {
                    return -0.6;
                } else
                    return 0.6;
            }
        } 
     // else if number of stones taken are odd then repeat the same process
        else {
        
            if (stones[1] == true)
                return 0;
            else if (lastMove == 1) {
                if (getSuccessors().size() % 2 == 0) {
                    return 0.5;
                } else
                    return -0.5;
            } else if (Helper.isPrime(lastMove)) {

                int c = 0;
                for (int i = 0; i < moves.size(); i++) {
                    if (moves.get(i) % lastMove == 0) {
                        c++;
                    }
                }
                if (c % 2 == 0) {
                    return 0.7;
                } else
                    return -0.7;
            } else {
                int factor = Helper.getLargestPrimeFactor(lastMove);

                int c = 0;
                for (int i = 0; i < moves.size(); i++) {
                    if (moves.get(i) % factor == 0) {
                        c++;
                    }
                }
                if (c % 2 == 0) {
                    return 0.6;
                } else
                    return -0.6;
            }
        }

    }

    /**
     * This method is used to take a stone out
     *
     * @param idx Index of the taken stone
     */
    public void removeStone(int idx) {
        this.stones[idx] = false;
        this.lastMove = idx;
    }

    /**
     * These are get/set methods for a stone
     *
     * @param idx Index of the taken stone
     */
    public void setStone(int idx) {
        this.stones[idx] = true;
    }

    public boolean getStone(int idx) {
        return this.stones[idx];
    }

    /**
     * These are get/set methods for lastMove variable
     *
     * @param move Index of the taken stone
     */
    public void setLastMove(int move) {
        this.lastMove = move;
    }

    public int getLastMove() {
        return this.lastMove;
    }

    /**
     * This is get method for game size
     *
     * @return int the number of stones
     */
    public int getSize() {
        return this.size;
    }

    public void setValue(Double val) {
        value = val;
    }

    public double getValue() {
        return value;
    }

}
