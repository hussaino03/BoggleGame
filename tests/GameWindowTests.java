package tests;

import boggle.BoggleGame;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import javafx.stage.Stage;
import command.*;
import src.*;


import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class GameWindowTests {
    JFXPanel panel = new JFXPanel(); // a GUI element needs to be made before the tests can run
    @Test
    void blankButtonIdTest() {
        Button testButton = new Button();
        testButton.setId("");
        testButton.setOnAction(CommandCenter.getInstance(new GameWindow()));
        testButton.fire();
        assertDoesNotThrow(() -> {});
    }

    @Test
    void shortButtonIdTest() {
        Button testButton = new Button();
        testButton.setId(", ");
        testButton.setOnAction(CommandCenter.getInstance(new GameWindow()));
        testButton.fire();
        assertDoesNotThrow(() -> {});
    }

    @Test
    void CommandCenterExecute() { // Checks whether command center executes
        // commands in the correct order
        /*
        Initialize a command.CommandCenter and command attributes to pass in
         */
        CommandCenter center = CommandCenter.getInstance(new GameWindow());
        Queue<Command> q = center.commandQueue;
        GridPane layout = new GridPane();
        Stage stage = new GameWindow().primaryStage;
        Scene scene = new Scene(layout, 100, 100);
        String string = "";
        BoggleGame game = new BoggleGame(new GameWindow());

        /*
        Populate the commandQueue with commands in various orders
         */
        q.add(new DisplayGridElementsCommand(string, stage));
        q.add(new StartGameCommand(game));
        q.add(new RedirectScreenCommand(stage, scene, string));
        q.add(new DisplayGridElementsCommand(string, stage));
        q.add(new UpdateUserChoiceCommand(game, string, string));
        q.add(new RedirectScreenCommand(stage, scene, string));
        q.add(new StartGameCommand(game));
        q.add(new DisplayGridElementsCommand(string, stage));
        q.add(new RedirectScreenCommand(stage, scene, string));
        q.add(new UpdateUserChoiceCommand(game, string, string));

        /*
        Check whether the commands have been correctly organized by type
         */
        assertEquals(q.poll().getClass(), RedirectScreenCommand.class);
        assertEquals(q.poll().getClass(), RedirectScreenCommand.class);
        assertEquals(q.poll().getClass(), RedirectScreenCommand.class);
        assertEquals(q.poll().getClass(), UpdateUserChoiceCommand.class);
        assertEquals(q.poll().getClass(), UpdateUserChoiceCommand.class);
        assertEquals(q.poll().getClass(), StartGameCommand.class);
        assertEquals(q.poll().getClass(), StartGameCommand.class);
        assertEquals(q.poll().getClass(), DisplayGridElementsCommand.class);
        assertEquals(q.poll().getClass(), DisplayGridElementsCommand.class);
        assertEquals(q.poll().getClass(), DisplayGridElementsCommand.class);
    }

    @Test
    void initialScoreValues() {
        GameWindow gameWindow = new GameWindow();
        Assertions.assertEquals(0, gameWindow.getRoundScore());
        Assertions.assertEquals(0, gameWindow.getCompScore());
        Assertions.assertEquals(0, gameWindow.getTotalScore());
        Assertions.assertEquals(0, gameWindow.getRoundNumber());
    }
    /*
    Check whether instantiating command center several times always returns the same instance
     */
    @Test
    void comCenterSingleton() {
        CommandCenter com1 = CommandCenter.getInstance(new GameWindow());
        CommandCenter com2 = CommandCenter.getInstance(new GameWindow());
        assertSame(com1, com2);
    }
}
