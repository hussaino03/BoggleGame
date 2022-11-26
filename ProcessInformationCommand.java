import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProcessInformationCommand implements Command {
    public Stage stage;

    public String choice;

    public ProcessInformationCommand(Stage currStage, String c) {
        this.stage = currStage;
        this.choice = c;
    }
    @Override
    public void execute() {
        System.out.println("Processed information");
        System.out.println(this.choice);
    }
}
