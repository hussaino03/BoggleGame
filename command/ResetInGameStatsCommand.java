package command;

import boggle.BoggleStats;

/**
 * This command resets the stats for a given round before moving onto the next round or game.
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
        BoggleStats.getInstance().clearWords();
        BoggleStats.getInstance().setPScore(0);
        BoggleStats.getInstance().setCScore(0);
        BoggleStats.getInstance().setRound(0);
    }
}
