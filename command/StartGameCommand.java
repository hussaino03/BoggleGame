package command;

import boggle.BoggleGame;

public class StartGameCommand implements Command {
    private BoggleGame game;

    public void execute() {
        if (game.ready()) {
            game.playGame();
        }
    }
}
