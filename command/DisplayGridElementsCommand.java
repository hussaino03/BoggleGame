package command;

import boggle.BoggleGrid;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Given a stage and a string of letters, create a scene displaying the letters in
 * a grid and set the stage to this scene
 */
public class DisplayGridElementsCommand implements Command {

    public String letters;
    public int gridWidth;
    public String title;
    public Stage stage;

    public DisplayGridElementsCommand(String l, int w, String t, Stage s) {
        this.letters = l;
        this.gridWidth = w; // Can easily add gridHeight for non-square grids but
        // this is unnecessary for now
        this.title = t;
        this.stage = s;
    }
    @Override
    public void execute() {
        System.out.println(Thread.currentThread().getName());
        System.out.println("Elements displayed");
        GridPane layout = new GridPane(); // create a grid layout to display the grid

        for (int i = 0; i < gridWidth; i++) {
            for (int j=0; j < gridWidth; j++) {
                Button b = new Button(Character.toString(letters.charAt(i*gridWidth + j)));
                layout.add(b, j, i);
            }
        }

        Scene gridScene = new Scene(layout, 700, 700); // create a scene from this layout
        Thread t = new Thread(()->{
            Platform.runLater(()->{
                stage.setScene(gridScene);
                stage.setTitle(title);
            });
        });
        t.start();
    }
}
