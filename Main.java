import boggle.BoggleGame;
import src.gameWindow;

/**
 * The Main class for the first Assignment in CSC207, Fall 2022
 */
public class Main {
    /**
     * Main method.
     * @param args command line arguments.
     **/
    public static void main(String[] args) {
        gameWindow g = new gameWindow();
        BoggleGame b = new BoggleGame(g);
        b.choiceProcessor.put("Game Mode", "normal");
        b.choiceProcessor.put("Grid Size", "four");
//        b.giveInstructions();
        b.playGame();
    }

}