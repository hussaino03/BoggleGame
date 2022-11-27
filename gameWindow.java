import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.HashMap;

/**
 * gameWindow controls the flow of the program
 */

public class gameWindow extends Application {

    HashMap<String, Scene> scenes = new HashMap<String, Scene>();
    HashMap<Scene, String> sceneTitles = new HashMap<Scene, String>();
    Stage primaryStage;
    private TableView table = new TableView();
    private int roundNumber;
    private int roundScore;
    private int totalScore;
    private int compScore;

    public int getRoundNumber() {return roundNumber;}
    public int getRoundScore() {return roundScore;}
    public int getTotalScore() {return totalScore;}
    public int getCompScore() {return compScore;}
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start() method makes the window run
     * @param stage The game window to be displayed
     * @throws Exception Thrown if game does not run
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        stage.setTitle("Main Menu"); // Stage setup

        // Four different buttons the user can select
        Button howToPlayButton = new Button("How to Play [A]");
        howToPlayButton.setId("How to Play, How To Play Scene");

        Button statsButton = new Button("Stats [S]");
        statsButton.setId("Stats, Stats Scene");

        Button normalModeButton = new Button("Normal Mode [Q]");
        normalModeButton.setId("Normal Mode, Grid Selection Scene, normal");

        Button timedModeButton = new Button("Timed Mode [W]");
        timedModeButton.setId("Timed Mode, Grid Selection Scene, timed");

        // Setup for the main scene and layout [Contains the four buttons]
        GridPane mainLayout =  new GridPane();
        mainLayout.setPadding(new Insets(10,10,10,10));
        mainLayout.setVgap(20);
        mainLayout.setHgap(20);
        Scene mainScene = new Scene(mainLayout, 700, 700);
        scenes.put("Main Scene", mainScene);
        sceneTitles.put(mainScene, "Main Menu");

        // add buttons to main layout
        // buttonIds are used to encapsulate commands via the following ID format:
        // "ID, Scene to transition to, choice"
        mainLayout.add(normalModeButton, 10, 10);
        mainLayout.add(timedModeButton, 11, 10);
        mainLayout.add(howToPlayButton, 10, 11);
        mainLayout.add(statsButton, 11, 11);

        // Setup for How to Play scene and layout
        GridPane howToPlayLayout =  new GridPane();
        howToPlayLayout.setPadding(new Insets(10,10,10,10));
        howToPlayLayout.setVgap(20);
        howToPlayLayout.setHgap(20);
        Scene howToPlayScene = new Scene(howToPlayLayout, 700, 700);
        scenes.put("How To Play Scene", howToPlayScene);
        sceneTitles.put(howToPlayScene, "How to Play");

        // Add text and button to how to play layout
        Label gameInstructions = new Label(" Boggle is a game where you try to find as" +
                " many words as you can in a randomized grid of letters. \n Words can be" +
                " formed by joining letters which are vertically, horizontally or diagonally" +
                " adjacent. \n You will earn points based on the length of the word found. \n " +
                "You can choose between two game modes: Normal and Timed. \n In the Normal Game Mode, " +
                "you will play against the computer. \n You will be given a grid of letters and should" +
                " form as many words as you can. \n When you can no longer think of any words, the " +
                "computer will find all remaining words. \n At the end of each round, you will be given" +
                " information regarding your current standing against the computer. \n Your goal is to" +
                " end the game with a higher score than the computer. \n In the Timed Game Mode, you" +
                " will play against yourself. \n You will be given a grid of letters and should form" +
                " as many words as you can before time runs out. \n Each word you find will extend your " +
                "time proportional to the length of the word found. \n Your goal is to beat your " +
                "previous high score. \n You can return to this page at any time to review the " +
                "game instructions. \n You can also go to the Stats Page to review your success across" +
                " all games.");

        Button goBackFromHTPButton = new Button("Return to Main Menu [R]");
        goBackFromHTPButton.setId("Return to Main Menu, Main Scene");
        howToPlayLayout.add(gameInstructions, 0, 0);
        howToPlayLayout.add(goBackFromHTPButton, 0, 1);

        // Normal Gamemode Scene and Layout

        GridPane normalGamemodeLayout =  new GridPane();
        normalGamemodeLayout.setPadding(new Insets(10,10,10,10));
        normalGamemodeLayout.setVgap(20);
        normalGamemodeLayout.setHgap(20);
        Scene normalGamemodeScene = new Scene(normalGamemodeLayout, 700, 700);
        scenes.put("Normal Gamemode Scene", normalGamemodeScene);
        sceneTitles.put(normalGamemodeScene, "Normal Gamemode");
        Label normalGamemodeStats = new Label("Round Number: " + roundNumber +" | Round Score: " + roundScore+ " | " +
                "Total Score: " + totalScore + " | Computer Score: " + compScore);
        Button goBackFromNormalGamemode = new Button("Return to Main Menu [R]");
        goBackFromNormalGamemode.setId("Return to Main Menu, Main Scene");
        normalGamemodeLayout.add(normalGamemodeStats, 0, 0);
        normalGamemodeLayout.add(goBackFromNormalGamemode, 0, 1);


        // Setup for stats scene and layout

        //set table to non-editable
        table.setEditable(false);

        //normal stats column
        TableColumn<String, String> normalStatColumn = new TableColumn<>("Normal Stat");
        normalStatColumn.setMaxWidth(60);

        //normal stats values column
        TableColumn<String, Double> normalValueColumn = new TableColumn<>("Values");
        normalValueColumn.setMaxWidth(60);

        //timed stats column
        TableColumn<String, String> timedStatColumn = new TableColumn<>("Values");
        timedStatColumn.setMaxWidth(60);

        //timed stats values column
        TableColumn<String, Double> timedValueColumn = new TableColumn<>("Values");
        timedValueColumn.setMaxWidth(60);

        //add columns to table
        table.getColumns().addAll(normalStatColumn, normalValueColumn, timedStatColumn, timedValueColumn);

        Scene statsScene = new Scene(table, 700, 700);
        scenes.put("Stats Scene", statsScene);
        sceneTitles.put(statsScene, "Stats");

        // Add button to stats layout
        Button goBackFromStatsButton = new Button("Return to Main Menu [R]");
        goBackFromStatsButton.setId("Return to Main Menu, Main Scene");
//        statsLayout.add(goBackFromStatsButton, 5, 5);

        // Setup for grid selection scene and layout

        GridPane gridSelectionLayout =  new GridPane();
        gridSelectionLayout.setPadding(new Insets(10,10,10,10));
        gridSelectionLayout.setVgap(20);
        gridSelectionLayout.setHgap(20);
        Scene gridSelectionScene = new Scene(gridSelectionLayout, 700, 700);
        scenes.put("Grid Selection Scene", gridSelectionScene);
        sceneTitles.put(gridSelectionScene, "Grid Selection");

        // Label to explain how to choose grid size
        Label gridInstructions = new Label("Enter 1 to play on a (4x4) grid; 2 to play on a (5x5) grid;" +
                " 3 to play on a (6x6) grid:");
        gridSelectionLayout.add(gridInstructions,0,1);

        //textbox to input size
//        TextField inpGrid = new TextField("Input here");
//        gridSelectionLayout.add(inpGrid,1,1);

        // add buttons to grid selection layout
        Button fourByFourButton = new Button("4x4 [1]");
        fourByFourButton.setId("Four By Four Button, Normal Gamemode Scene, four");

        Button fiveByFiveButton = new Button("5x5 [2]");
        fiveByFiveButton.setId("Five By Five Button, Normal Gamemode Scene, five");

        Button sixBysixButton = new Button("6x6 [3]");
        sixBysixButton.setId("Four By Four Button, Normal Gamemode Scene, six");

        Button goBackFromGridSelectionButton = new Button("Return to Main Menu [R]");
        goBackFromGridSelectionButton.setId("Return to Main Menu, Main Scene");
        gridSelectionLayout.add(goBackFromGridSelectionButton, 5, 5);
        gridSelectionLayout.add(fourByFourButton, 5, 2);
        gridSelectionLayout.add(fiveByFiveButton, 5, 3);
        gridSelectionLayout.add(sixBysixButton, 5, 4);

        // Send all button clicks to commandCenter for these commands to be handled
        CommandCenter commandCenter = new CommandCenter(this);
        howToPlayButton.setOnAction(commandCenter);
        statsButton.setOnAction(commandCenter);
        normalModeButton.setOnAction(commandCenter);
        timedModeButton.setOnAction(commandCenter);
        goBackFromHTPButton.setOnAction(commandCenter);
        goBackFromStatsButton.setOnAction(commandCenter);
        goBackFromNormalGamemode.setOnAction(commandCenter);
        goBackFromGridSelectionButton.setOnAction(commandCenter);
        fourByFourButton.setOnAction(commandCenter);
        fiveByFiveButton.setOnAction(commandCenter);
        sixBysixButton.setOnAction(commandCenter);

        // Allow buttons to be fired through keyboard to make the program more accessible
        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode c = keyEvent.getCode();
                if (c == KeyCode.Q) {
                    normalModeButton.fire();
                }
                else if (c == KeyCode.W) {
                    timedModeButton.fire();
                }
                else if (c == KeyCode.A) {
                    howToPlayButton.fire();
                }
                else if (c == KeyCode.S) {
                    statsButton.fire();
                }
            }
        });

        gridSelectionScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case R: goBackFromGridSelectionButton.fire();
                }
            }
        });

        howToPlayScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.R) {
                    goBackFromHTPButton.fire();
                }
            }
        });

        statsScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.R) {
                    goBackFromStatsButton.fire();
                }
            }
        });

        // Set the scene to the main scene when first running the game
        stage.setScene(mainScene);
        stage.show();
    }
}
