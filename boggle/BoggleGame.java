package boggle;

import command.*;
import src.gameWindow;

import java.util.*;

/**
 * The BoggleGame class serves as the Model for our game in the Model-View-Controller Software
 * Architecture. The logic of the game is handled here.
 */
public class BoggleGame {

    /**
     * Choice Processor used to interact with the user by storing their decisions
     */ 
    public HashMap<String, String> choiceProcessor;
    /**
     * window through which this game will be played
     */
    public gameWindow window;
    /**
     * CommandCenter to which commands will be sent
     */
    public CommandCenter commandCenter;
    /**
     * stores game statistics
     */
    public BoggleStats gameStats;

    /**
     * dice used to randomize letter assignments for a 4x4 grid
     */ 
    private final String[] four_by_four_grid= //dice specifications, for small and large grids
            {"AAEEGN", "ABBJOO", "ACHOPS", "AFFKPS", "AOOTTW", "CIMOTU", "DEILRX", "DELRVY",
                    "DISTTY", "EEGHNW", "EEINSU", "EHRTVW", "EIOSST", "ELRTTY", "HIMNQU", "HLNNRZ"};
    /**
     * dice used to randomize letter assignments for a 5x5 grid
     */ 
    private final String[] five_by_five_grid =
            {"AAAFRS", "AAEEEE", "AAFIRS", "ADENNN", "AEEEEM", "AEEGMU", "AEGMNN", "AFIRSY",
                    "BJKQXZ", "CCNSTW", "CEIILT", "CEILPT", "CEIPST", "DDLNOR", "DDHNOT", "DHHLOR",
                    "DHLNOR", "EIIITT", "EMOTTT", "ENSSSU", "FIPRSY", "GORRVW", "HIPRRY", "NOOTUW", "OOOTTU"};

    /** dice used to randomize letter assignments for a 6x6 grid
     */ 
    private final String[] six_by_six_grid =
            {"MNPQWR", "ABBJOO", "ACHOPS", "AFFKPS", "AOOTTW", "CIMOTU", "DELRVY",
                    "DISTTY", "EEGHNW", "EEINSU", "EHRTVW", "EIOSST", "ELRTTY", "HIMNQU", "AAAFRS", "HJKGBN", "ADENNN", "KLFGVC", "AEEGMU", "AEGMNN", "AFIRSY",
                    "BJKQXZ", "CCNSTW", "CEIILT", "CEILPT", "CEIPST", "DDHNOT", "DHHLOR", "DHLNOR", "EIIITT", "EMOTTT", "ENSSSU", "FIPRSY", "GORRVW", "HIPRRY", "NOOTUW", "OOOTTU"};

    /**
     * BoggleGame constructor
     * @param w the window on which this instance of BoggleGame is being played
     */
    public BoggleGame(gameWindow w) {
        this.choiceProcessor = new HashMap<String, String>();
        this.choiceProcessor.put("Grid Size", "");
        this.choiceProcessor.put("Word", "_");
        this.choiceProcessor.put("choice", "_");
        this.choiceProcessor.put("Round Ended", "false");
        this.gameStats = BoggleStats.getInstance();
        this.window = w;
        this.commandCenter = CommandCenter.getInstance(window);
    }

    /**
     * Gets information from the user to initialize a new Boggle game.
     * It will loop until the user indicates they are done playing.
     */
    public void playGame() {
        int boardSize = 0;
        while(true){
            String gridSize = choiceProcessor.get("Grid Size");

            //get grid size preference

            switch (gridSize) {
                case "four" -> boardSize = 4;
                case "five" -> boardSize = 5;
                case "six" -> boardSize = 6;
            }

            if (!(gridSize.equals(""))) {
                playRound(boardSize, randomizeLetters(boardSize));
                this.gameStats.summarizeRound();
                this.gameStats.endRound();
                this.choiceProcessor.put("Grid Size", "");
            }
            while (this.choiceProcessor.get("choice").equals("_")) {
                if (!this.choiceProcessor.get("choice").equals("_")) {
                    break;
                }
            }
            if (this.choiceProcessor.get("choice").equals("Y")) {
                BoggleStats.getInstance().playerWords.clear();
                BoggleStats.getInstance().computerWords.clear();
                BoggleStats.getInstance().pScore = 0;
                BoggleStats.getInstance().cScore = 0;
                this.choiceProcessor.put("choice", "_");
            }
            //round is over! So, store the statistics, and end the round.

            //Shall we repeat?
            if (this.choiceProcessor.get("choice").equals("N")) {
                this.choiceProcessor.put("choice", "_");
                break;
            }
        }

        //we are done with the game! So, summarize all the play that has transpired and exit.
        this.gameStats.summarizeGame();
    }

    /**
     * Play a round of Boggle.
     * This initializes the main objects: the board, the dictionary, the map of all
     * words on the board, and the set of words found by the user. These objects are
     * passed by reference from here to many other functions.
     * @param size the width (and also height, since the board is square) of the boggle grid
     * to be initialized
     * @param letters the letters which should be assigned to each position of the boggle grid
     */
    public void playRound(int size, String letters) {
        //step 1. initialize the grid
        BoggleGrid grid = new BoggleGrid(size);
        grid.initalizeBoard(letters);
        //step 2. initialize the dictionary of legal words
        Dictionary boggleDict = new Dictionary("wordlist.txt"); //you may have to change the path to the wordlist, depending on where you place it.
        //step 3. find all legal words on the board, given the dictionary and grid arrangement.
        Map<String, ArrayList<Position>> allWords = new HashMap<String, ArrayList<Position>>();
        findAllWords(allWords, boggleDict, grid);

        //step 3.5. display the boggle board for the user to have words to find
        String Id = "DisplayGridElements; " + letters;
        commandCenter.handle(Id);

        //step 4. allow the user to try to find some words on the grid
        humanMove(allWords);
//        //step 5. allow the computer to identify remaining words
        computerMove(allWords);
    }

    /**
     * This method returns a String of letters. There will be one letter per grid position,
     * and they will be organized left to right, top to bottom.
     * @return String a String of random letters (length 16, 25 or 36)
     * depending on the size of the grid)
     */
    private String randomizeLetters(int size){
        List<String> four = Arrays.asList(four_by_four_grid);
        List<String> five = Arrays.asList(five_by_five_grid);
        List<String> six = Arrays.asList(six_by_six_grid);

        Collections.shuffle(four);
        Collections.shuffle(five);
        Collections.shuffle(six);

        StringBuilder fourRes = new StringBuilder(size*size);
        StringBuilder fiveRes = new StringBuilder(size*size);
        StringBuilder sixRes = new StringBuilder(size*size);

        if (size == 4){
            for (String str: four){
                int i = (int)(str.length() * Math.random());
                fourRes.append(str.charAt(i));
            }
            return fourRes.toString();
        }
        else if (size == 5){
            for (String str: five){
                int i = (int)(str.length() * Math.random());
                fiveRes.append(str.charAt(i));
            }
            return fiveRes.toString();
        }
        else if (size == 6){
            for (String str: six){
                int i = (int)(str.length() * Math.random());
                sixRes.append(str.charAt(i));
            }
            return sixRes.toString();
        }
        return "";
    }

    /**
     * This is a recursive function that finds all valid words on the boggle board.
     * Every word should be valid (i.e. in the boggleDict) and of length 4 or more.
     * Words that are found are entered into the allWords HashMap. This HashMap
     * will be consulted as we play the game.
     * @param allWords A mutable list of all legal words that can be found, given the boggleGrid grid letters
     * @param boggleDict A dictionary of legal words
     * @param boggleGrid A boggle grid, with a letter at each position on the grid
     */
    private void findAllWords(Map<String,ArrayList<Position>> allWords, Dictionary boggleDict, BoggleGrid boggleGrid) {
        String str = "";
        boolean[][] tracker = new boolean[boggleGrid.getSize()][boggleGrid.getSize()];

        for (int i = 0; i < boggleGrid.getSize(); i++)
            for (int j = 0; j < boggleGrid.getSize(); j++)
                traverse(allWords, boggleGrid, tracker, i, j, str, boggleDict);
    }
    /**
     * A helper function for findAllWords that traverses the Boggle board and recursively builds up the valid words
     * It takes the current position and searches 8 adjacent cells around it to build up valid words
     * @param allWords A mutable list of all legal words that can be found, given the boggleGrid grid letters
     * @param boggle A boggle grid, with a letter at each position on the grid
     * @param tracker A boolean variable that keeps track of the current position in the grid already visited
     * @param row The current position in the grid horizontally (row)
     * @param col The current position in the grid vertically (row)
     * @param str The string used to build up the found valid words recursively
     * @param boggleDict A dictionary of legal words
     */
    private void traverse(Map<String,ArrayList<Position>> allWords, BoggleGrid boggle,
                           boolean[][] tracker, int row, int col,
                        String str, Dictionary boggleDict){

        // create an arraylist to add the positions inside it and pass it into the allWords dict
        ArrayList<Position> list = new ArrayList<>();

        // create attributes to loop through the total rows and cols and search the whole board for the word
        int totalRows, totalCols;
        totalRows = totalCols = boggle.getSize();

        // when the function gets called, we know we have already seen the particular position of the str
        tracker[row][col] = true;

        // setup another base case that keeps building up the str using letters at that position
        if (boggleDict.isPrefix(str)){
            str += boggle.getCharAt(row, col);
        }

        // create position object and set the positions
        Position pos = new Position();
        pos.setRow(row);
        pos.setCol(col);

        // add the positions to a list that stores all the positions of the certain word
        list.add(pos);

        // check if the str is in dict and if its valid
        if (boggleDict.containsWord(str.toLowerCase()) && str.length() >= 4){
            allWords.put(str, list);
        }

        // Traverse alongside the position to all 8 adjacent grid positions around it
        for (int i = row - 1; i <= row + 1 && i < totalRows; i++) {
            for (int j = col - 1; j <= col + 1 && j < totalCols; j++) {
                if (i >= 0 && j >= 0) {
                    if (!tracker[i][j] && boggleDict.isPrefix(str)) {
                        traverse(allWords, boggle, tracker, i, j, str, boggleDict);
                    }
                }
            }
        }
        // Set the tracker to be false since we create new positions for the str when we traverse
        tracker[row][col] = false;
    }

    /**
     * Gets words from the user.  As words are input, check to see that they are valid.
     * If yes, add the word to the player's word list (in boggleStats) and increment
     * the player's score (in boggleStats).
     * End the turn once the user hits return (with no word).
     * @param allWords A mutable list of all legal words that can be found,
     * given the boggleGrid grid letters
     */
    private void humanMove(Map<String,ArrayList<Position>> allWords){
        Dictionary dict = new Dictionary("wordlist.txt");
        while(true) {

            String word = this.choiceProcessor.get("Word");

            if (word.length() >= 4 && dict.containsWord(word) &&
                    !gameStats.getPlayerWords().contains(word) &&
                    allWords.containsKey(word.toUpperCase())){
                gameStats.addWord(word.toLowerCase(), BoggleStats.Player.Human);
                commandCenter.handle("DisplayInGameStats; ");

            }

            if (choiceProcessor.get("Round Ended").equals("true")){
                commandCenter.handle("DisplayRoundStats; ");
                this.choiceProcessor.put("Round Ended", "false");
                break;
            }
        }
    }


    /**
     * Gets words from the computer. The computer should find words that are both valid
     * and not in the player's word list.  For each word that the computer finds, update
     * the computer's word list and increment the computer's score (stored in boggleStats).
     * @param all_words A mutable list of all legal words that can be found, given the boggleGrid grid letters
     */
    private void computerMove(Map<String,ArrayList<Position>> all_words){
        for (String i: all_words.keySet()){
            if (!gameStats.getPlayerWords().contains(i.toLowerCase())){
                gameStats.addWord(i, BoggleStats.Player.Computer);
            }
        }
    }

    /**
     * This method checks whether the player has selected a grid size
     * @return returns true if the player has selected a grid size and false otherwise
     */
    public boolean gridSizeSelected() {
        return !(choiceProcessor.get("Grid Size").equals(""));
    }
}
