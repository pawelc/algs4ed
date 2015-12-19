/**
 * Check if system percolate.
 */
public class Percolation {
    /**
     * Contains sites.
     */
    private int[] id;
    /**
     * Contains sites.
     */
    private int[] id2;
    /**
     * Sizes of the connected components.
     */
    private int[] sz;
    /**
     * Sizes of the connected components.
     */
    private int[] sz2;
    /**
     * Grid the contains opened/closed states.
     */
    private boolean[][] grid;
    /**
     * N-N grid.
     */
    private int n;

    /**
     * Create N-by-N grid, with all sites blocked.
     *
     * @param size
     *            size of the grid.
     */
    public Percolation(final int size) {
        this.n = size;
        this.id = new int[n * n + 2];
        this.sz = new int[n * n + 2];
        this.id2 = new int[n * n + 1];
        this.sz2 = new int[n * n + 1];
        this.grid = new boolean[n][n];
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
            sz[i] = 1;
            if (i < id.length - 1) {
                id2[i] = i;
                sz2[i] = 1;
            }
        }
    }

    /**
     * Gets root.
     * @param i
     *            element
     * @param idp idp
     * @return root
     */
    private int root(final int i, final int[] idp) {
        int k = i;
        while (k != idp[k]) {
            idp[k] = idp[idp[k]];
            k = idp[k];
        }
        return k;
    }

    /**
     * Open site (row i, column j) if it is not already.
     * @param i
     *            row in the grid.
     * @param j
     *            column in the grid.
     */
    public void open(final int i, final int j) {
        grid[i - 1][j - 1] = true;
        if (i > 1 && grid[i - 2][j - 1]) {
            union(i, j, i - 1, j, id, sz);
            union(i, j, i - 1, j, id2, sz2);
        }
        if (i < n && grid[i][j - 1]) {
            union(i, j, i + 1, j, id, sz);
            union(i, j, i + 1, j, id2, sz2);
        }
        if (j > 1 && grid[i - 1][j - 2]) {
            union(i, j, i, j - 1, id, sz);
            union(i, j, i, j - 1, id2, sz2);
        }
        if (j < n && grid[i - 1][j]) {
            union(i, j, i, j + 1, id, sz);
            union(i, j, i, j + 1, id2, sz2);
        }
        if (i == 1) {
            union(i, j, n, n + 1, id, sz);
            union(i, j, n, n + 1, id2, sz2);
        }
        if (i == n) {
            union(i, j, n, n + 2, id, sz);
        }
    }

    /**
     * Union of the sites in the grid.
     * @param i1
     *            row
     * @param j1
     *            column
     * @param i2
     *            row
     * @param j2
     *            column
     * @param idp
     *            idp
     * @param szp
     *            szp
     */
    private void union(final int i1, final int j1, final int i2, final int j2, final int[] idp, final int[] szp) {
        int p = to1D(i1, j1);
        int q = to1D(i2, j2);
        int rp = root(p, idp);
        int rq = root(q, idp);
        if (szp[rp] < szp[rq]) {
            idp[rp] = rq;
            szp[rq] += szp[rp];
        } else {
            idp[rq] = rp;
            szp[rp] += szp[rq];
        }
    }

    /**
     * is site (row i, column j) open?
     * @param i
     *            row
     * @param j
     *            column
     * @return is site open
     */
    public boolean isOpen(final int i, final int j) {
        if (i < 1 || i > n || j < 1 || j > n) {
            throw new IndexOutOfBoundsException();
        }
        return grid[i - 1][j - 1];
    }

    /**
     * is site (row i, column j) full?
     * @param i
     *            row
     * @param j
     *            column
     * @return is site full
     */
    public boolean isFull(final int i, final int j) {
        return isOpen(i, j) && connected(i, j, n, n + 1, id2);
    }

    /**
     * does the system percolate?
     * @return does it percolate
     */
    public boolean percolates() {
        return connected(n, n + 1, n, n + 2, id);
    }

    /**
     * Connected?
     * @param i1
     *            row
     * @param j1
     *            column
     * @param i2
     *            row
     * @param j2
     *            column
     * @param idp idp
     * @return are connected
     */
    private boolean connected(final int i1, final int j1, final int i2,
            final int j2, final int[] idp) {
        int p = to1D(i1, j1);
        int q = to1D(i2, j2);
        return root(p, idp) == root(q, idp);
    }

    /**
     * From 2D to 1D coordinates.
     * @param i
     *            row
     * @param j
     *            column
     * @return 1D coordinate
     */
    private int to1D(final int i, final int j) {
        return (i - 1) * n + j - 1;
    }

}
