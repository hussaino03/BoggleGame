package command;

import boggle.BoggleGame;

public class StartGameCommand implements Command {
    private final BoggleGame game;

    public StartGameCommand(BoggleGame g) {
        this.game = g;
    }

    public void execute() {
        if (game.ready()) {
            game.playGame();
        }
    }
}
