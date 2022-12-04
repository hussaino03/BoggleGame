package command;

import boggle.BoggleGame;
import boggle.BoggleStats;
import javafx.stage.Stage;
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
 * into commands and executed within the command.CommandCenter.
 */

public class CommandCenter implements EventHandler<ActionEvent> {
    /**
     * The application from which the command.CommandCenter processes events.
     */
    private gameWindow gameWindow;

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
     * Constructor for the command.CommandCenter class. Initializes a comparator which determines the order of
     * execution of commands, so that commandQueue can follow this order. The order is as follows:
     * RedirectScreenCommand -> UpdateUserChoiceCommand -> StartGameCommand -> DisplayGridElementsCommand
     * @param g The application from which command.CommandCenter should receive events to process.
     */
    private CommandCenter(gameWindow g) {
        /*
        Initialize command.CommandCenter's attributes
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
                    else if (c2 instanceof DisplayGridElementsCommand) {
                        return -1;
                    }
                }
                else if (c1 instanceof StartGameCommand) {
                    if (c2 instanceof RedirectScreenCommand) {
                        return 1;
                    }
                    else if (c2 instanceof UpdateUserChoiceCommand) {
                        return 1;
                    }
                    else if (c2 instanceof DisplayGridElementsCommand) {
                        return -1;
                    }
                }
                else if (c1 instanceof DisplayGridElementsCommand) {
                    return 1;
                }
                return 0;
            }
        };
        commandQueue = new PriorityQueue<Command>(commandComparator);
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
            Command c = commandQueue.poll();
            System.out.println(c.getClass());
            c.execute();
        }
    }

    /**
     * the handle() method processes events triggered by the user in the src.gameWindow application in order to
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

    /*
    IDs should be of the following format:
    "COMMANDS TO BE EXECUTED; COMMAND 1 INFO; COMMAND 2 INFO; ..."
    COMMANDS TO BE EXECUTED -> "RedirectScreen, UpdateUserChoice, ..."
    Info required by each command is as follows:
        StartGame: no info ("")
        DisplayStats: no info ("")
        ResetStats: no info ("")
        RedirectScreen: newScene ("Scene Name")
        DisplayGridElements: letters ("letters")
        UpdateUserChoice: choiceType, choice ("choiceType, choice")

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

        if (Id.contains(", ") && Id.length() >= 3) { // ID is not blank and is of right format
            String[] idVariables = Id.split(", "); // Split the ID into its variables
            System.out.println(idVariables.length);
            Stage stage = gameWindow.primaryStage;
            if (idVariables[0].equals("updateStats")) {
                BoggleStats stats = gameWindow.game.gameStats;
                this.setCommand(new DisplayGameStatsCommand(stats.getStatsMap(), stage));
                execute();
                return;
            }
            // All IDs have info for a RedirectScreenCommand at least, so we can process this info
            String newScene = idVariables[1];
            Scene transition = gameWindow.scenes.get(newScene);
            String title = gameWindow.sceneTitles.get(transition);

            if (idVariables.length == 2) { // if ID has two attributes, it has info for
                // a RedirectScreenCommand
                this.setCommand(new RedirectScreenCommand(stage, transition, title));
            }

            /*
            This must be modified for the IDs to reflect the fact that a title is no
            longer needed.
             */

            else if (idVariables.length == 3) { // if ID has three attributes, it has info
                // for a DisplayGridElementsCommand
                String letters = idVariables[0];
                int gridSize = Integer.parseInt(idVariables[1]);
                String displayTitle = idVariables[2];

                this.setCommand(new DisplayGridElementsCommand(letters, stage));
            }

            else if (idVariables.length == 4) { // If ID has four attributes, it has info for a RedirectScreenCommand
                // and an UpdateUserChoiceCommand

                // Process info for an UpdateUserChoiceCommand
                boggle.BoggleGame game = gameWindow.game;
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
        else if (Id.length() >= 16) { // If Id is a single string of characters of length 16 or more,
            // it represents a DisplayGridElementsCommand
            this.setCommand(new DisplayGridElementsCommand(Id, this.gameWindow.primaryStage));
            this.execute();
        }
    }
    /*
    IDs should be of the following format:
    "COMMANDS TO BE EXECUTED; COMMAND 1 INFO; COMMAND 2 INFO; ..."
    COMMANDS TO BE EXECUTED -> "RedirectScreen, UpdateUserChoice, ..."
    Info required by each command is as follows:
        StartGame: no info ("")
        DisplayStats: no info ("")
        ResetStats: no info ("")
        RedirectScreen: newScene ("Scene Name")
        DisplayGridElements: letters ("letters")
        UpdateUserChoice: choiceType, choice ("choiceType, choice")

     */

    private void processId(String command, String[] IdVariables, int i) {
        /*
        Commands with no info required
         */
        if (command.equals("StartGame")) {
            this.setCommand(
                    new StartGameCommand(this.gameWindow.game));
        }
        else if (command.equals("DisplayStats")) {
            this.setCommand(
                    new DisplayGameStatsCommand(BoggleStats.getInstance().getStatsMap(),
                            this.gameWindow.primaryStage));
        }
        else if (command.equals("RestStats")) {
            this.setCommand(new ResetStatsCommand());
        }
        /*
        Commands with info required
         */
        else if (command.equals("DisplayGridElements")) {
            String letters = IdVariables[i+1];
            this.setCommand(new DisplayGridElementsCommand(letters, this.gameWindow.primaryStage));
        }
        else if (command.equals("RedirectScreen")) {
            String newScene = IdVariables[i+1];
            Scene transition = gameWindow.scenes.get(newScene);
            String title = gameWindow.sceneTitles.get(transition);
            this.setCommand(new RedirectScreenCommand(
                    this.gameWindow.primaryStage, transition, title));
        }
        else if (command.equals("UpdateUserChoice")) {
            String[] params = IdVariables[i+1].split(", ");
            String choiceType = params[0];
            String choice = params[1];
            this.setCommand(new UpdateUserChoiceCommand(this.gameWindow.game, choiceType, choice));
        }
    }

    public static synchronized CommandCenter getInstance(gameWindow window) {
        if (instance == null) {
            instance = new CommandCenter(window);
        }
        return instance;
    }
    }

