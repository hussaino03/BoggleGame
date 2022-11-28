import boggle.BoggleGame;
import command.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * CommandCenter serves the purpose of an Invoker in the Command Design Pattern. The CommandCenter class is
 * responsible for processing events triggered by the user in the application. These events are encapsulated
 * into commands and executed within the CommandCenter.
 */

public class CommandCenter implements EventHandler<ActionEvent> {
    /**
     * The application from which the CommandCenter processes events.
     */
    private gameWindow gameWindow;

    /**
     * A queue of commands processed by the CommandCenter to be executed upon request.
     */
    public Queue<Command> commandQueue;

    private Comparator<Command> commandComparator;

    /**
     * Constructor for the CommandCenter class. Initializes a comparator which determines the order of
     * execution of commands, so that commandQueue can follow this order. The order is as follows:
     * RedirectScreenCommand -> UpdateUserChoiceCommand -> StartGameCommand
     * @param g The application from which CommandCenter should receive events to process.
     */
    public CommandCenter(gameWindow g) {
        /*
        Initialize CommandCenter's attributes
         */
        this.gameWindow = g;
        this.commandComparator = new Comparator<Command>() {
            @Override
            public int compare(Command c1, Command c2) {
                if (c1.getClass().equals(c2.getClass())) {
                    return 0;
                }
                else if (c1 instanceof RedirectScreenCommand) {
                    return -1;
                }
                else if (c1 instanceof UpdateUserChoiceCommand) {
                    if (c2 instanceof RedirectScreenCommand) {
                        return 1;
                    }
                    else if (c2 instanceof StartGameCommand) {
                        return -1;
                    }
                }
                else if (c1 instanceof StartGameCommand) {
                    return 1;
                }
                return 0;
            }
        };
        commandQueue = new PriorityQueue<Command>(commandComparator);
    }

    /**
     * Add a command to commandQueue for it to be executed upon request.
     * @param command
     */
    private void setCommand(Command command) {
        commandQueue.add(command);
    }

    /**
     * Execute all commands in the commandQueue.
     */
    private void execute() {
        while (!(commandQueue.isEmpty())) {
            Command c = commandQueue.poll();
            System.out.println(c.getClass());
            c.execute();
        }
    }

    /**
     * the handle() method processes events triggered by the user in the gameWindow application in order to
     * encapsulate these events into specific commands
     * @param actionEvent the event that was set off (e.g. button was pressed)
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Node event = (Node) actionEvent.getSource();
        String Id = event.getId(); // ID encapsulates command information

        if (Id.contains(", ") && Id.length() >= 3) { // ID is not blank and is of right format
            String[] idVariables = Id.split(", "); // Split the ID into its variables
            // All IDs have info for a RedirectScreenCommand at least, so we can process this info
            String newScene = idVariables[1];
            Stage stage = gameWindow.primaryStage;
            Scene transition = gameWindow.scenes.get(newScene);
            String title = gameWindow.sceneTitles.get(transition);

            if (idVariables.length == 2) { // if ID only has two attributes, it only has info
                // for a RedirectScreenCommand
                this.setCommand(new RedirectScreenCommand(stage, transition, title));
            }

            else if (idVariables.length == 4) { // If ID has four attributes, it has info for a RedirectScreenCommand
                // and an UpdateUserChoiceCommand

                // Process info for an UpdateUserChoiceCommand
                BoggleGame game = gameWindow.game;
                String choiceType = idVariables[2];
                String choice = idVariables[3];

                this.setCommand(new RedirectScreenCommand(stage, transition, title));
                this.setCommand(new UpdateUserChoiceCommand(game, choiceType, choice));
            }

            else if (idVariables.length == 5) { // If ID has five attributes, it has info for a RedirectScreenCommand,
                // an UpdateUserChoiceCommand and a StartGameCommand

                // Process info for an UpdateUserChoiceCommand
                BoggleGame game = gameWindow.game;
                String choiceType = idVariables[2];
                String choice = idVariables[3];

                this.setCommand(new UpdateUserChoiceCommand(game, choiceType, choice));
                this.setCommand(new StartGameCommand(game));
                this.setCommand(new RedirectScreenCommand(stage, transition, title));
            }
            this.execute(); // Execute all commands once information has been processed
        }
        }
    }

