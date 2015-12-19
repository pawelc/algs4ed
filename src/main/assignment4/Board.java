import java.util.ArrayList;
import java.util.Arrays;

/**
 * immutable data type.
 * @author pawelc
 */
public class Board {
    private int[][] board;
    private int hamming = -1;
    private int manhattan = -1;
    private Board twin;
    private ArrayList<Board> neighbors;
    private int emptyBlockRow;
    private int emptyBlockColumn;
    private String toString;
    /**
     * construct a board from an N-by-N array of blocks. (where blocks[i][j] =
     * block in row i, column j)
     * @param blocks
     */
    public Board(int[][] blocks) {
        board = new int[blocks.length][];
        for (int i = 0; i < blocks.length; i++) {
            board[i] = new int[blocks[i].length];
            for (int j = 0; j < blocks[i].length; j++) {
                int value = blocks[i][j];
                if (value == 0) {
                    emptyBlockRow = i;
                    emptyBlockColumn = j;
                }
                board[i][j] = value;
            }
        }
    }

    private Board() {
    }

    private static Board createWithoutCopy(int[][] blocks){
        Board b = new Board();
        b.board = blocks;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                int value = blocks[i][j];
                if (value == 0) {
                    b.emptyBlockRow = i;
                    b.emptyBlockColumn = j;
                }
            }
        }
        return b;
    }

    /**
     * board dimension N.
     * @return
     */
    public int dimension() {
        return board.length;
    }

    /**
     * @return number of blocks out of place.
     */
    public int hamming() {
        if (hamming < 0) {
            hamming = 0;
            for (int i = 0; i < dimension() * dimension(); i++) {
                int row = i / dimension();
                int col = i % dimension();
                int val = board[row][col];
                if (val != 0 && val != i + 1) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    /**
     * @return sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        if (manhattan < 0) {
            manhattan = 0;
            for (int i = 0; i < dimension() * dimension(); i++) {
                int row = i / dimension();
                int col = i % dimension();
                int val = board[row][col];
                if (val != 0) {
                    int targetRow = (val - 1) / dimension();
                    int targetCol = (val - 1) % dimension();
                    manhattan += Math.abs(targetRow - row) + Math.abs(targetCol - col);
                }
            }
        }
        return manhattan;
    }

    /**
     * is this board the goal board?
     * @return
     */
    public boolean isGoal() {
        return hamming() == 0;
    }

    /**
     * @return a board obtained by exchanging two adjacent blocks in the same row
     */
    public Board twin() {
        if (twin == null) {
            int[][] twinBoard = copy(board);
            int adjucentCols = 0;
            for (int i = 0; i < dimension() * dimension(); i++) {
                int row = i / dimension();
                int col = i % dimension();
                if (twinBoard[row][col] == 0) {
                    adjucentCols = 0;
                    continue;
                }
                if (col == 0) {
                    adjucentCols = 0;
                }
                adjucentCols++;
                if (adjucentCols == 2) {
                    int tmp = twinBoard[row][col];
                    twinBoard[row][col] = twinBoard[row][col - 1];
                    twinBoard[row][col - 1] = tmp;
                    break;
                }
            }
            twin = createWithoutCopy(twinBoard);
        }
        return twin;
    }

    /**
     * does this board equal y?
     */
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y.getClass() != getClass()) {
            return false;
        }

        Board other = (Board) y;

        if (dimension() != other.dimension()) {
            return false;
        }

        for (int i = 0; i < dimension(); i++) {
            if (!Arrays.equals(this.board[i], other.board[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return all neighboring boards.
     */
    public Iterable<Board> neighbors() {
        if (neighbors == null) {
            neighbors = new ArrayList<Board>();
            if (emptyBlockColumn > 0) {
                int[][] neighborBoard = copy(board);
                int movingBlock = neighborBoard[emptyBlockRow][emptyBlockColumn - 1];
                neighborBoard[emptyBlockRow][emptyBlockColumn - 1] = 0;
                neighborBoard[emptyBlockRow][emptyBlockColumn] = movingBlock;
                neighbors.add(createWithoutCopy(neighborBoard));
            }
            if (emptyBlockColumn < dimension() - 1) {
                int[][] neighborBoard = copy(board);
                int movingBlock = neighborBoard[emptyBlockRow][emptyBlockColumn + 1];
                neighborBoard[emptyBlockRow][emptyBlockColumn + 1] = 0;
                neighborBoard[emptyBlockRow][emptyBlockColumn] = movingBlock;
                neighbors.add(createWithoutCopy(neighborBoard));
            }
            if (emptyBlockRow > 0) {
                int[][] neighborBoard = copy(board);
                int movingBlock = neighborBoard[emptyBlockRow - 1][emptyBlockColumn];
                neighborBoard[emptyBlockRow - 1][emptyBlockColumn] = 0;
                neighborBoard[emptyBlockRow][emptyBlockColumn] = movingBlock;
                neighbors.add(createWithoutCopy(neighborBoard));
            }
            if (emptyBlockRow < dimension() - 1) {
                int[][] neighborBoard = copy(board);
                int movingBlock = neighborBoard[emptyBlockRow + 1][emptyBlockColumn];
                neighborBoard[emptyBlockRow + 1][emptyBlockColumn] = 0;
                neighborBoard[emptyBlockRow][emptyBlockColumn] = movingBlock;
                neighbors.add(createWithoutCopy(neighborBoard));
            }
        }
        return neighbors;
    }

    /**
     * Copy.
     * @param original
     * @return copy
     */
    private int[][] copy(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }

    /**
     * @return string representation of the board (in the output format specified below).
     */
    public String toString() {
        if (toString == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(dimension()).append("\n");
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    sb.append(board[i][j]).append(" ");
                }
                sb.append("\n");
            }
            toString = sb.toString();
        }
        return toString;
    }
}
