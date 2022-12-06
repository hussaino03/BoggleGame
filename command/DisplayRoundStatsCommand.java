package command;

import boggle.BoggleStats;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.HashMap;
import java.util.HashSet;
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
        GridPane normalSummaryLayout = (GridPane) stage.getScene().getRoot(); // get the layout of
        // the current scene to be updated
        Thread t = new Thread(()->{
            Platform.runLater(()->{

                for (int i = 5; i < 12; i++) {
                    int row = i;
                    normalSummaryLayout.getChildren().removeIf(
                            node -> GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) == row);
                }

                /*
                   scoreMap.put("Player Words", playerWords);
                    scoreMap.put("Computer Words", computerWords);
                    scoreMap.put("Player Score", pScore);
                    scoreMap.put("Computer Score", cScore);
                    scoreMap.put("Player Average Words", pAverageWords);
                    scoreMap.put("Computer Average Words", cAverageWords);
                    scoreMap.put("Player Score Total", pScoreTotal);
                    scoreMap.put("Computer Score Total", cScoreTotal);
                    scoreMap.put("Round", round);
                 */

                int cNumWords = ((HashSet) BoggleStats.getInstance().getStatsMap().get("Computer Words")).size();
                HashSet cWords = (HashSet) statsMap.get("Computer Words");


                Label pscore = new Label("Player Score: " + BoggleStats.getInstance().pScore);
                Label cscore = new Label("Computer Score: " + BoggleStats.getInstance().cScore);
                Label csize = new Label("Number of Words Found By Computer: " + cNumWords);
                Label psize = new Label("Number of Words Found By Player: " + BoggleStats.getInstance().playerWords.size());
                Label pwords = new Label("Words Found By Player: " + BoggleStats.getInstance().playerWords);
                Label cwords = new Label("Words Found By Computer: " + cWords);

                cwords.setWrapText(true);
                cwords.setMaxWidth(350);

                normalSummaryLayout.add(pscore, 0, 5);
                normalSummaryLayout.add(cscore, 0, 6);
                normalSummaryLayout.add(csize, 0, 7);
                normalSummaryLayout.add(psize, 0, 8);
                normalSummaryLayout.add(pwords, 0, 9);
                normalSummaryLayout.add(cwords, 0, 10);

                GridPane.setHalignment(pscore, HPos.CENTER);
                GridPane.setHalignment(cscore, HPos.CENTER);
                GridPane.setHalignment(csize, HPos.CENTER);
                GridPane.setHalignment(psize, HPos.CENTER);
                GridPane.setHalignment(cwords, HPos.CENTER);
                GridPane.setHalignment(pwords, HPos.CENTER);
            });
        });
        t.start();

        /*
        Clear the stats after the round AFTER they are displayed so that they are not displayed as default values.
         */

    }
}
