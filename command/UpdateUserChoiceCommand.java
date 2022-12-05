package command;

import boggle.BoggleGame;
import javafx.stage.Stage;

/**
 * This command processes user input. May be changed to command.UpdateUserChoiceCommand, to be more fitting.
 */
public class UpdateUserChoiceCommand implements Command {
    public String choiceType;

    public String choice;

    public BoggleGame game;

    public UpdateUserChoiceCommand(BoggleGame g, String cT, String c) {
        this.game = g;
        this.choiceType = cT;
        this.choice = c;
    }
    @Override
    public void execute() {
        game.choiceProcessor.put(choiceType, choice);
    }
}
