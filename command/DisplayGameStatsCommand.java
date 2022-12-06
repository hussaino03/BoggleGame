package command;

import boggle.BoggleStats;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.HashMap;

public class DisplayGameStatsCommand implements Command{
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
        GridPane mainLayout = (GridPane) stage.getScene().getRoot(); // get the layout of
        // the current scene to be updated
        BoggleStats stats = BoggleStats.getInstance(); // get the instance of BoggleStats
        HBox statsLayout = new HBox(); // create a layout for the game stats
        Thread t = new Thread(()->{
            Platform.runLater(()->{
                statsLayout.getChildren().add(new Label("Player Score: "+ stats.getPScore() + " | "+ "Round Number: " + (stats.getRound() + 1)));
                statsLayout.setAlignment(Pos.CENTER); // Center the game stats
                mainLayout.add(statsLayout, 0, 0);
            });
        });
        t.start();
    }
}
