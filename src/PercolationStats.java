/******************************************************************************
 *  Compilation: javac PercolationStats.java
 *  Execution: java PercolationStats
 *  Dependencies:
 *  Riccardo Coppola
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    public PercolationStats(int n, int trials) {
        // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0) throw new java.lang.IllegalArgumentException("Pass n and T as arguments");

        for (int i = 0; i < trials; i++) {
            this.performTrial(n);
        }
    }

    private void performTrial(int n) {
        Percolation p = new Percolation(n);

        while (!p.percolates()) {
            int siteRow = StdRandom.uniform(n) + 1;
            int siteCol = StdRandom.uniform(n) + 1;

            if (!p.isOpen(siteRow, siteCol)) {
                p.open(siteRow, siteCol);

                System.out.println("Opened: " + p.numberOfOpenSites());
                System.out.println("Percolates: " + p.percolates());
            }
        }
    }

    public double mean() {
        // sample mean of percolation threshold

        return 0.0;
    }

    public double stddev() {
        // sample standard deviation of percolation threshold

        return 0.0;
    }

    public double confidenceLo() {
        // low  endpoint of 95% confidence interval

        return 0.0;
    }

    public double confidenceHi() {
        // high endpoint of 95% confidence interval

        return 0.0;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        System.out.println("Performing " + trials + " trials on grids of side: " + n);

        new PercolationStats(n, trials);
    }
}
