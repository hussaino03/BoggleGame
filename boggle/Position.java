package boggle;

/**
 * Class to hold Position information associated with a BoggleGrid
 */
public class Position {
    /**
     * row
     */    
    private int row;
    /**
     * column
     */    
    private int col;

    /**
     * Position Constructor
     * Sets row and column to 0, by default
     */
    public Position() {
        this.row = 0;
        this.col = 0;
    }

    /**
     * Setter for this.row
     * @param row the row this position should be set to
     */
    public void setRow(int row) { this.row = row; }

    /**
     * Setter for this.col
     * @param col the column this position should be set to
     */
    public void setCol(int col) { this.col = col; }

    /**
     * Getter for this.row
     * @return the row this position is at
     */
    public int getRow() { return this.row; }

    /**
     * Getter for this.col
     * @return the column this position is at
     */
    public int getCol() { return this.col; }
}
