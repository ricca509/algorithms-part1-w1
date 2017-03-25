/******************************************************************************
 *  Compilation: javac Percolation.java
 *  Execution: java Percolation
 *  Dependencies:
 *  Riccardo Coppola
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] grid;
    private WeightedQuickUnionUF fullyConnectedUf, topOnlyConnectedUf;
    private int cols;
    private int virtualTopIndex;
    private int virtualBottomIndex;
    private int openSites;

    public Percolation(int n) {
        // create n-by-n grid, with all sites blocked
        if (n <= 0) throw new IllegalArgumentException("n needs to be > 0");

        this.cols = n;
        this.grid = new boolean[n * n];
        this.fullyConnectedUf = new WeightedQuickUnionUF(n * n + 2);
        this.topOnlyConnectedUf = new WeightedQuickUnionUF(n * n + 2);
        this.virtualTopIndex = n * n;
        this.virtualBottomIndex = n * n + 1;
        this.openSites = 0;

        // Connect virtual top to first row
        for (int i = 0; i < this.cols; i++) {
            this.fullyConnectedUf.union(this.virtualTopIndex, i);
            this.topOnlyConnectedUf.union(this.virtualTopIndex, i);
        }
        // Connect virtual bottom to last row
        for (int i = n * n - this.cols; i < n * n; i++) {
            this.fullyConnectedUf.union(this.virtualBottomIndex, i);
        }
    }

    private boolean areIndexesInRange(int row, int col) {
        return row > 0 && row <= this.cols && col > 0 && col <= this.cols;
    }

    private int xyTo1D(int row, int col) {
        return (this.cols * (row - 1) + col) - 1;
    }

    private int[][] getAdjacentSites(int row, int col) {
        return new int[][]{
            {row - 1, col},
            {row + 1, col},
            {row, col - 1},
            {row, col + 1}
        };
    }

    public void open(int row, int col) {
        if (!this.areIndexesInRange(row, col)) throw new java.lang.IndexOutOfBoundsException();
        if (this.isOpen(row, col)) return;

        int pointToBeOpened = this.xyTo1D(row, col);

        // open site (row, col)
        this.grid[pointToBeOpened] = true;
        this.openSites++;
        // Connect it to all open adjacent sites:
        int[][] adjacents = this.getAdjacentSites(row, col);

        for (int[] adjacent : adjacents) {
            if (this.areIndexesInRange(adjacent[0], adjacent[1]) &&
                this.isOpen(adjacent[0], adjacent[1])) {
                int adjacentOpenSite = this.xyTo1D(adjacent[0], adjacent[1]);

                this.fullyConnectedUf.union(pointToBeOpened, adjacentOpenSite);
                this.topOnlyConnectedUf.union(pointToBeOpened, adjacentOpenSite);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (!this.areIndexesInRange(row, col)) throw new java.lang.IndexOutOfBoundsException();

        return this.grid[this.xyTo1D(row, col)];
    }

    public boolean isFull(int row, int col) {
        if (!this.areIndexesInRange(row, col)) throw new java.lang.IndexOutOfBoundsException();

        return this.isOpen(row, col) && this.topOnlyConnectedUf.connected(this.xyTo1D(row, col), this.virtualTopIndex);
    }

    public int numberOfOpenSites() {
        return this.openSites;
    }

    public boolean percolates() {
        return this.fullyConnectedUf.connected(this.virtualTopIndex, this.virtualBottomIndex);
    }

    public static void main(String[] args) {
        System.out.println("Percolation");
    }
}
