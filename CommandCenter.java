import boggle.BoggleGame;
import command.Command;
import command.RedirectScreenCommand;
import command.UpdateUserChoiceCommand;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Comparator;

public class CommandCenter implements EventHandler<ActionEvent> {

    // This class serves as the invoker for the command pattern
    private gameWindow gameWindow;
    private Queue<Command> commandQueue;

    private Comparator<Command> commandComparator = new Comparator<Command>() {
        @Override
        public int compare(Command c1, Command c2) {
            return 1;
        }
    };

    public CommandCenter(gameWindow g) {
        this.gameWindow = g;
        commandQueue = new PriorityQueue<Command>(commandComparator);
    }

    private void setCommand(Command command) {
        commandQueue.add(command);
    }

    private void execute() {
        for (Command c : this.commandQueue) {
            commandQueue.remove(c);
            c.execute();
        }
    }
    // handle method which processes commands

    /**
     * the handle() method processes events triggered by the user in the gameWindow application in order to
     * encapsulate these events into specific commands
     * @param actionEvent the event that was set off (e.g. button was pressed)
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Node event = (Node) actionEvent.getSource();
        String Id = event.getId();
        System.out.println(Id);
        if (Id.contains(", ") && Id.length() >= 3) {
            String[] idVariables = Id.split(", ");
            String newScene = idVariables[1];
            Stage stage = gameWindow.primaryStage;
            Scene transition = gameWindow.scenes.get(newScene);
            String title = gameWindow.sceneTitles.get(transition);
            this.setCommand(new RedirectScreenCommand(stage, transition, title));
            this.execute();
            if (idVariables.length > 2) { // Store info
                BoggleGame game = gameWindow.game;
                String choiceType = idVariables[2];
                String choice = idVariables[3];
                this.setCommand(new UpdateUserChoiceCommand(stage, game, choiceType, choice));
                this.execute();
            }
        }


        }

    }

