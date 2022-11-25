import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import java.util.HashMap;


public class gameWindow extends Application {

    HashMap<String, Scene> scenes = new HashMap<String, Scene>();
    HashMap<Scene, String> sceneTitles = new HashMap<Scene, String>();
    Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }

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
        normalModeButton.setId("Normal Mode, Grid Selection Scene");

        Button timedModeButton = new Button("Timed Mode [W]");
        timedModeButton.setId("Timed Mode, Grid Selection Scene");

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
        // "ID, Scene to transition to"
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

        // Setup for stats scene and layout
        GridPane statsLayout =  new GridPane();
        statsLayout.setPadding(new Insets(10,10,10,10));
        statsLayout.setVgap(20);
        statsLayout.setHgap(20);
        Scene statsScene = new Scene(statsLayout, 700, 700);
        scenes.put("Stats Scene", statsScene);
        sceneTitles.put(statsScene, "Stats");

        // Add button to stats layout
        Button goBackFromStatsButton = new Button("Return to Main Menu [R]");
        goBackFromStatsButton.setId("Return to Main Menu, Main Scene");
        statsLayout.add(goBackFromStatsButton, 5, 5);

        // Setup for grid selection scene and layout
        GridPane gridSelectionLayout =  new GridPane();
        gridSelectionLayout.setPadding(new Insets(10,10,10,10));
        gridSelectionLayout.setVgap(20);
        gridSelectionLayout.setHgap(20);
        Scene gridSelectionScene = new Scene(gridSelectionLayout, 700, 700);
        scenes.put("Grid Selection Scene", gridSelectionScene);
        sceneTitles.put(gridSelectionScene, "Grid Selection");

        // add button to grid selection layout
        Button goBackFromGridSelectionButton = new Button("Return to Main Menu [R]");
        goBackFromGridSelectionButton.setId("Return to Main Menu, Main Scene");
        gridSelectionLayout.add(goBackFromGridSelectionButton, 5, 5);

        // Send all button clicks to commandCenter for these commands to be handled
        CommandCenter commandCenter = new CommandCenter(this);
        howToPlayButton.setOnAction(commandCenter);
        statsButton.setOnAction(commandCenter);
        normalModeButton.setOnAction(commandCenter);
        timedModeButton.setOnAction(commandCenter);
        goBackFromHTPButton.setOnAction(commandCenter);
        goBackFromStatsButton.setOnAction(commandCenter);
        goBackFromGridSelectionButton.setOnAction(commandCenter);

        // Allow buttons to be fired through keyboard to make the program more accessible
        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case Q: normalModeButton.fire();
                    case W: timedModeButton.fire();
                    case A: howToPlayButton.fire();
                    case S: statsButton.fire();
                }
            }
        });

        gridSelectionScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.R) {
                    goBackFromGridSelectionButton.fire();
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
