import javafx.scene.Scene;
import javafx.stage.Stage;

public class RedirectScreenCommand implements Command {
    Stage stage;
    Scene transitionScene;

    String transitionTitle;

    public RedirectScreenCommand(Stage currStage, Scene newScene, String title) {
        this.stage = currStage;
        this.transitionScene = newScene;
        this.transitionTitle = title;
    }
    @Override
    public void execute() {
        stage.setScene(transitionScene);
        stage.setTitle(transitionTitle);
    }
}
