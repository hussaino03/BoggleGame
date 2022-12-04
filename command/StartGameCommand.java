package command;

import boggle.BoggleGame;
import javafx.concurrent.Task;

import java.io.IOException;

public class StartGameCommand implements Command {
    private final BoggleGame game;

    public StartGameCommand(BoggleGame g) {
        this.game = g;
    }

    public void execute() {
            Thread t = new Thread(()->{
                if (game.ready()) {
                    try {
                        game.playGame();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            t.start();

    }
}
