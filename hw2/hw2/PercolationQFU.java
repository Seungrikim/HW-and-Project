package hw2;

import edu.princeton.cs.algs4.QuickUnionUF;

public class PercolationQFU {
    private boolean[][] grid;
    private int openSite;
    private int top;
    private int bottom;
    private int gridSize;
    private int gridLength;
    private QuickUnionUF tracker;
    private QuickUnionUF fullTracker;

    // create N-by-N grid, with all sites initially blocked
    public PercolationQFU(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Size of grid must be greater than 0 * 0");
        }
        gridLength = N;
        gridSize = N * N + 2;
        tracker = new QuickUnionUF(gridSize);
        fullTracker = new QuickUnionUF(gridSize);
        openSite = 0;
        top = 0;
        bottom = gridSize - 1;
        grid = new boolean[N][N];
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            int index = xyTo1D(row, col);
            if (index <= gridLength) {
                tracker.union(top, index);
                fullTracker.union(top, index);
            } else if (index >= bottom - gridLength) {
                tracker.union(bottom, index);
            }
            openSite += 1;
            //need to connect check
            unionHelper(row, col, row + 1, col);
            unionHelper(row, col, row - 1, col);
            unionHelper(row, col, row, col - 1);
            unionHelper(row, col, row, col + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isInGrid(row, col)) {
            throw new IndexOutOfBoundsException("One of Input is Out of Bound");
        }
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isInGrid(row, col)) {
            throw new IndexOutOfBoundsException("One of Input is Out of Bound");
        }
        int index = xyTo1D(row, col);
        if (fullTracker.connected(top, index)) {
            return true;
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSite;
    }

    // does the system percolate?
    public boolean percolates() {
        return (tracker.connected(top, bottom) || gridLength == 1 && isOpen(0, 0));
    }

    private int xyTo1D(int r, int c) {
        return r * gridLength + c + 1;
    }

    private void unionHelper(int oriRow, int oriCol, int nearRow, int nearCol) {
        if (isInGrid(nearRow, nearCol) && isOpen(nearRow, nearCol)) {
            int oriIndex = xyTo1D(oriRow, oriCol);
            int nearIndex = xyTo1D(nearRow, nearCol);
            tracker.union(oriIndex, nearIndex);
            fullTracker.union(oriIndex, nearIndex);
        }
    }

    private boolean isInGrid(int row, int col) {
        if (row < 0 || row > gridLength - 1 || col < 0 || col > gridLength - 1) {
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        Percolation test = new Percolation(3);
        System.out.println(test.isOpen(0, 0)); //false
        test.open(0, 0);
        System.out.println(test.isOpen(0, 0)); //true
        /*test.open(0, 4);
        test.open(4, 0);
        test.open(4, 4);
        test.open(1,1);*/
        System.out.println(test.isFull(1, 1)); //false
        System.out.println(test.isFull(0, 0)); //true
        test.open(1, 1);
        test.open(1, 0);
        System.out.println(test.isOpen(1, 0)); //true
        System.out.println(test.isFull(1, 0)); //true
        System.out.println(test.percolates()); //false
        test.open(2, 1);
        System.out.println(test.percolates()); //true
        test.open(2, 2);
    }

}
