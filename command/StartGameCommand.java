package command;

import boggle.BoggleGame;
import javafx.concurrent.Task;

public class StartGameCommand implements Command {
    private final BoggleGame game;

    public StartGameCommand(BoggleGame g) {
        this.game = g;
    }

    public void execute() {
            Task playGame = new Task<Void>() {
                @Override
                public Void call() {
                    if (game.ready()) {
                        game.playGame();
                    }
                    return null;
                }
            };
            new Thread(playGame).start();
    }
}
