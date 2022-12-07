package command;

import boggle.BoggleGame;
import javafx.stage.Stage;

/**
 * This command processes user decisions and lets BoggleGame know which decisions were made.
 */

public class UpdateUserChoiceCommand implements Command {
    /**
     * The type of choice the user is making
     */
    public String choiceType;
    /**
     * The choice made by the user
     */
    public String choice;
    /**
     * The game which should process the user's decision
     */
    public BoggleGame game;

    /**
     * UpdateUserChoiceCommand constructor
     * @param g The game which should process the user's decision
     * @param cT The type of choice the user is making
     * @param c The choice made by the user
     */
    public UpdateUserChoiceCommand(BoggleGame g, String cT, String c) {
        this.game = g;
        this.choiceType = cT;
        this.choice = c;
    }

    /**
     * Update the game's choiceProcessor to store the user's decision
     */
    @Override
    public void execute() {
        game.choiceProcessor.put(choiceType, choice);
    }
}
