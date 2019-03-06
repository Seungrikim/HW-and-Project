package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    int T;
    int numberOfSite;
    int[] threshold;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        T = T;
        numberOfSite = 0;
        Percolation newPercolchecker = pf.make(N);
        for (int i = 0; i < T; i++) {
            while (!newPercolchecker.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                newPercolchecker.open(row, col);
                numberOfSite++;
            }
            threshold[i] = numberOfSite / N * N;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev()  {
        return StdStats.stddev(threshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(T));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(T));
    }

    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats test = new PercolationStats(20, 10, pf);
    }
}

