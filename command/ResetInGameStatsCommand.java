package command;

import boggle.BoggleStats;

/**
 * This command resets the stats for a given round before moving onto the next round/game.
 */

public class ResetInGameStatsCommand implements Command{

    /**
     * ResetInGameStatsCommand Constructor.
     */
    public ResetInGameStatsCommand() {}
    /**
     * This method resets the stats that should not persist between rounds or games.
     */
    @Override
    public void execute() {
        BoggleStats.getInstance().playerWords.clear();
        BoggleStats.getInstance().computerWords.clear();
        BoggleStats.getInstance().pScore = 0;
        BoggleStats.getInstance().cScore = 0;
        BoggleStats.getInstance().round = 0;
    }
}
