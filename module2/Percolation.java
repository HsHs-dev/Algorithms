import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final boolean[][] grid;
    private final int size;
    private final int wquLength;
    private int openSites;
    private final WeightedQuickUnionUF wqu;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) throws IllegalAccessException {
        if (n <= 0) throw new IllegalAccessException("grid dimensions should be more than 0");

        grid = new boolean[n][n];
        size = n;
        openSites = 0;
        wquLength = size * size + 2;
        wqu = new WeightedQuickUnionUF(wquLength);
        // initialize top and bottom virtual sites
        for (int i = 1; i <= n; i++) {
            wqu.union(0, i);
        }
        for (int i = wquLength - 2; i >= (wquLength - (n + 1)); i--) {
            wqu.union(wquLength - 1, i);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) throws IllegalAccessException {
        if (row < 1 || row > size || col < 1 || col > size)
            throw new IllegalAccessException("provided dimensions are out of bounds (between 1 and " + size + ")");
        row = row - 1;
        col = col - 1;
        if (!grid[row][col]) {
            grid[row][col] = true;
            openSites++;
            connect(row, col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return wqu.connected(0, wquLength - 1);
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
                wqu.union(row * size + col, newRow * size + newCol);
            }
        }
    }

    // test client (optional)
    public static void main(String[] args) throws IllegalAccessException {
        Percolation per = new Percolation(3);
        per.open(1, 2);
        System.out.println(per.percolates());
        per.open(2, 2);
        System.out.println(per.percolates());
        per.open(3, 2);
        System.out.println(per.percolates());


    }

}
