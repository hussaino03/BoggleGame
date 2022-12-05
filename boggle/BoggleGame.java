package boggle;

import command.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import src.gameWindow;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.*;

/**
 * The BoggleGame class for the first Assignment in CSC207, Fall 2022
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

    /* 
     * BoggleGame constructor
     */
    public BoggleGame(gameWindow w) {
        this.choiceProcessor = new HashMap<String, String>();
        this.choiceProcessor.put("Game Mode", "");
        this.choiceProcessor.put("Grid Size", "");
        this.choiceProcessor.put("Word", "_");
        this.choiceProcessor.put("Round Ended", "false");
        this.gameStats = BoggleStats.getInstance();
        this.window = w;
        this.commandCenter = CommandCenter.getInstance(window);
    }

    /*
     * Provide instructions to the user, so they know how to play the game.
     */

    // This is obsolete because of the How To Play screen
    public void giveInstructions()
    {
        System.out.println("The Boggle board contains a grid of letters that are randomly placed.");
        System.out.println("We're both going to try to find words in this grid by joining the letters.");
        System.out.println("You can form a word by connecting adjoining letters on the grid.");
        System.out.println("Two letters adjoin if they are next to each other horizontally, ");
        System.out.println("vertically, or diagonally. The words you find must be at least 4 letters long, ");
        System.out.println("and you can't use a letter twice in any single word. Your points ");
        System.out.println("will be based on word length: a 4-letter word is worth 1 point, 5-letter");
        System.out.println("words earn 2 points, and so on. After you find as many words as you can,");
        System.out.println("I will find all the remaining words.");
        System.out.println("\nHit return when you're ready...");
    }


    /**
     * Gets information from the user to initialize a new Boggle game.
     * It will loop until the user indicates they are done playing.
     */
    public void playGame() {
        int boardSize = 0;
        while(true){
            String gameMode = choiceProcessor.get("Game Mode");
            String gridSize = choiceProcessor.get("Grid Size");

            //get grid size preference

            if(gridSize.equals("four")) {
                boardSize = 4;
            } else if (gridSize.equals("five")) {
                boardSize = 5;
            } else if (gridSize.equals("six")){
                boardSize = 6;
            }

            if (!(gridSize.equals(""))) {
                playRound(boardSize, randomizeLetters(boardSize));
                this.gameStats.summarizeRound();
                this.gameStats.endRound();
            }

//            //get letter choice preference
//            System.out.println("Enter 1 to randomly assign letters to the grid; 2 to provide your own.");
//            String choiceLetters = scanner.nextLine();
//
//            if(choiceLetters == "") break; //end game if user inputs nothing
//            while(!choiceLetters.equals("1") && !choiceLetters.equals("2") && !choiceLetters.equals("3")){
//                System.out.println("Please try again.");
//                System.out.println("Enter 1 to randomly assign letters to the grid; 2 to provide your own.");
//                choiceLetters = scanner.nextLine();
//            }
//
//            if(choiceLetters.equals("1")){
//                playRound(boardSize,randomizeLetters(boardSize));
//            } else {
//                System.out.println("Input a list of " + boardSize*boardSize + " letters:");
//                choiceLetters = scanner.nextLine();
//                while(!(choiceLetters.length() == boardSize*boardSize)){
//                    System.out.println("Sorry, bad input. Please try again.");
//                    System.out.println("Input a list of " + boardSize*boardSize + " letters:");
//                    choiceLetters = scanner.nextLine();
//                }
//                playRound(boardSize,choiceLetters.toUpperCase());
//            }

            //round is over! So, store the statistics, and end the round.


            // Display round stats
            commandCenter.handle("DisplayRoundStats; ");
            //Shall we repeat?
            System.out.println("Play again? Type 'Y' or 'N'");
            Scanner sc = new Scanner(System.in);
            String choiceRepeat = sc.nextLine().toUpperCase();

            if(choiceRepeat == "") break; //end game if user inputs nothing
            while(!choiceRepeat.equals("Y") && !choiceRepeat.equals("N")){
                System.out.println("Please try again.");
                System.out.println("Play again? Type 'Y' or 'N'");
                choiceRepeat = sc.nextLine().toUpperCase();
            }

            if(choiceRepeat == "" || choiceRepeat.equals("N")) break; //end game if user inputs nothing

        }

        //we are done with the game! So, summarize all the play that has transpired and exit.
        this.gameStats.summarizeGame();
        System.out.println("Thanks for playing!");
    }

    /**
     * Play a round of Boggle.
     * This initializes the main objects: the board, the dictionary, the map of all
     * words on the board, and the set of words found by the user. These objects are
     * passed by reference from here to many other functions.
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
        humanMove(grid, allWords);
//        //step 5. allow the computer to identify remaining words
        computerMove(allWords);
    }

    /**
     * This method should return a String of letters (length 16 or 25 depending on the size of the grid).
     * There will be one letter per grid position, and they will be organized left to right,
     * top to bottom. A strategy to make this string of letters is as follows:
     * -- Assign a one of the dice to each grid position (i.e. dice_big_grid or dice_small_grid)
     * -- "Shuffle" the positions of the dice to randomize the grid positions they are assigned to
     * -- Randomly select one of the letters on the given die at each grid position to determine
     *    the letter at the given position
     *
     * @return String a String of random letters (length 16 or 25 depending on the size of the grid)
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
     * This should be a recursive function that finds all valid words on the boggle board.
     * Every word should be valid (i.e. in the boggleDict) and of length 4 or more.
     * Words that are found should be entered into the allWords HashMap.  This HashMap
     * will be consulted as we play the game.
     *
     * Note that this function will be a recursive function.  You may want to write
     * a wrapper for your recursion. Note that every legal word on the Boggle grid will correspond to
     * a list of grid positions on the board, and that the Position class can be used to represent these
     * positions. The strategy you will likely want to use when you write your recursion is as follows:
     * -- At every Position on the grid:
     * ---- add the Position of that point to a list of stored positions
     * ---- if your list of stored positions is >= 4, add the corresponding word to the allWords Map
     * ---- recursively search for valid, adjacent grid Positions to add to your list of stored positions.
     * ---- Note that a valid Position to add to your list will be one that is either horizontal, diagonal, or
     *      vertically touching the current Position
     * ---- Note also that a valid Position to add to your list will be one that, in conjunction with those
     *      Positions that precede it, form a legal PREFIX to a word in the Dictionary (this is important!)
     * ---- Use the "isPrefix" method in the Dictionary class to help you out here!!
     * ---- Positions that already exist in your list of stored positions will also be invalid.
     * ---- You'll be finished when you have checked EVERY possible list of Positions on the board, to see
     *      if they can be used to form a valid word in the dictionary.
     * ---- Food for thought: If there are N Positions on the grid, how many possible lists of positions
     *      might we need to evaluate?
     *
     * @param allWords A mutable list of all legal words that can be found, given the boggleGrid grid letters
     * @param boggleDict A dictionary of legal words
     * @param boggleGrid A boggle grid, with a letter at each position on the grid
     */
    private void findAllWords(Map<String,ArrayList<Position>> allWords, Dictionary boggleDict, BoggleGrid boggleGrid) {
        String str = "";
        boolean[][] tracker = new boolean[boggleGrid.numRows()][boggleGrid.numCols()];

        for (int i = 0; i < boggleGrid.numRows(); i++)
            for (int j = 0; j < boggleGrid.numCols(); j++)
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

        // create a arraylist to add the positions inside it and pass it into the allWords dict
        ArrayList<Position> list = new ArrayList<>();

        // create attributes to loop through the total rows and cols and search the whole board for the word
        int totalRows = boggle.numRows();
        int totalCols = boggle.numCols();

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
     *
     * @param board The boggle board
     * @param allWords A mutable list of all legal words that can be found, given the boggleGrid grid letters
     */
    private void humanMove(BoggleGrid board, Map<String,ArrayList<Position>> allWords){
        System.out.println("It's your turn to find some words!");
//        Scanner sc = new Scanner(System.in);
        Dictionary dict = new Dictionary("wordlist.txt");
        while(true) {

//            System.out.println(board); Instead of printing this to the console, should be displayed
//            System.out.println("Enter your word!");
            String word = this.choiceProcessor.get("Word");

            if (word.length() >= 4 && dict.containsWord(word) &&
                    !gameStats.getPlayerWords().contains(word) &&
                    allWords.containsKey(word.toUpperCase())){
                System.out.println(word);
                gameStats.addWord(word.toLowerCase(), BoggleStats.Player.Human);
                commandCenter.handle("DisplayGameStats; ");

            }else {
                if (!word.equals("")) {
//                    System.out.println("Invalid Word! Sorry!");
                }
            }
            if (choiceProcessor.get("Round Ended").equals("true")){
                break;
            }
        }
    }


    /**
     * Gets words from the computer.  The computer should find words that are
     * both valid and not in the player's word list.  For each word that the computer
     * finds, update the computer's word list and increment the
     * computer's score (stored in boggleStats).
     *
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
     * This method checks if the game has received all the information necessary to begin.
     * @return Returns true if the game is ready to begin and false otherwise
     */
    public boolean ready() { // the game is ready to start iff the choiceProcessor is fully populated
        for (String info : this.choiceProcessor.values()) { // go through each choice in choiceProcessor
            if (info.equals("")) { // if a choice has not been made, the game is not ready to begin
                return false;
            }
        }
        return true; // if all choices have been made, the game is ready to start
    }
}
