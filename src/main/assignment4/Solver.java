import java.util.Comparator;
import java.util.concurrent.CountDownLatch;

/**
 * Sovlves puzzle.
 */
public class Solver {

    private Stack<Board> solution = new Stack<Board>();

    /**
     * To hold board in priority queue.
     */
    private static class BoardInternal {
        /**
         * Board.
         */
        private Board board;
        /**
         * Move.
         */
        private int move;
        /**
         * Previous board.
         */
        private BoardInternal previous;

        /**
         * Constructor.
         * @param board
         * @param move
         * @param previous
         */
        public BoardInternal(Board board, int move, BoardInternal previous) {
            super();
            this.board = board;
            this.move = move;
            this.previous = previous;
        }
    }

    private static class SolverTask implements Runnable {
        /**
         * Board.
         */
        private Board board;
        
        private volatile Stack<Board> solution = new Stack<Board>();
        
        private volatile boolean stopped = false;
        
        private CountDownLatch latch;
        
        SolverTask(Board board, CountDownLatch latch) {
            this.board = board;
            this.latch = latch;
        }
        
        public void run() {
            MinPQ<BoardInternal> pq = new MinPQ<BoardInternal>(new Comparator<BoardInternal>() {
                public int compare(BoardInternal b1, BoardInternal b2) {
                    if ((b1.board.manhattan() + b1.move) > (b2.board.manhattan() + b2.move)) {
                        return 1;
                    } else if ((b1.board.manhattan() + b1.move) < (b2.board.manhattan() + b2.move)) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
            pq.insert(new BoardInternal(board, 0, null));
            while (!pq.isEmpty() && !stopped) {
                BoardInternal minBoard = pq.delMin();
//                System.out.println(minBoard.board);
//                System.out.println(minBoard.board.manhattan());
                if (minBoard.board.manhattan() == 0) {
                    Stack<Board> foundSolution = new Stack<Board>();
                    for (BoardInternal solutionBoard = minBoard;  solutionBoard != null;
                            solutionBoard = solutionBoard.previous) {
                        foundSolution.push(solutionBoard.board);
                    }
                    solution = foundSolution;
                    break;
                }
                for (Board neighbor : minBoard.board.neighbors()) {
                    if (minBoard.previous == null || !neighbor.equals(minBoard.previous.board)) {
                        pq.insert(new BoardInternal(neighbor, minBoard.move + 1, minBoard));
                    }
                }
            }
            latch.countDown();
        }
    }

    /**
     * find a solution to the initial board (using the A* algorithm).
     * @param initial
     *            board.
     */
    public Solver(final Board initial) {
        CountDownLatch latch = new CountDownLatch(1);
        SolverTask taskForInitial = new SolverTask(initial, latch);
        SolverTask taskForTwin = new SolverTask(initial.twin(), latch);
        new Thread(taskForInitial).start();
        new Thread(taskForTwin).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (taskForInitial.solution.size() > 0) {
            solution = taskForInitial.solution;
        }
        taskForInitial.stopped = true;
        taskForTwin.stopped = true;
    }

    /**
     * is the initial board solvable?
     * @return
     */
    public boolean isSolvable() {
        return solution.size() > 0;
    }

    /**
     * @return min number of moves to solve initial board; -1 if no solution.
     */
    public int moves() {
        return solution.size() - 1;
    }

    /**
     * @return sequence of boards in a shortest solution; null if no solution
     */
    public Iterable<Board> solution() {
        return solution;
    }

    /**
     * solve a slider puzzle (given below).
     * @param args parameters
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }

}
