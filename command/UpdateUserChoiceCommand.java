package command;

import boggle.BoggleGame;
import javafx.stage.Stage;

/**
 * This command processes user input. May be changed to command.UpdateUserChoiceCommand, to be more fitting.
 */
public class UpdateUserChoiceCommand implements Command {
    public Stage stage;
    public String choiceType;

    public String choice;

    public BoggleGame game;

    public UpdateUserChoiceCommand(Stage currStage, BoggleGame g, String cT, String c) {
        this.stage = currStage;
        this.game = g;
        this.choiceType = cT;
        this.choice = c;
    }
    @Override
    public void execute() {
//        System.out.println("Processed information");
        game.choiceProcessor.put(choiceType, choice);
//        System.out.println(game.ready());
    }
}
