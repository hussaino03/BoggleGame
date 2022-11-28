import boggle.BoggleStats;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
    BoggleStats stat = new BoggleStats();
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
        // Grid Selection Scene (change it to this)
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

        // Start Normal Game Mode Round Summary Screen
        // -----------------------------------------------------------------------------------------------------------//
        Button goBackFromGameRoundSummaryButton = new Button("Return to Main Menu [R]");
        goBackFromGameRoundSummaryButton.setId("Return to Main Menu, Main Scene");

        // add grid panels
        GridPane normalSummaryLayout =  new GridPane();
        normalSummaryLayout.setPadding(new Insets(10,10,10,10));
        normalSummaryLayout.setVgap(20);
        normalSummaryLayout.setHgap(20);
        normalSummaryLayout.add(goBackFromGameRoundSummaryButton, 0, 2);

        // create a new scene
        Scene normalSummary = new Scene(normalSummaryLayout, 700, 700);
        scenes.put("Normal Game Mode Round Summary Scene", normalSummary);
        sceneTitles.put(normalSummary, "Normal Game Mode Round Summary");

        Label IntroText = new Label("The stats for the normal game mode round summary screen are displayed as follows:");
        IntroText.setTextAlignment(TextAlignment.CENTER);

        normalSummaryLayout.add(IntroText, 0, 0);

        Label pscore = new Label(stat.PScore());
        Label cscore = new Label(stat.CScore());
        Label csize = new Label(stat.computerwordsSize());
        Label psize = new Label(stat.playerwordsSize());
        Label cwords = new Label(stat.computerwords());
        Label pwords = new Label(stat.playerwords());


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

        Label tRound = new Label(stat.Totalround());
        Label pscoreT = new Label(stat.pScoreTotal());
        Label cscoreT = new Label(stat.cScoreTotal());
        Label pAverage = new Label(stat.pAverageWords());
        Label cAverage = new Label(stat.cAverageWords());


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
        fourByFourButton.setId("Four By Four Button, Main Scene, four");

        Button fiveByFiveButton = new Button("5x5 [2]");
        fiveByFiveButton.setId("Five By Five Button, Main Scene, five");

        Button sixBysixButton = new Button("6x6 [3]");
        sixBysixButton.setId("Four By Four Button, Main Scene, six");

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
        goBackFromGameRoundButton.setOnAction(commandCenter);
        goBackFromStatsButton.setOnAction(commandCenter);
        goBackFromGridSelectionButton.setOnAction(commandCenter);
        fourByFourButton.setOnAction(commandCenter);
        fiveByFiveButton.setOnAction(commandCenter);
        sixBysixButton.setOnAction(commandCenter);
        goBackFromGameRoundSummaryButton.setOnAction(commandCenter);

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
