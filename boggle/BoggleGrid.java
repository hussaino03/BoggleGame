package boggle;

/**
 * The BoggleGrid represents the grid on which we play Boggle 
 */
public class BoggleGrid {

    /**
     * size of grid
     */  
    private final int size;
    /**
     * characters assigned to grid
     */      
    private final char[][] board;

    /** BoggleGrid constructor
     * @param size  The size of the Boggle grid to initialize
     */
    public BoggleGrid(int size) {
        this.size = size;
        board = new char[this.size][this.size];
    }

    /**
     * Assigns a letter in the string of letters to each grid position
     * Letters are assigned left to right, top to bottom
     * @param letters a string of letters, one for each grid position.
     */
    public void initializeBoard(String letters) {
        for (int row = 0; row < this.board.length; row++){
            for (int col = 0; col < this.board[row].length; col++) {
                    this.board[row][col] = letters.charAt(row*this.size+col);
            }
        }
    }

    /**
     * Provides a nice-looking string representation of the grid,
     * so that the user can easily scan it for words.
     * @return String to print
     */
    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        for(int row = 0; row < this.size; row++){
            for(int col = 0; col < this.size; col++){
                boardString.append(this.board[row][col]).append(" ");
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }

    /** 
     * Getter for this.size
     * @return int the number of rows/cols on the board (assumes square grid)
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Getter for this.board[row][col]
     * @param row the row the desired character is in
     * @param col the column the desired character is in
     * @return char the character at a given grid position
     */
    public char getCharAt(int row, int col) {
        return this.board[row][col];
    }

    /**
     * Getter for this.board
     * @return the Boggle board
     */
    public char[][] getBoard() {return this.board;}

}
