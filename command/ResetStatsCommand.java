package command;

import java.io.File;

/**
 * This command resets the stats stored across program instances
 */
public class ResetStatsCommand implements Command {
    /**
     * ResetStatsCommand Constructor.
     */
    public ResetStatsCommand() {}

    /**
     * Attempts to delete the file which stores saved stats.
     */
    @Override
    public void execute() {
        File savedStats = new File("boggle/SavedStats.ser");
        if (savedStats.delete()) {
            System.out.println("Stats Reset Successfully");
        }
        else {
            System.out.println("No Saved Stats Could Be Found");
        }
    }
}
