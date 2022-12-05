package command;

import command.Command;

import java.io.File;

public class ResetStatsCommand implements Command {
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
