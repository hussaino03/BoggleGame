import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;


public class MainStartScreen extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Main Menu"); // Stage setup

        // Four different buttons the user can select
        Button howToPlayButton = new Button("How to Play");
        howToPlayButton.setId("How to Play");

        Button statsButton = new Button("Stats");
        statsButton.setId("Stats");

        Button normalModeButton = new Button("Normal Mode");
        normalModeButton.setId("Normal Mode");

        Button timedModeButton = new Button("Timed Mode");
        timedModeButton.setId("Timed Mode");

        // Setup for the main scene and layout [Contains the four buttons]
        GridPane mainLayout =  new GridPane();
        mainLayout.setPadding(new Insets(10,10,10,10));
        mainLayout.setVgap(20);
        mainLayout.setHgap(20);
        Scene mainScene = new Scene(mainLayout, 700, 700);

        // add buttons to main layout
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

        Button goBackButton = new Button("Return to Main Menu");
        goBackButton.setId("Return to Main Menu");
        howToPlayLayout.add(gameInstructions, 0, 0);
        howToPlayLayout.add(goBackButton, 0, 1);

        // Send all button clicks to commandCenter for these commands to be handled
        CommandCenter htpCommandCenter = new CommandCenter(stage, howToPlayScene, "How to Play",
                "");
        CommandCenter mainMenuCommandCenter = new CommandCenter(stage, mainScene, "Main Menu",
                "");
        howToPlayButton.setOnAction(htpCommandCenter);
//        statsButton.setOnAction(commandCenter);
//        normalModeButton.setOnAction(commandCenter);
//        timedModeButton.setOnAction(commandCenter);
        goBackButton.setOnAction(mainMenuCommandCenter);

        // Set the scene to the main scene when first running the game
        stage.setScene(mainScene);
        stage.show();
    }
}
