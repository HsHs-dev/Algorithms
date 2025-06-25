/*
* The Percolation works fine but it suffers form Backwash
*/
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final boolean[][] grid;
    private final int size;
    private final int wquLength;
    private int openSites;
    private final WeightedQuickUnionUF wqu;
    private final int virtualTop;
    private final int virtualBottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("grid dimensions should be more than 1");

        grid = new boolean[n][n];
        size = n;
        openSites = 0;
        wquLength = size * size + 2;
        wqu = new WeightedQuickUnionUF(wquLength);

        // initialize top and bottom virtual sites
        virtualTop = 0;
        virtualBottom = wquLength - 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (validate(row, col))
            throw new IllegalArgumentException("provided dimensions are out of bounds (between 1 and " + size + ")");

        row = row - 1;
        col = col - 1;
        if (!grid[row][col]) {
            grid[row][col] = true;
            openSites++;
            connect(row, col);

            int index = row * size + col;
            if (row == 0)
                wqu.union(virtualTop, index);
            if (row == size - 1)
                wqu.union(virtualBottom, index);
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (validate(row, col))
            throw new IllegalArgumentException("Out of bounds site");
        row = row - 1;
        col = col - 1;
        return grid[row][col];
    }

    /** is the site (row, col) full? */
    public boolean isFull(int row, int col) {
        if (validate(row, col))
            throw new IllegalArgumentException("Out of bounds site");
        row = row - 1;
        col = col - 1;
        int index = row * size + col;

        return grid[row][col] && wqu.find(virtualTop) == wqu.find(index);
    }

    /** returns the number of open sites */
    public int numberOfOpenSites() {
        return openSites;
    }

    /** does the system percolate? */
    public boolean percolates() {
        return wqu.find(virtualTop) == wqu.find(virtualBottom);
    }

    /** an enum that specifies the adjacent sites */
    private enum Adjacent {
        UP(-1, 0),
        DOWN(1, 0),
        LEFT(0, -1),
        RIGHT(0, 1);

        final int row, col;
        Adjacent(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    /** check the adjacent neighbours and connect to them if they are open */
    private void connect(int row, int col) {

        final Adjacent[] DIRECTIONS = {Adjacent.UP, Adjacent.DOWN, Adjacent.LEFT, Adjacent.RIGHT};

        for (Adjacent adj: DIRECTIONS) {
            int newRow = row + adj.row;
            int newCol = col + adj.col;

            if (newRow < 0 || newRow >= size || newCol < 0 || newCol >= size) continue;

            if (grid[newRow][newCol]) {
                int index = row * size + col;
                int newIndex = newRow * size + newCol;
                wqu.union(index, newIndex);
            }

        }
    }

    private boolean validate(int row, int col) {
        return row < 1 || row > size || col < 1 || col > size;
    }

}
