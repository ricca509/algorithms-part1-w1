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
    private WeightedQuickUnionUF uf;
    private int cols;
    private int virtualTopIndex;
    private int virtualBottomIndex;

    public Percolation(int n) {
        // create n-by-n grid, with all sites blocked
        if (n <= 0)
            throw new IllegalArgumentException("n needs to be > 0");

        this.cols = n;
        this.grid = new boolean[n * n];
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.setVirtualTopIndex(n * n);
        this.setVirtualBottomIndex(n * n + 1);

        // Connect virtual top to first row
        for (int i = 0; i < this.cols - 1; i++) this.uf.union(this.getVirtualTopIndex(), i);
        // Connect virtual bottom to last row
        for (int i = n * n - this.cols; i < n * n - 1; i++) this.uf.union(this.getVirtualBottomIndex(), i);
    }

    public int getVirtualTopIndex() {
        return virtualTopIndex;
    }

    public void setVirtualTopIndex(int virtualTopIndex) {
        this.virtualTopIndex = virtualTopIndex;
    }

    public int getVirtualBottomIndex() {
        return virtualBottomIndex;
    }

    public void setVirtualBottomIndex(int virtualBottomIndex) {
        this.virtualBottomIndex = virtualBottomIndex;
    }

    private boolean areIndexesInRange(int row, int col) {
        if (row > 0 &&
            row <= this.cols &&
            col > 0 &&
            col <= this.cols) {
            return true;
        }

        return false;
    }

    private int xyTo1D(int row, int col) {
        return (this.cols * (row - 1) + col) - 1;
    }

    private int[][] getAdjacentSites(int row, int col) {
        int[][] adjacents = new int[][]{
            {row - 1, col},
            {row + 1, col},
            {row, col - 1},
            {row, col + 1}
        };

        return adjacents;
    }

    public void open(int row, int col) {
        if (!this.areIndexesInRange(row, col)) throw new java.lang.IndexOutOfBoundsException();
        int pointToBeOpened = this.xyTo1D(row, col);

        // open site (row, col) if it is not open already
        this.grid[pointToBeOpened] = true;
        // Connect it to all open adjacent sites:
        int[][] adjacents = this.getAdjacentSites(row, col);

        for (int[] adjacent : adjacents) {
            if (this.areIndexesInRange(adjacent[0], adjacent[1]) &&
                this.isOpen(adjacent[0], adjacent[1])) {
                int adjacentOpenSite = this.xyTo1D(adjacent[0], adjacent[1]);

                this.uf.union(pointToBeOpened, adjacentOpenSite);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (!this.areIndexesInRange(row, col)) throw new java.lang.IndexOutOfBoundsException();

        return this.grid[this.xyTo1D(row, col)];
    }

    public boolean isFull(int row, int col) {
        if (!this.areIndexesInRange(row, col)) throw new java.lang.IndexOutOfBoundsException();
        int point = this.xyTo1D(row, col);

        boolean connected = this.uf.connected(point, this.getVirtualTopIndex());

        return this.isOpen(row, col) && connected;
    }

    public int numberOfOpenSites() {
        int opened = 0;
        for (boolean elem : this.grid) {
            if (elem) opened++;
        }

        return opened;
    }

    public boolean percolates() {
        return this.uf.connected(this.getVirtualTopIndex(), this.getVirtualBottomIndex());
    }

    public static void main(String[] args) {
        System.out.println("Percolation");
        Percolation p = new Percolation(3);

        p.open(1, 2);
        p.open(2, 1);
        p.open(2, 2);
        p.open(3, 2);

        System.out.println("Opened: " + p.numberOfOpenSites());
        System.out.println("Percolates: " + p.percolates());
    }
}
