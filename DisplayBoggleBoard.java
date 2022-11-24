import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class DisplayBoggleBoard implements Runnable {

    // run method for the interface
    @Override
    public void run() {

        // GUI Logistics
        JFrame frame = new JFrame("Boggle Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the panels in the respected positions
        frame.add(createGridPanel(), BorderLayout.CENTER);
        frame.add(createTextPanel(), BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    // Method to create the BoggleGrid
    private JPanel createGridPanel() {
        // Use GridLayout to align the buttons (words) in a grid manner
        JPanel panel = new JPanel(new GridLayout(0, 3, 1, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        // Generate a total of 9 buttons to display
        for (int index = 0; index < 9; index++) {
            JButton button = new JButton(Integer.toString(index + 1));
            button.setPreferredSize(new Dimension(96, 96));
            panel.add(button);
        }

        return panel;
    }

    // Create a textbox prompting the user to enter words
    private JPanel createTextPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        JTextField t1 = new JTextField("Please Type in Words!");
        panel.add(t1);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new DisplayBoggleBoard());
    }

}