package src;

import boggle.*;
import command.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.HashMap;

/**
 * src.gameWindow controls the flow of the program
 */

public class gameWindow extends Application {

    public HashMap<String, Scene> scenes = new HashMap<String, Scene>();
    public HashMap<Scene, String> sceneTitles = new HashMap<Scene, String>();
    public Stage primaryStage;
    private TableView table;
    public CommandCenter commandCenter;
    private int roundNumber;
    private int roundScore;
    private int totalScore;
    private int compScore;
    public BoggleGame game;

    public int getRoundNumber() {return roundNumber;}
    public int getRoundScore() {return roundScore;}
    public int getTotalScore() {return totalScore;}
    public int getCompScore() {return compScore;}

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start() method makes the window run
     * Note that button IDs have the following format:
     * "buttonName, transitionScene, choiceType, choice, gameState"
     * buttonName refers to what button this is (e.g "Normal Mode")
     * transitionScene refers to which scene to transition to (e.g. "Grid Selection Scene")
     * choiceType refers to what kind of choice needs to be updated (e.g. "Game Mode")
     * choice refers to the choice made by the user of a specific choiceType (e.g. "normal")
     * gameState refers to how the gameState should be changed (e.g. "start")
     * @param stage The game window to be displayed
     * @throws Exception Thrown if game does not run
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        primaryStage.setTitle("Main Menu"); // Stage setup

        this.game = new BoggleGame(this); // Ready the game for the player

        // Four different buttons the user can select
        Button howToPlayButton = new Button("How to Play [A]");
        howToPlayButton.setId("How to Play, Normal Game Mode Round Summary Scene");

        Button statsButton = new Button("Stats [S]");
        statsButton.setId("Stats, Stats Scene");

        Button normalModeButton = new Button("Normal Mode [Q]");
        normalModeButton.setId("Normal Mode, Grid Selection Scene, Game Mode, normal");

        Button timedModeButton = new Button("Timed Mode [W]");
        timedModeButton.setId("Timed Mode, Grid Selection Scene, Game Mode, normal");

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
        
        // start Display in-game stats 
        // -----------------------------------------------------------------------------------------------
        // Normal Gamemode Scene and Layout

        BorderPane normalGamemodeLayout =  new BorderPane();
        normalGamemodeLayout.setPadding(new Insets(10,10,10,10));
        Scene normalGamemodeScene = new Scene(normalGamemodeLayout, 700, 700);
        scenes.put("Normal Gamemode Scene", normalGamemodeScene);
        sceneTitles.put(normalGamemodeScene, "Normal Gamemode");
        Label normalGamemodeStats = new Label(game.gameStats.playerwords() +" | "+ game.gameStats.computerwords()+ " | "+game.gameStats.PScore() +" | "+ game.gameStats.CScore());
        Button goBackFromNormalGamemode = new Button("Return to Main Menu [R]");
        goBackFromNormalGamemode.setId("Return to Main Menu, Main Scene");
        Button endRound = new Button("End the Round");
        TextField inpWord = new TextField("Input here");
        HBox statsBar = new HBox();
        VBox buttonBar = new VBox();
        statsBar.getChildren().add(normalGamemodeStats);
        buttonBar.getChildren().add(endRound);
        statsBar.setAlignment(Pos.CENTER);
        normalGamemodeLayout.setRight(buttonBar);
        normalGamemodeLayout.setTop(statsBar);
        normalGamemodeLayout.setRight(buttonBar);
        normalGamemodeLayout.setBottom(inpWord);
        //--------------------------------------------------


        endRound.setId("Go to game summary screen, Normal Game Mode Game Summary Scene");
        // -------------------------------------------------------------------------------------------------

        // Setup for stats scene and layout

        //set table to non-editable
        this.table = new TableView();
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

        // Start Normal Game Mode Round Summary Screen
        // -----------------------------------------------------------------------------------------------------------//
        Button goBackFromGameRoundSummaryButton = new Button("Return to Main Menu [R]");
        goBackFromGameRoundSummaryButton.setId("Return to Main Menu, Main Scene");

        Button goToGameSummary = new Button("Go To Game Summary [G]");
        goToGameSummary.setId("Go To Game Summary, Normal Game Mode Game Summary Scene");

        // add grid panels
        GridPane normalSummaryLayout =  new GridPane();
        normalSummaryLayout.setPadding(new Insets(10,10,10,10));
        normalSummaryLayout.setVgap(20);
        normalSummaryLayout.setHgap(20);
        normalSummaryLayout.add(goToGameSummary, 0, 3);
        normalSummaryLayout.add(goBackFromGameRoundSummaryButton, 0, 2);

        // create a new scene
        Scene normalSummary = new Scene(normalSummaryLayout, 700, 700);
        scenes.put("Normal Game Mode Round Summary Scene", normalSummary);
        sceneTitles.put(normalSummary, "Normal Game Mode Round Summary");

        Label IntroText = new Label("The stats for the normal game mode round summary screen are displayed as follows:");
        IntroText.setTextAlignment(TextAlignment.CENTER);

        normalSummaryLayout.add(IntroText, 0, 0);

        Label pscore = new Label(BoggleStats.getInstance().PScore());
        Label cscore = new Label(BoggleStats.getInstance().CScore());
        Label csize = new Label(BoggleStats.getInstance().computerwordsSize());
        Label psize = new Label(BoggleStats.getInstance().playerwordsSize());
        Label cwords = new Label(BoggleStats.getInstance().computerwords());
        Label pwords = new Label(BoggleStats.getInstance().playerwords());

        normalSummaryLayout.add(pscore, 2, 4);
        normalSummaryLayout.add(cscore, 2, 5);
        normalSummaryLayout.add(csize, 2, 6);
        normalSummaryLayout.add(psize, 2, 7);
        normalSummaryLayout.add(cwords, 2, 8);
        normalSummaryLayout.add(pwords, 2, 9);

        // -----------------------------------------------------------------------------------------------------------//

        // Start Normal Game Mode Game Summary Screen
        // -----------------------------------------------------------------------------------------------------------//

        Button goBackFromGameRoundButton = new Button("Return to Main Menu [R]");
        goBackFromGameRoundButton.setId("Return to Main Menu, Main Scene");

        // add grid panels
        GridPane normalEndLayout =  new GridPane();
        normalEndLayout.setPadding(new Insets(10,10,10,10));
        normalEndLayout.setVgap(20);
        normalEndLayout.setHgap(20);

        normalEndLayout.add(goBackFromGameRoundButton, 0, 2);

        // create a new scene
        Scene normalEndScene = new Scene(normalEndLayout, 700, 700);
        scenes.put("Normal Game Mode Game Summary Scene", normalEndScene);
        sceneTitles.put(normalEndScene, "Normal Game Mode Game Summary");

        Label IntrText = new Label("The stats for the normal game mode Game summary screen are displayed as follows:");
        IntrText.setTextAlignment(TextAlignment.CENTER);

        normalEndLayout.add(IntrText, 0, 0);

        Label tRound = new Label(BoggleStats.getInstance().Totalround());
        Label pscoreT = new Label(BoggleStats.getInstance().pScoreTotal());
        Label cscoreT = new Label(BoggleStats.getInstance().cScoreTotal());
        Label pAverage = new Label(BoggleStats.getInstance().pAverageWords());
        Label cAverage = new Label(BoggleStats.getInstance().cAverageWords());


        normalEndLayout.add(tRound, 2, 4);
        normalEndLayout.add(pscoreT, 2, 5);
        normalEndLayout.add(cscoreT, 2, 6);
        normalEndLayout.add(pAverage, 2, 8);
        normalEndLayout.add(cAverage, 2, 9);


        // -----------------------------------------------------------------------------------------------------------//


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
        fourByFourButton.setId("Four By Four Button, Normal Gamemode Scene, Grid Size, four, start");

        Button fiveByFiveButton = new Button("5x5 [2]");
        fiveByFiveButton.setId("Five By Five Button, Normal Gamemode Scene, Grid Size, five, start");

        Button sixBySixButton = new Button("6x6 [3]");
        sixBySixButton.setId("Four By Four Button, Normal Gamemode Scene, Grid Size, six, start");

        Button goBackFromGridSelectionButton = new Button("Return to Main Menu [R]");
        goBackFromGridSelectionButton.setId("Return to Main Menu, Main Scene");
        gridSelectionLayout.add(goBackFromGridSelectionButton, 5, 5);
        gridSelectionLayout.add(fourByFourButton, 5, 2);
        gridSelectionLayout.add(fiveByFiveButton, 5, 3);
        gridSelectionLayout.add(sixBySixButton, 5, 4);

        // Send all button clicks to commandCenter for these commands to be handled
        this.commandCenter = CommandCenter.getInstance(this);
        howToPlayButton.setOnAction(commandCenter);
        statsButton.setOnAction(commandCenter);
        normalModeButton.setOnAction(commandCenter);
        timedModeButton.setOnAction(commandCenter);
        goBackFromNormalGamemode.setOnAction(commandCenter);
        goBackFromHTPButton.setOnAction(commandCenter);
        goBackFromGameRoundButton.setOnAction(commandCenter);
        goBackFromStatsButton.setOnAction(commandCenter);
        goBackFromGridSelectionButton.setOnAction(commandCenter);
        fourByFourButton.setOnAction(commandCenter);
        goToGameSummary.setOnAction(commandCenter);
        fiveByFiveButton.setOnAction(commandCenter);
        sixBySixButton.setOnAction(commandCenter);
        goBackFromGameRoundSummaryButton.setOnAction(commandCenter);
        endRound.setOnAction(commandCenter);


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

        normalSummary.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode c = keyEvent.getCode();
                if (c == KeyCode.G) {
                    goToGameSummary.fire();
                }
                if (keyEvent.getCode() == KeyCode.R) {
                    goBackFromGameRoundSummaryButton.fire();
                }
            }
        });

        normalEndScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode c = keyEvent.getCode();
                if (keyEvent.getCode() == KeyCode.R) {
                    goBackFromGameRoundSummaryButton.fire();
                }
            }
        });

        gridSelectionScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                char c = keyEvent.getText().charAt(0);
                if (keyEvent.getCode() == KeyCode.R) {
                    goBackFromGridSelectionButton.fire();
                }
                else if (c == '1') {
                    fourByFourButton.fire();
                }
                else if (c == '2') {
                    fiveByFiveButton.fire();
                }
                else if (c == '3') {
                    sixBySixButton.fire();
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
        primaryStage.setScene(mainScene);
        primaryStage.show();

//
    }
}
