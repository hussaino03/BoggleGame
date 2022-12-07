package command;

import boggle.BoggleGrid;
import boggle.BoggleStats;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Given a stage and a string of letters, update the current scene to display
 * the letters in a grid
 */
public class DisplayGridElementsCommand implements Command {
    /**
     * the letters to be displayed on the Boggle board in the GUI
     */
    public String letters;
    /**
     * the width of the Boggle board to be displayed in the GUI
     */
    public int gridWidth;
    /**
     * the stage on which to display the Boggle board
     */
    public Stage stage;

    /**
     * DisplayGridElementsCommand Constructor
     * @param l the letters to be displayed on the Boggle board in the GUI
     * @param s the stage on which to display the Boggle board
     */
    public DisplayGridElementsCommand(String l, Stage s) {
        this.letters = l;
        this.gridWidth = (int) Math.sqrt(l.length());
        this.stage = s;
    }

    /**
     * Generates a grid of buttons containing the characters in this.letters and displays
     * them on the screen.
     */
    @Override
    public void execute() {
        BorderPane mainLayout = (BorderPane) stage.getScene().getRoot(); // get the layout of
        // the current scene to be updated
        GridPane boardLayout = new GridPane(); // create a layout for the board
        boardLayout.setHgap(20);
        boardLayout.setVgap(20);

        Thread t = new Thread(()->{
            Platform.runLater(()->{
                for (int i = 0; i < gridWidth; i++) {
                    for (int j=0; j < gridWidth; j++) {
                        Button b = new Button(Character.toString(letters.charAt(i*gridWidth + j)));
                        b.setPrefSize(50, 50);
                        boardLayout.add(b, j, i);
                    }
                }
                boardLayout.setAlignment(Pos.CENTER); // Center the boggle board
               mainLayout.setCenter(boardLayout);
               mainLayout.getBottom().requestFocus();
            });
        });
        t.start();
    }
}
