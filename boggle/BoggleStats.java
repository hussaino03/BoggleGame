package boggle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * The BoggleStats class for the first Assignment in CSC207, Fall 2022
 * The BoggleStats will contain statsitics related to game play Boggle 
 */
public class BoggleStats {
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
    public BoggleStats() {
        round = 0;
        pScoreTotal = 0;
        cScoreTotal = 0;
        pAverageWords = 0;
        cAverageWords = 0;
        playerWords = new HashSet<String>();
        computerWords = new HashSet<String>();
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

        pScore = 0;
        cScore = 0;
        round += 1;

        pAverageWords = (pAverageWords * (round - 1)) / round + playerWords.size() / (double) round;

        cAverageWords = (cAverageWords * (round - 1)) / round + computerWords.size() / (double) round;

        playerWords.clear();
        computerWords.clear();

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
     * @return int The number of rounds played
     */
    public int getRound() { return this.round; }

    /*
    * @return int The current player score
    */
    public int getScore() {
        return this.pScore;
    }

}
