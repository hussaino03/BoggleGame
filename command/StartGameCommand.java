package command;

import boggle.BoggleGame;

/**
 * This command starts the game
 */

public class StartGameCommand implements Command {
    /**
     * The game which is to be started
     */
    private final BoggleGame game;

    /**
     * StartGameCommand constructor
     * @param g The game which is to be started
     */
    public StartGameCommand(BoggleGame g) {
        this.game = g;
    }

    /**
     * Checks if a grid size has been selected and starts the game if so. Done on a separate thread
     * so that playGame()'s infinite while loop and the GUI can run concurrently.
     */
    public void execute() {
            Thread t = new Thread(()->{
                if (game.gridSizeSelected()) {
                        game.playGame();
                }
            });
            t.start();
    }
}
