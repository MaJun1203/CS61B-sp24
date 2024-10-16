import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // TODO: Add any necessary instance variables.
    public WeightedQuickUnionUF weightUnion;
    public int[][] grid;
    public int openNumber;
    public Percolation(int N) {
        weightUnion = new WeightedQuickUnionUF(N*N);
        openNumber = 0;
        grid = new int[N][N];
        for(int i =0; i < N; i++){
            for(int j = 0; j < N; j++){
                grid[i][j] = 0;
            }
        }
    }

    public void open(int row, int col) {
        // TODO: Fill in this method.
        openNumber++;
        int number = row * grid.length + col;
        grid[row][col] = 1;
        if(row > 0 && grid[row-1][col] == 1){
            weightUnion.union(number,number - grid.length);
        }if(col > 0 && grid[row][col-1] == 1){
            weightUnion.union(number,number-1);
        }if (row < grid.length-1 && grid[row + 1][col] == 1){
            weightUnion.union(number,number + grid.length);
        }if(col < grid[0].length-1 && grid[row][col + 1] == 1){
            weightUnion.union(number,number + 1);
        }
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        return grid[row][col] == 1;
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        int number = row * grid.length + col;
        for(int i = 0; i < grid.length; i++){
            if(weightUnion.connected(i, number)){
                return true;
            }
        }
        return false;
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        return openNumber;
    }

    public boolean percolates() {
        // TODO: Fill in this method.
        for(int i = 0; i < grid.length; i++){
            if( isFull(grid.length - 1, i)){
                return true;
            }
        }
        return false;
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.

}
