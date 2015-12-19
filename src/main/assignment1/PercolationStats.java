/**
 * Perform T independent computational experiments on an N-by-N grid.
 */
public class PercolationStats {

    /**
     * Confidence.
     */
    private static final double Q = 1.96;
    /**
     * Mean of the experiment.
     */
    private double mean;
    /**
     * Stddev of the experiment.
     */
    private double stddev;

    /**
     * Create experiment.
     * @param n grid size.
     * @param t number of experiments.
     */
    public PercolationStats(final int n, final int t) {
        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException();
        }
        double[] s = new double[t];
        for (int i = 0; i < t; i++) {
            Percolation p = new Percolation(n);
            int opened = 0;
            while (!p.percolates()) {
                int r = StdRandom.uniform(1, n + 1);
                int c = StdRandom.uniform(1, n + 1);
                if (p.isOpen(r, c)) {
                    continue;
                }
                p.open(r, c);
                opened++;
            }
            s[i] = (double) opened / (n * n);
        }

        mean = StdStats.mean(s);
        stddev = StdStats.stddev(s);
    }

    /**
     * Sample mean of percolation threshold.
     * @return mean of the experiment
     */
    public double mean() {
        return mean;
    }

    /**
     * sample standard deviation of percolation threshold.
     * @return stdev of the experiment
     */
    public double stddev() {
        return stddev;
    }

    /**
     * test client, described below.
     * @param args arguments
     */
    public static void main(final String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats s = new PercolationStats(n, t);
        System.out.println("mean                    = " + s.mean());
        System.out.println("stddev                  = " + s.stddev());
        System.out.println("95% confidence interval = "
                + (s.mean() - Q * s.stddev() / Math.sqrt(t)) + ", "
                + (s.mean() + Q * s.stddev() / Math.sqrt(t)));
    }

}
