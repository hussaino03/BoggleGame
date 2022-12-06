package command;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This command redirects the user to a new screen
 */
public class RedirectScreenCommand implements Command {
    /**
     * The stage which the new screen (scene) belongs to
     */
    public Stage stage;
    /**
     * The screen (scene) to transition to
     */
    public Scene transitionScene;
    /**
     * The title to display on the new scene
     */
    public String transitionTitle;

    /**
     * RedirectScreenCommand Constructor
     * @param currStage the stage on which the GUI is set
     * @param newScene the scene to transition to
     * @param title the title to be displayed on the new scene
     */
    public RedirectScreenCommand(Stage currStage, Scene newScene, String title) {
        this.stage = currStage;
        this.transitionScene = newScene;
        this.transitionTitle = title;
    }

    /**
     * This method sets the stage to the new scene and sets its title to the new title
     */
    public void execute() {
        stage.setScene(transitionScene);
        stage.setTitle(transitionTitle);
    }
}
