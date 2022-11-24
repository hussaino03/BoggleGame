import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

import java.util.PriorityQueue;
import java.util.Queue;

public class CommandCenter implements EventHandler<ActionEvent> {

    // This class serves as the invoker for the command pattern
    Stage stage;
    Queue<Command> commandQueue;

    // Information required for RedirectScreenCommand
    Scene transitionScene;

    String transitionTitle;

    // Information required for ProcessInformationCommand

    String choice;


    // Constructor which takes in the information from the command for RedirectScreen

    // Maybe we can overload the constructor with different parameters
    // For example, one constructor only takes in the info for RedirectScreen so that
    // unnecessary information is not passed in
    public CommandCenter(Stage newStage, Scene newScene, String title, String info) {
        this.stage = newStage;
        this.transitionScene = newScene;
        this.transitionTitle = title;
        this.choice = info;
        commandQueue = new PriorityQueue<Command>();
    }

    private void setCommand(Command command) {
        commandQueue.add(command);
    }

    private void execute() {
        for (Command c : this.commandQueue) {
            c.execute();
        }
    }
    // handle method which processes commands
    @Override
    public void handle(ActionEvent actionEvent) {
        Node event = (Node) actionEvent.getSource();
        if (event.getId().equals("How to Play") || event.getId().equals("Return to Main Menu")) {
            this.setCommand(new RedirectScreenCommand(stage, transitionScene, transitionTitle));
        }
        this.execute();
        }

    }

