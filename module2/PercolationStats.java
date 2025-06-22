import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final int TRIALS;
    private final double[] FRACTIONS;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) throws IllegalAccessException {

        TRIALS = trials;
        FRACTIONS = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation per = new Percolation(n);
            while (!per.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                per.open(row, col);
            }

            int opened = per.numberOfOpenSites();
            double fraction = (double) opened / (n * n);
            FRACTIONS[i] = fraction;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(FRACTIONS);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(FRACTIONS);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(TRIALS));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(TRIALS));
    }

    // test client (see below)
    public static void main(String[] args) throws IllegalAccessException {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats perstat = new PercolationStats(n, t);
        StdOut.println("mean = " + perstat.mean());
        StdOut.println("stddev = " + perstat.stddev());
        StdOut.println("95% confidence interval = [" + perstat.confidenceLo()
                + ", " + perstat.confidenceHi() + "]");
    }
}