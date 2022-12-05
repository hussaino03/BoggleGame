package src;

import javafx.collections.FXCollections;
import boggle.*;
import command.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.awt.*;
import java.util.HashMap;
import java.util.Objects;

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

        String css = Objects.requireNonNull(this.getClass().getResource("../styles/styles.css")).toExternalForm(); // Link to CSS file

        this.game = new BoggleGame(this); // Ready the game for the player

        // Four different buttons the user can select
        Button howToPlayButton = new Button("How to Play [A]");
        howToPlayButton.setMinSize(80, 80);
        howToPlayButton.setId("RedirectScreen; How To Play Scene");

        Button statsButton = new Button("Stats [S]");
        statsButton.setMinSize(80, 80);
        statsButton.setId("RedirectScreen; Stats Scene");

        Button normalModeButton = new Button("Play [Q]");
        normalModeButton.setMinSize(80, 80);
        normalModeButton.setId("RedirectScreen, UpdateUserChoice; Grid Selection Scene; Game Mode, normal");


        // Setup for the main scene and layout [Contains the four buttons]
        GridPane mainLayout =  new GridPane();
        mainLayout.setPadding(new Insets(10,10,10,10));
        mainLayout.setVgap(20);
        mainLayout.setHgap(20);
        Scene mainScene = new Scene(mainLayout, 600, 600);
        scenes.put("Main Scene", mainScene);
        sceneTitles.put(mainScene, "Main Menu");
        mainScene.getStylesheets().add(css);

        // add buttons to main layout
        // buttonIds are used to encapsulate commands via the following ID format:
        // "ID, Scene to transition to, choice"
        mainLayout.add(howToPlayButton, 0, 0);
        mainLayout.add(normalModeButton, 1, 0);
        mainLayout.add(statsButton, 2, 0);
        mainLayout.setAlignment(Pos.CENTER);

        // Setup for How to Play scene and layout
        GridPane howToPlayLayout =  new GridPane();
        howToPlayLayout.setPadding(new Insets(10,10,10,10));
        howToPlayLayout.setVgap(20);
        howToPlayLayout.setHgap(20);
        Scene howToPlayScene = new Scene(howToPlayLayout, 600, 600);
        scenes.put("How To Play Scene", howToPlayScene);
        sceneTitles.put(howToPlayScene, "How to Play");
        howToPlayScene.getStylesheets().add(css);

        // Add text and button to how to play layout
        Label gameInstructions = new Label("Boggle is a game where you try to find as" +
                " many words as you can in a randomized grid of letters. Words can be" +
                " formed by joining letters which are vertically, horizontally or diagonally" +
                " adjacent.\n \nYou will earn points based on the length of the word found. " +
                "You will play against the computer. You will be given a grid of letters and should" +
                " form as many words as you can. When you can no longer think of any words, the " +
                "computer will find all remaining words. At the end of each round, you will be given" +
                " information regarding your current standing against the computer. \n\nYour goal is to" +
                " end the game with a higher score than the computer. You can return to this page at any " +
                "time to review the " +
                "game instructions. You can also go to the Stats Page to review your success across" +
                " all games.");

        gameInstructions.setWrapText(true);
        gameInstructions.setMaxWidth(500);
        Button goBackFromHTPButton = new Button("Return to Main Menu [R]");
        goBackFromHTPButton.setId("RedirectScreen; Main Scene");
        howToPlayLayout.add(gameInstructions, 0, 0);
        howToPlayLayout.add(goBackFromHTPButton, 0, 1);
        
        // start Display in-game stats 
        // -----------------------------------------------------------------------------------------------
        // Normal Gamemode Scene and Layout

        BorderPane normalGamemodeLayout =  new BorderPane();
        normalGamemodeLayout.setPadding(new Insets(10,10,10,10));
        Scene normalGamemodeScene = new Scene(normalGamemodeLayout, 600, 600);
        normalGamemodeScene.getStylesheets().add(css);


        scenes.put("Normal Gamemode Scene", normalGamemodeScene);
        sceneTitles.put(normalGamemodeScene, "Normal Gamemode");
        normalGamemodeScene.getStylesheets().add(css);
        Label normalGamemodeStats = new Label("Player Score: "+ game.gameStats.getPScore() + " | "+ "Round Number: " + (game.gameStats.getRound() + 1));
        Button goBackFromNormalGamemode = new Button("Return to Main Menu [R]");
        goBackFromNormalGamemode.setId("RedirectScreen; Main Scene");
        Button endRound = new Button("End the Round [N]");
        TextField inpWord = new TextField();
        inpWord.setPromptText("Input Here");
        HBox statsBar = new HBox();
        VBox buttonBar = new VBox();
        buttonBar.setAlignment(Pos.BOTTOM_RIGHT);
        statsBar.getChildren().add(normalGamemodeStats);
        buttonBar.getChildren().add(endRound);
        statsBar.setAlignment(Pos.CENTER);
        normalGamemodeLayout.setRight(buttonBar);
        normalGamemodeLayout.setTop(statsBar);
        normalGamemodeLayout.setRight(buttonBar);
        normalGamemodeLayout.setBottom(inpWord);
        //--------------------------------------------------


        endRound.setId("RedirectScreen, UpdateUserChoice; Normal Game Mode Round Summary Scene; Round Ended, true");
        // -------------------------------------------------------------------------------------------------

        // Setup for stats scene and layout

        //set table to non-editable
        this.table = new TableView();
        table.setEditable(false);

        //normal stats column
        TableColumn<String, String> normalStatColumn = new TableColumn<>("Statistic");
        normalStatColumn.setMinWidth(200);

        //normal stats values column
        TableColumn<String, Double> normalValueColumn = new TableColumn<>("Value");
        normalValueColumn.setMinWidth(200);

        //add columns to table
        table.getColumns().addAll(normalStatColumn, normalValueColumn);

        //create stats layout
        BorderPane statsLayout = new BorderPane();
        statsLayout.setCenter(table);
        Button goBackFromStatsButton = new Button("Return to Main Menu [R]");
        goBackFromStatsButton.setId("RedirectScreen; Main Scene");
        Button resetStatsButton = new Button("Reset Stats [X]");
        resetStatsButton.setId("ResetStats; ");
        HBox statsButtons = new HBox();
//        statsButtons.setAlignment(Pos.CENTER);
        statsButtons.getChildren().addAll(goBackFromStatsButton, resetStatsButton);
        statsLayout.setBottom(statsButtons);


        Scene statsScene = new Scene(statsLayout, 600, 600);
        scenes.put("Stats Scene", statsScene);
        sceneTitles.put(statsScene, "Stats");
        statsScene.getStylesheets().add(css);
        //

        // Start Normal Game Mode Round Summary Screen
        // -----------------------------------------------------------------------------------------------------------//
        Button playAgain = new Button("Play Next Round [P]");
        playAgain.setId("RedirectScreen, UpdateUserChoice; Grid Selection Scene; choice, Y");

        Button goToGameSummary = new Button("Go To Game Summary [G]");
        goToGameSummary.setId("RedirectScreen, UpdateUserChoice; Normal Game Mode Game Summary Scene; choice, N");

        // add grid panels
        GridPane normalSummaryLayout =  new GridPane();
        normalSummaryLayout.setPadding(new Insets(10,10,10,10));
        normalSummaryLayout.setVgap(20);
        normalSummaryLayout.setHgap(20);
        normalSummaryLayout.add(goToGameSummary, 1, 0);
        normalSummaryLayout.add(playAgain, 0, 0);

        // create a new scene
        Scene normalSummary = new Scene(normalSummaryLayout, 600, 600);
        scenes.put("Normal Game Mode Round Summary Scene", normalSummary);
        sceneTitles.put(normalSummary, "Normal Game Mode Round Summary");
        normalSummary.getStylesheets().add(css);

        Label IntroText = new Label("The stats for the round are displayed as follows:");
        IntroText.setTextAlignment(TextAlignment.CENTER);
        IntroText.setMaxWidth(580);

        normalSummaryLayout.add(IntroText, 0, 4);

        Label pscore = new Label(game.gameStats.PScore());
        Label cscore = new Label(BoggleStats.getInstance().CScore());
        Label csize = new Label(BoggleStats.getInstance().computerwordsSize());
        Label psize = new Label(BoggleStats.getInstance().playerwordsSize());
        Label cwords = new Label(BoggleStats.getInstance().computerwords());
        Label pwords = new Label(BoggleStats.getInstance().playerwords());

        normalSummaryLayout.add(pscore, 0, 5);
        normalSummaryLayout.add(cscore, 0, 6);
        normalSummaryLayout.add(csize, 0, 7);
        normalSummaryLayout.add(psize, 0, 8);
        normalSummaryLayout.add(cwords, 0, 9);
        normalSummaryLayout.add(pwords, 0, 10);

        GridPane.setHalignment(pscore, HPos.CENTER);
        GridPane.setHalignment(cscore, HPos.CENTER);
        GridPane.setHalignment(csize, HPos.CENTER);
        GridPane.setHalignment(psize, HPos.CENTER);
        GridPane.setHalignment(cwords, HPos.CENTER);
        GridPane.setHalignment(pwords, HPos.CENTER);


        // -----------------------------------------------------------------------------------------------------------//

        // Start Normal Game Mode Game Summary Screen
        // -----------------------------------------------------------------------------------------------------------//

        Button goBackFromGameRoundButton = new Button("Return to Main Menu [R]");
        goBackFromGameRoundButton.setId("RedirectScreen; Main Scene");

        // add grid panels
        GridPane normalEndLayout =  new GridPane();
        normalEndLayout.setPadding(new Insets(10,10,10,10));
        normalEndLayout.setVgap(20);
        normalEndLayout.setHgap(20);

        normalEndLayout.add(goBackFromGameRoundButton, 0, 1);


        // create a new scene
        Scene normalEndScene = new Scene(normalEndLayout, 600, 600);
        scenes.put("Normal Game Mode Game Summary Scene", normalEndScene);
        sceneTitles.put(normalEndScene, "Normal Game Mode Game Summary");
        normalEndScene.getStylesheets().add(css);

        Label IntrText = new Label("The stats for the normal game mode Game summary screen are displayed as follows:");
        IntrText.setTextAlignment(TextAlignment.CENTER);

        normalEndLayout.add(IntrText, 0, 3);

        Label tRound = new Label(BoggleStats.getInstance().Totalround());
        Label pscoreT = new Label(BoggleStats.getInstance().pScoreTotal());
        Label cscoreT = new Label(BoggleStats.getInstance().cScoreTotal());
        Label pAverage = new Label(BoggleStats.getInstance().pAverageWords());
        Label cAverage = new Label(BoggleStats.getInstance().cAverageWords());


        normalEndLayout.add(tRound, 0, 5);
        normalEndLayout.add(pscoreT, 0, 6);
        normalEndLayout.add(cscoreT, 0, 7);
        normalEndLayout.add(pAverage, 0, 8);
        normalEndLayout.add(cAverage, 0, 9);

        GridPane.setHalignment(tRound, HPos.CENTER);
        GridPane.setHalignment(pscoreT, HPos.CENTER);
        GridPane.setHalignment(cscoreT, HPos.CENTER);
        GridPane.setHalignment(pAverage, HPos.CENTER);
        GridPane.setHalignment(cAverage, HPos.CENTER);


        // -----------------------------------------------------------------------------------------------------------//



        // Setup for grid selection scene and layout

        GridPane gridSelectionLayout =  new GridPane();
        gridSelectionLayout.setPadding(new Insets(10,10,10,10));
        gridSelectionLayout.setVgap(20);
        gridSelectionLayout.setHgap(20);
        Scene gridSelectionScene = new Scene(gridSelectionLayout, 600, 600);
        scenes.put("Grid Selection Scene", gridSelectionScene);
        sceneTitles.put(gridSelectionScene, "Grid Selection");
        gridSelectionScene.getStylesheets().add(css);

        // Label to explain how to choose grid size
        Label gridInstructions = new Label("Enter 1 to play on a (4x4) grid; 2 to play on a (5x5) grid;" +
                " 3 to play on a (6x6) grid:");
        gridSelectionLayout.add(gridInstructions,0,2);

        //textbox to input size
//        TextField inpGrid = new TextField("Input here");
//        gridSelectionLayout.add(inpGrid,1,1);

        // add buttons to grid selection layout
        Button fourByFourButton = new Button("4x4 [1]");
        fourByFourButton.setId("RedirectScreen, UpdateUserChoice, StartGame; Normal Gamemode Scene; Grid Size, four; ");

        Button fiveByFiveButton = new Button("5x5 [2]");
        fiveByFiveButton.setId("RedirectScreen, UpdateUserChoice, StartGame; Normal Gamemode Scene; Grid Size, five; ");

        Button sixBySixButton = new Button("6x6 [3]");
        sixBySixButton.setId("RedirectScreen, UpdateUserChoice, StartGame; Normal Gamemode Scene; Grid Size, six; ");

        Button goBackFromGridSelectionButton = new Button("Return to Main Menu [R]");
        goBackFromGridSelectionButton.setId("RedirectScreen; Main Scene");
        gridSelectionLayout.add(fourByFourButton, 0, 5);
        GridPane.setHalignment(fourByFourButton, HPos.CENTER);
        gridSelectionLayout.add(fiveByFiveButton, 0, 6);
        GridPane.setHalignment(fiveByFiveButton, HPos.CENTER);
        gridSelectionLayout.add(sixBySixButton, 0, 7);
        GridPane.setHalignment(sixBySixButton, HPos.CENTER);
        gridSelectionLayout.add(goBackFromGridSelectionButton, 0, 1);
        GridPane.setHalignment(goBackFromGridSelectionButton, HPos.LEFT);


        // Send all button clicks to commandCenter for these commands to be handled
        this.commandCenter = CommandCenter.getInstance(this);
        howToPlayButton.setOnAction(commandCenter);
        statsButton.setOnAction(commandCenter);
        normalModeButton.setOnAction(commandCenter);
        goBackFromNormalGamemode.setOnAction(commandCenter);
        goBackFromHTPButton.setOnAction(commandCenter
        );
        goBackFromGameRoundButton.setOnAction(commandCenter);
        goBackFromStatsButton.setOnAction(commandCenter);
        goBackFromGridSelectionButton.setOnAction(commandCenter);
        fourByFourButton.setOnAction(commandCenter);
        goToGameSummary.setOnAction(commandCenter);
        fiveByFiveButton.setOnAction(commandCenter);
        sixBySixButton.setOnAction(commandCenter);
        playAgain.setOnAction(commandCenter);
        endRound.setOnAction(commandCenter);
        resetStatsButton.setOnAction(commandCenter);


        // Allow buttons to be fired through keyboard to make the program more accessible
        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode c = keyEvent.getCode();
                if (c == KeyCode.Q) {
                    normalModeButton.fire();
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
                if (keyEvent.getCode() == KeyCode.P) {
                    playAgain.fire();
                }
            }
        });

        normalEndScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode c = keyEvent.getCode();
                if (keyEvent.getCode() == KeyCode.R) {
                    playAgain.fire();
                }
            }
        });

        gridSelectionScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
           public void handle(KeyEvent keyEvent) {
               String cString = keyEvent.getText();
               if (cString.length() == 0) { // If string is empty, do not handle
                   return;
               }
               char c = keyEvent.getText().charAt(0);
               if (keyEvent.getCode() == KeyCode.R) {
                   goBackFromGridSelectionButton.fire();
               } else if (c == '1') {
                   fourByFourButton.fire();
               } else if (c == '2') {
                   fiveByFiveButton.fire();
               } else if (c == '3') {
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
                else if (keyEvent.getCode() == KeyCode.X) {
                    resetStatsButton.fire();
                }
            }
        });

        normalGamemodeScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    String word = inpWord.getText();
                    if (word.length()==0) { // Do not handle empty words
                        return;
                    }
                    String Id = "UpdateUserChoice; Word, " + word;
                    inpWord.setText("");
                    commandCenter.handle(Id);
                }
                if (keyEvent.getCode() == KeyCode.N) {
                    endRound.fire();
                }
            }
        });

        // Set the scene to the main scene when first running the game
        primaryStage.setScene(mainScene);
        primaryStage.show();

//
    }

    //public ObservableList<BoggleStatsTable> getTable(){
        //ObservableList<Object> statsTable = FXCollections.observableArrayList();
        //statsTable.add(new BoggleStatsTable("Average num of words",BoggleStats.))
        //statsTable.add(new BoggleStatsTable("Highest score",BoggleStats.highestScore))

    }


//}
