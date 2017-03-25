/******************************************************************************
 *  Compilation: javac PercolationStats.java
 *  Execution: java PercolationStats
 *  Dependencies:
 *  Riccardo Coppola
 *
 ******************************************************************************/

public class PercolationStats {
    public PercolationStats(int n, int trials) {
        // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0) throw new java.lang.IllegalArgumentException();
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
        Percolation p = new Percolation(3);

        p.open(1, 2);
        p.open(2, 1);
        p.open(2, 2);
        p.open(3, 2);

        System.out.println("Opened: " + p.numberOfOpenSites());
        System.out.println("Percolates: " + p.percolates());
    }
}
