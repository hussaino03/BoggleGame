package command;

import boggle.BoggleStats;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class DisplayRoundStatsCommand implements Command{
    HashMap<String, Object> statsMap;
    public Stage stage;

    public DisplayRoundStatsCommand(HashMap<String, Object> map, Stage s) {
        this.statsMap =  map;
        this.stage = s;
    }
    @Override
    public void execute() {
        System.out.println("Elements displayed");
        GridPane normalSummaryLayout = (GridPane) stage.getScene().getRoot(); // get the layout of
        // the current scene to be updated
        Thread t = new Thread(()->{
            Platform.runLater(()->{
                normalSummaryLayout.getChildren().removeIf( node -> GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) == 11);
                Label pscore = new Label(BoggleStats.getInstance().PScore());
                Label cscore = new Label(BoggleStats.getInstance().CScore());
                Label csize = new Label(BoggleStats.getInstance().computerwordsSize());
                Label psize = new Label(BoggleStats.getInstance().playerwordsSize());
                Label cwords = new Label(BoggleStats.getInstance().computerwords());
                Label pwords = new Label(BoggleStats.getInstance().playerwords());
                Label map = new Label(statsMap.toString());

                normalSummaryLayout.getChildren().remove(map);

                normalSummaryLayout.add(map, 0, 11);
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
            });
        });
        t.start();
    }
}
