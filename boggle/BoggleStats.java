package boggle;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * The BoggleStats class contains statistics related to game play Boggle
 */

public class BoggleStats implements Serializable {
    /**
     * instance attribute of BoggleStats as per the Singleton Design Pattern
     */
    private static BoggleStats instance = null;
    /**
     * set of words the player finds in a given round 
     */  
    private Set<String> playerWords = new HashSet<String>();
    /**
     * set of words the computer finds in a given round 
     */  
    private Set<String> computerWords = new HashSet<String>();
    /**
     * the player's score for the current round
     */  
    private int pScore;
    /**
     * the computer's score for the current round
     */  
    private int cScore;
    /**
     * the player's total score across every round
     */  
    private int pScoreTotal;
    /**
     * the computer's total score across every round
     */  
    private int cScoreTotal;
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
    private int round;
    /**
     * the player's total score across all games
     */
    private double pScoreAllTime;
    /**
     * the average number of words, per round, found by the computer across all games
     */
    private double pAverageWordsAllTime;
    /**
     * the number of rounds across all games
     */
    private double totalRounds;
    /**
     * enumerable types of players (human or computer)
     */  
    public enum Player {
        Human("Human"),
        Computer("Computer");
        private final String player;
        Player(final String player) {
            this.player = player;
        }
    }

    /** BoggleStats constructor NEEDS TO BE UPDATED
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

        // can make the below a separate method. Tries to read from previously saved stats
        // If there is no such file, it starts from default values.
        try {
            FileInputStream file = new FileInputStream("boggle/SavedStats.ser");
            ObjectInputStream in = new ObjectInputStream(file);
            BoggleStats savedStats = (BoggleStats) in.readObject();
            pScoreAllTime = savedStats.pScoreAllTime;
            pAverageWordsAllTime = savedStats.pAverageWordsAllTime;
            totalRounds = savedStats.totalRounds;
        }
        catch (IOException e) {
            pScoreAllTime = 0;
            pAverageWordsAllTime = 0;
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

    /**
    * Singleton Design Implementation:
    * A public method to return the instance of BoggleStats
    * @return BoggleStats instance
     */
    public static synchronized  BoggleStats getInstance(){
        if (instance == null){
            instance = new BoggleStats();
        }
        return instance;
    }

    /**
     * Add a word to a given player's word list for the current round.
     * The player's score is incremented, as words are added.
     * @param word The word to be added to the list
     * @param player The player to whom the word was awarded
     */
    public void addWord(String word, Player player) {
        word = word.toLowerCase();
        if (player == Player.Human){
            playerWords.add(word);
            if (word.length() >= 4){
                String spliced = word.substring(4);
                pScore += spliced.length() + 1;
            }
        } else if (player == Player.Computer){
            computerWords.add(word.toLowerCase());
            if (word.length() >= 4){
                String spliced = word.substring(4);
                cScore += spliced.length() + 1;
            }
        }
    }

    /**
     * End a given round.
     * This will clear out the human and computer word lists, so we can begin again.
     * The function will also update each player's total scores, average scores, and
     * reset the current scores for each player to zero. Finally, this function
     * increments the current round number by 1.
     */
    public void endRound() {

        pScoreTotal += pScore;
        cScoreTotal += cScore;

        pScoreAllTime += pScore;

        totalRounds += 1;

        round += 1;


        pAverageWords = (pAverageWords * (round - 1)) / round + playerWords.size() / (double) round;

        pAverageWordsAllTime = (pAverageWordsAllTime * (totalRounds - 1))
                / totalRounds + playerWords.size() / (double) totalRounds;

        cAverageWords = (cAverageWords * (round - 1)) / round + computerWords.size() / (double) round;

    }

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
    public int getPScore(){
        return pScore;
    }

    /**
     * Getter for cScore
     * @return cScore
     */
    public int getCScore(){
        return cScore;
    }
    /**
     * Setter for pScore
     * @param score the score to set pScore to
     */
    public void setPScore(int score){
        this.pScore = score;
    }

    public void setRound(int num){this.round = num;}

    /**
     * Clears this.playerWords and this.computerWords
     */
    public void clearWords() {
        this.playerWords.clear();
        this.computerWords.clear();
    }
    /**
     * Stores the stats of a given game in the boggle/SavedStats.ser file
     */
    public void storeStats() {

        try {
            FileOutputStream file = new FileOutputStream("boggle/SavedStats.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(this);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
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
     * Getter for this.round
     * @return int The number of rounds played this game
     */
    public int getRound() { return this.round; }

    /**
     * Getter for this.totalRounds
     * @return int The number of rounds played across all games
     */
    public int getTotalRounds() { return (int) this.totalRounds; }

    /**
     * Setter for this.totalRounds
     * @param totalRounds the number of totalRounds to be set
     */
    public void setTotalRounds(int totalRounds) {
        this.totalRounds = totalRounds;
    }

    public int getPScoreTotal(){return this.pScoreTotal;}

    public int getCScoreTotal(){return this.cScoreTotal;}
    public double getPAvgWordsAllTime(){return this.pAverageWordsAllTime;}

    public void setPAvgWordsAllTime(double avgWords){this.pAverageWordsAllTime = avgWords;}
    public double getPScoreAllTime(){return this.pScoreAllTime;}

    public void setCScore(int score){this.cScore = score;}

    public void setPScoreAllTime(int score){this.pScoreAllTime = score;}
}
