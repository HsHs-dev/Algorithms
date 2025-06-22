import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final int trials;
    private final double[] fractions;
    private final double confidence95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        this.trials = trials;
        fractions = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation per = new Percolation(n);
            while (!per.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                per.open(row, col);
            }

            int opened = per.numberOfOpenSites();
            double fraction = (double) opened / (n * n);
            fractions[i] = fraction;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((confidence95 * stddev()) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((confidence95 * stddev()) / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats perstat = new PercolationStats(n, t);
        StdOut.println("mean = " + perstat.mean());
        StdOut.println("stddev = " + perstat.stddev());
        StdOut.println("95% confidence interval = [" + perstat.confidenceLo()
                + ", " + perstat.confidenceHi() + "]");
    }
}