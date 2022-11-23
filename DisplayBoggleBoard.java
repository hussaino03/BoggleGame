import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;

import javax.swing.*;

public class DisplayBoggleBoard {
    JFrame frame = new JFrame("Boggle Game");


    // Constructor (Skeleton: Add more attributes in Week 2)
    public DisplayBoggleBoard(){
    }

    // run method for GUI
    public void run(){

        // GUI logistics
        frame.setSize(600,600);

        // Set the frame layout to be a grid
        frame.setLayout(new GridLayout(4, 2, 1, 1));

        // Add buttons to display
        frame.add(new JButton("1"));
        frame.add(new JButton("2"));
        frame.add(new JButton("3"));
        frame.add(new JButton("4"));
        frame.add(new JButton("5"));
        frame.add(new JButton("6"));
        frame.add(new JButton("7"));
        frame.add(new JButton("8"));
        frame.add(new JButton("9"));



        // Display the frame
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TextInput();
    }

    // Method for userInput (Textbox)
    public void TextInput(){
        JTextField t1;
        t1=new JTextField("Please Enter Your Input!");
        t1.setBounds(100,100, 600,30);
        frame.add(t1);

    }
    public static void main(String[] args){
        DisplayBoggleBoard b = new DisplayBoggleBoard();
        b.run();
    }
}
