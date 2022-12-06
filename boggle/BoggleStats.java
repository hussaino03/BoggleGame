package boggle;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * The BoggleStats class for the first Assignment in CSC207, Fall 2022
 * The BoggleStats will contain statsitics related to game play Boggle 
 */

public class BoggleStats implements Serializable {
    private static BoggleStats instance = null;
    /**
     * set of words the player finds in a given round 
     */  
    public Set<String> playerWords = new HashSet<String>();
    /**
     * set of words the computer finds in a given round 
     */  
    public Set<String> computerWords = new HashSet<String>();
    /**
     * the player's score for the current round
     */  
    public int pScore;
    /**
     * the computer's score for the current round
     */  
    public int cScore;
    /**
     * the player's total score across every round
     */  
    public int pScoreTotal;
    /**
     * the computer's total score across every round
     */  
    public int cScoreTotal;
    /**
     * the average number of words, per round, found by the player
     */  
    private double pAverageWords; 
    /**
     * the average number of words, per round, found by the computer
     */  
    private double cAverageWords; 
    /**
     * the current round being played
     */  
    public int round;
    /**
     * the player's total score across all games
     */
    public double pScoreAllTime;
    /**
     * the computer's total score across all games
     */

    public double pAverageWordsAllTime;
    /**
     * the average number of words, per round, found by the computer across all games
     */
    public double totalRounds;

    private double cAverageWordsAllTime;
    /**
     * the number of rounds across all games
     */
    /**
     * enumarable types of players (human or computer)
     */  
    public enum Player {
        Human("Human"),
        Computer("Computer");
        private final String player;
        Player(final String player) {
            this.player = player;
        }
    }

    /* BoggleStats constructor
     * ----------------------
     * Sets round, totals and averages to 0.
     * Initializes word lists (which are sets) for computer and human players.
     */
    private BoggleStats() {
        round = 0;
        pScoreTotal = 0;
        pScoreAllTime = 0;
        cScoreTotal = 0;
        pAverageWords = 0;
        pAverageWordsAllTime = 0;
        cAverageWords = 0;
        playerWords = new HashSet<String>();
        computerWords = new HashSet<String>();

        try {
            FileInputStream file = new FileInputStream("boggle/SavedStats.ser");
            ObjectInputStream in = new ObjectInputStream(file);
            BoggleStats savedStats = (BoggleStats) in.readObject();
            pScoreAllTime = savedStats.pScoreAllTime;
            pAverageWordsAllTime = savedStats.pAverageWordsAllTime;
            cAverageWordsAllTime = savedStats.cAverageWordsAllTime;
            totalRounds = savedStats.totalRounds;
        }
        catch (IOException e) {
            pScoreAllTime = 0;
            pAverageWordsAllTime = 0;
            cAverageWordsAllTime = 0;
            totalRounds = 0;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            round = 0;
            pScoreTotal = 0;
            cScoreTotal = 0;
            pAverageWords = 0;
            cAverageWords = 0;
            playerWords = new HashSet<String>();
            computerWords = new HashSet<String>();
        }
        }
    /*
    * SingleTon Design Implementation:
    * A public method to return the instance of BoggleStats
    * @return BoggleStats instance
     */
    public static synchronized  BoggleStats getInstance(){
        if (instance == null){
            instance = new BoggleStats();
        }
        return instance;
    }

    /* 
     * Add a word to a given player's word list for the current round.
     * You will also want to increment the player's score, as words are added.
     *
     * @param word     The word to be added to the list
     * @param player  The player to whom the word was awarded
     */
    public void addWord(String word, Player player) {
        word = word.toLowerCase();
        if (player == Player.Human){
            playerWords.add(word);
            if (word.length() >= 4){
                System.out.println("Working");
                String spliced = word.substring(4);
                pScore += spliced.length() + 1;
                System.out.println(pScore);
            }
        } else if (player == Player.Computer){
            computerWords.add(word.toLowerCase());
            if (word.length() >= 4){
                String spliced = word.substring(4);
                cScore += spliced.length() + 1;
            }
        }
    }

    /* 
     * End a given round.
     * This will clear out the human and computer word lists, so we can begin again.
     * The function will also update each player's total scores, average scores, and
     * reset the current scores for each player to zero.
     * Finally, increment the current round number by 1.
     */
    public void endRound() {

        pScoreTotal += pScore;
        cScoreTotal += cScore;

        // All Time Stats Updated
        pScoreAllTime += pScore;

        totalRounds += 1;
        System.out.println("Total Rounds:" + totalRounds);

//        pScore = 0;
//        cScore = 0;
        round += 1;


        pAverageWords = (pAverageWords * (round - 1)) / round + playerWords.size() / (double) round;
        pAverageWordsAllTime =
                (pAverageWordsAllTime * (totalRounds - 1)) / totalRounds + playerWords.size() / (double) totalRounds;

        cAverageWords = (cAverageWords * (round - 1)) / round + computerWords.size() / (double) round;
       
        cAverageWordsAllTime =
                (cAverageWordsAllTime * (totalRounds - 1)) / totalRounds + computerWords.size() / (double) totalRounds;


    }

    /* 
     * Summarize one round of boggle.  Print out:
     * The words each player found this round.
     * Each number of words each player found this round.
     * Each player's score this round.
     */
    public void summarizeRound() {
        System.out.println("Human Words: "+playerWords);
        System.out.println("Computer Words: "+computerWords);

        System.out.println("Number of Human Average Words: "+playerWords.size());
        System.out.println("Number of Computer Average Words: "+computerWords.size());

        System.out.println("Human Score For The Round: "+pScore);
        System.out.println("Computer Score For The Round: "+cScore);
    }

    /*
     * Summarize one round of boggle.  Print out:
     * The words each player found this round.
     * Each number of words each player found this round.
     * Each player's score this round.
     */
    public HashMap<String, Object> getStatsMap() {
        HashMap<String, Object> scoreMap = new HashMap<>();
        scoreMap.put("Player Words", playerWords);
        scoreMap.put("Computer Words", computerWords);
        scoreMap.put("Player Score", pScore);
        scoreMap.put("Computer Score", cScore);
        scoreMap.put("Player Average Words", pAverageWords);
        scoreMap.put("Computer Average Words", cAverageWords);
        scoreMap.put("Player Score Total", pScoreTotal);
        scoreMap.put("Computer Score Total", cScoreTotal);
        scoreMap.put("Round", round);
        return scoreMap;
    }

    /**
     * Getter for playerWords
     * @return playerWords
     */
    public String playerwords(){
        return "Human Words: "+playerWords;
    }
    /**
     * Getter for computerWords
     * @return computerWords
     */
    public String computerwords(){
        return "Computer Words: "+computerWords;
    }
    /**
     * Getter for playerWords size
     * @return playerWords size
     */
    public String playerwordsSize(){
        return "Number of Human Average Words: " + playerWords.size();
    }
    /**
     * Getter for computerWords size
     * @return computerWords size
     */
    public String computerwordsSize(){
        return "Number of Computer Average Words: "+computerWords.size();
    }
    /**
     * Getter for pScore
     * @return pScore
     */
    public String PScore(){
        return "Human Score For The Round: "+pScore;
    }
    /**
     * Getter for cScore
     * @return cScore
     */
    public String CScore(){
        return "Computer Score For The Round: "+cScore;
    }

    /* 
     * Summarize the entire boggle game.  Print out:
     * The total number of rounds played.
     * The total score for either player.
     * The average number of words found by each player per round.
     */
    public void summarizeGame() {
        // Save the stats of this game
        try {
            FileOutputStream file = new FileOutputStream("boggle/SavedStats.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(this);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        System.out.println("The Total Number of Rounds Played is: "+ round);
        System.out.println("The Total Score for Human is: "+pScoreTotal);
        System.out.println("The Total Score for Computer is: "+cScoreTotal);

        System.out.println("The Average Number of Words Found by Human: "+pAverageWords);
        System.out.println("The Average Number of Words Found by Computer: "+cAverageWords);
    }

    /**
     * Getter for total rounds
     * @return round
     */
    public String Totalround(){
        return "The Total Number of Rounds Played is: "+ round;
    }
    /**
     * Getter for human total score
     * @return pscoreTotal
     */
    public String pScoreTotal(){
        return "The Total Score for Human is: "+pScoreTotal;
    }
    /**
     * Getter for computer total score
     * @return cScoreTotal
     */
    public String cScoreTotal(){
        return "The Total Score for Computer is: "+cScoreTotal;
    }
    /**
     * Getter for player average words
     * @return pAverageWords
     */
    public String pAverageWords(){
        return "The Average Number of Words Found by Human: "+pAverageWords;
    }
    /**
     * Getter for computer average words
     * @return cAverageWords
     */
    public String cAverageWords(){
        return "The Average Number of Words Found by Computer: "+cAverageWords;
    }

    /*
     * @return Set<String> The player's word list
     */
    public Set<String> getPlayerWords() {
        return this.playerWords;
    }
    /*
     * @return Set<String> The player's word list
     */
    public Set<String> getComputerWords() {
        return this.computerWords;
    }

    /**
     * @return int The number of rounds played this game
     */
    public int getRound() { return this.round; }

    /**
     * @return int The number of rounds played across all games
     */
    public int getTotalRounds() { return (int) this.totalRounds; }

    /**
    * @return int The current player score
    */
    public int getPScore() {
        return this.pScore;
    }
    /**
     * @return int The current computer score
     */
    public int getCScore() {
        return this.cScore;
    }
    /**
     * Set the totalRounds attribute (for testing purposes)
     */
    public void setTotalRounds(int totalRounds) {
        this.totalRounds = totalRounds;
    }


    public double getTotalRound(){return this.totalRounds;}

    public double getAvgWordsAllTime(){return this.pAverageWordsAllTime;}

    public double getScoreAllTime(){return this.pScoreAllTime;}



}

