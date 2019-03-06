package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int times;
    private int numberOfSite;
    private double[] threshold;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        times = T;
        threshold = new double[T];
        for (int i = 0; i < T; i++) {
            numberOfSite = 0;
            Percolation newPercolchecker = pf.make(N);
            while (!newPercolchecker.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                if (!newPercolchecker.isOpen(row, col)) {
                    newPercolchecker.open(row, col);
                    numberOfSite += 1;
                }
            }
            threshold[i] = (double) numberOfSite / (N * N);
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
        return mean() - (1.96 * stddev()) / Math.sqrt(times);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (1.96 * stddev()) / Math.sqrt(times);
    }

    /*public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats test = new PercolationStats(20, 10, pf);
        for (int i = 0; i < test.threshold.length; i++) {
            System.out.println(test.threshold[i]);
        }
        System.out.println(test.mean());
        System.out.println(test.stddev());
        System.out.println(test.confidenceHigh());
        System.out.println(test.confidenceLow());
    }*/
}

