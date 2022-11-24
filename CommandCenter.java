import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class CommandCenter implements EventHandler<ActionEvent> {

    // This class serves as the invoker for the command pattern
    private gameWindow gameWindow;
    private Queue<Command> commandQueue;

    public CommandCenter(gameWindow g) {
        this.gameWindow = g;
        commandQueue = new LinkedList<Command>();
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
        String newScene = event.getId().split(", ")[1];
        System.out.println(newScene);
        Stage stage = gameWindow.primaryStage;
        Scene transition = gameWindow.scenes.get(newScene);
        String title = gameWindow.sceneTitles.get(transition);
        this.setCommand(new RedirectScreenCommand(stage, transition, title));
        this.execute();
        }

    }

