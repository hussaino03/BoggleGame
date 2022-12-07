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

    public String letters;
    public int gridWidth;
    public Stage stage;

    public DisplayGridElementsCommand(String l, Stage s) {
        this.letters = l;
        this.gridWidth = (int) Math.sqrt(l.length());
        this.stage = s;
    }
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
