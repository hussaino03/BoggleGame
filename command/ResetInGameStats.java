package command;

import boggle.BoggleStats;

public class ResetInGameStats implements Command{
    @Override
    public void execute() {
        BoggleStats.getInstance().playerWords.clear();
        BoggleStats.getInstance().computerWords.clear();
        BoggleStats.getInstance().pScore = 0;
        BoggleStats.getInstance().cScore = 0;
        BoggleStats.getInstance().round = 0;
    }
}
