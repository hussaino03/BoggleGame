package command;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Display the most updates boggle score
 */
public class DisplayGameStatsCommand implements Command {

    HashMap<String, Object> statsMap;
    public Stage stage;

    public DisplayGameStatsCommand(HashMap<String, Object> map, Stage s) {
        this.statsMap =  map;
        this.stage = s;
    }
    @Override
    public void execute() {
        System.out.println(Thread.currentThread().getName());
        System.out.println("Elements displayed");
        BorderPane mainLayout = (BorderPane) stage.getScene().getRoot(); // get the layout of
        // the current scene to be updated
        HBox statsLayout = new HBox(); // create a layout for the game stats
        Thread t = new Thread(()->{
            Platform.runLater(()->{
                statsLayout.getChildren().add(new Label(statsMap.toString()));
                statsLayout.setAlignment(Pos.CENTER); // Center the game stats
                mainLayout.setTop(statsLayout);
            });
        });
        t.start();
    }
}
