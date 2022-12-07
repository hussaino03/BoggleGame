package command;

import boggle.BoggleStats;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Comparator;
import src.*;

/**
 * command.CommandCenter serves the purpose of an Invoker in the Command Design Pattern. The command.CommandCenter class is
 * responsible for processing events triggered by the user in the application. These events are encapsulated
 * into commands and executed within the CommandCenter.
 */

public class CommandCenter implements EventHandler<ActionEvent> {
    /**
     * The application from which the CommandCenter processes events.
     */
    private GameWindow gameWindow;

    /**
     * A queue of commands processed by the command.CommandCenter to be executed upon request.
     */
    public Queue<Command> commandQueue;

    private Comparator<Command> commandComparator;

    /**
     * Private instance of CommandCenter following the Singleton pattern
     */

    private static CommandCenter instance = null;

    /**
     * CommandCenter Constructor.
     * @param g The application from which CommandCenter should receive events to process.
     */
    private CommandCenter(GameWindow g) {
        this.gameWindow = g;
        commandComparator = generateComparator();
        commandQueue = new PriorityQueue<>(commandComparator);
    }

    /**
     * Add a command to commandQueue for it to be executed upon request.
     * @param command the command added to the queue
     */
    public void setCommand(Command command) {
        commandQueue.add(command);
    }

    /**
     * Execute all commands in the commandQueue.
     */
    private void execute() {
        while (!(commandQueue.isEmpty())) {
            Command c = commandQueue.peek();
            c.execute();
            commandQueue.remove(c);
        }
    }

    /**
     * the handle() method processes events triggered by the user in the src.GameWindow application in order to
     * encapsulate these events into specific commands
     * @param actionEvent the event that was set off (e.g. button was pressed)
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Node event = (Node) actionEvent.getSource();
        String Id = event.getId(); // ID encapsulates command information
        this.handle(Id);
        }

    /**
     * handle() is an overloaded method, so it can also accept a string as a parameter
     * and process it directly, without needing to first convert an ActionEvent to a string
     * @param Id The id of the event to be processed
     */
    public void handle(String Id) {
        if (!(Id.contains("; "))) { // Ids of the wrong format should not be handled
            return;
        }

        // Decompose Id
        String[] IdVariables = Id.split("; ");
        String executableCommands = IdVariables[0]; // Commands to be processed

        if (executableCommands.contains(", ")) { // Multiple commands need to be processed
            String[] commands = executableCommands.split(", ");
            for (int i=0; i < commands.length; i++) {
                this.processId(commands[i], IdVariables, i);
            }
        }
        else { // A single command needs to be processed
            String command = executableCommands;
            this.processId(command, IdVariables, 0);
        }
        this.execute();
    }

    private void processId(String command, String[] IdVariables, int i) {
        switch (command) {
            case "StartGame" -> this.setCommand(
                    new StartGameCommand(this.gameWindow.game));
            case "DisplayInGameStats" -> this.setCommand(
                    new DisplayInGameStatsCommand(BoggleStats.getInstance().getStatsMap(),
                            this.gameWindow.primaryStage));
            case "DisplayRoundStats" -> this.setCommand(new DisplayRoundStatsCommand(
                    BoggleStats.getInstance().getStatsMap(), this.gameWindow.primaryStage));
            case "DisplayGameStats" -> this.setCommand(new DisplayGameStatsCommand(
                    BoggleStats.getInstance().getStatsMap(), this.gameWindow.primaryStage));
            case "ResetStats" -> this.setCommand(new ResetStatsCommand());
            case "ResetInGameStats" -> this.setCommand(new ResetInGameStatsCommand());
            case "DisplayGridElements" -> {
                String letters = IdVariables[i + 1];
                this.setCommand(new DisplayGridElementsCommand(letters, this.gameWindow.primaryStage));
            }
            case "RedirectScreen" -> {
                String[] params = IdVariables[i + 1].split(", ");
                String newScene = params[0];
                Scene transition = gameWindow.scenes.get(newScene);
                String title = gameWindow.sceneTitles.get(transition);
                this.setCommand(new RedirectScreenCommand(
                        this.gameWindow.primaryStage, transition, title));
            }
            case "UpdateUserChoice" -> {
                String[] params = IdVariables[i + 1].split(", ");
                String choiceType = params[0];
                String choice = params[1];
                this.setCommand(new UpdateUserChoiceCommand(this.gameWindow.game, choiceType, choice));
            }
        }
    }

    /**
     * Obtain an instance of CommandCenter. If no instance exists, create a new one
     * Otherwise, obtain the same instance which already exists. This guarantees at most
     * one instance of CommandCenter exists at all times while the program runs. This is
     * an implementation of the Singleton Design Pattern
     * @param window the window which CommandCenter should transmit to and receive
     * commands from
     * @return CommandCenter the universal instance of CommandCenter
     */
    public static synchronized CommandCenter getInstance(GameWindow window) {
        if (instance == null) {
            instance = new CommandCenter(window);
            }
        return instance;
        }

    /**
     * Initializes a comparator which determines the order of execution of commands,
     * so that commandQueue can follow this order. The order specifies which commands
     * must happen before others.
     * @return Comparator the comparator which commandQueue should use.
     */


    private Comparator<Command> generateComparator() {
        Comparator<Command> comparator = new Comparator<Command>() {
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
                    } else if (c2 instanceof StartGameCommand) {
                        return -1;
                    } else if (c2 instanceof DisplayGridElementsCommand) {
                        return -1;
                    }
                } else if (c1 instanceof StartGameCommand) {
                    if (c2 instanceof RedirectScreenCommand) {
                        return 1;
                    } else if (c2 instanceof UpdateUserChoiceCommand) {
                        return 1;
                    } else if (c2 instanceof DisplayGridElementsCommand) {
                        return -1;
                    }
                } else if (c1 instanceof DisplayGridElementsCommand ||
                c1 instanceof DisplayRoundStatsCommand || c1 instanceof DisplayGameStatsCommand
                || c1 instanceof DisplayInGameStatsCommand) {
                    return 1;
                }
                return 0;
            }
        };

        return comparator;
    }
}

