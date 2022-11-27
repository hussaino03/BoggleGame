package command;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class RedirectScreenCommand implements Command {
    public Stage stage;
    public Scene transitionScene;

    public String transitionTitle;

    public RedirectScreenCommand(Stage currStage, Scene newScene, String title) {
        this.stage = currStage;
        this.transitionScene = newScene;
        this.transitionTitle = title;
    }
    @Override
    public void execute() {
//        System.out.println("Redirected Screen");
        stage.setScene(transitionScene);
        stage.setTitle(transitionTitle);
    }
}
