import boggle.BoggleGame;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.Test;
import javafx.stage.Stage;
import javafx.concurrent.Task;
import command.*;


import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class GameWindowTests {
    JFXPanel panel = new JFXPanel(); // a GUI element needs to be made before the tests can run
    @Test
    void blankButtonIdTest() {
        Button testButton = new Button();
        testButton.setId("");
        testButton.setOnAction(new CommandCenter(new gameWindow()));
        testButton.fire();
        assertDoesNotThrow(() -> {});
    }

    @Test
    void shortButtonIdTest() {
        Button testButton = new Button();
        testButton.setId(", ");
        testButton.setOnAction(new CommandCenter(new gameWindow()));
        testButton.fire();
        assertDoesNotThrow(() -> {});
    }


    @Test
    void CommandCenterExecute() { // Checks whether command center executes
        // commands in the correct order
        /*
        Initialize a CommandCenter and command attributes to pass in
         */
        CommandCenter center = new CommandCenter(new gameWindow());
        Queue<Command> q = center.commandQueue;
        GridPane layout =  new GridPane();
        Stage stage = new gameWindow().primaryStage;
        Scene scene = new Scene(layout, 100, 100);
        String string = "";
        BoggleGame game = new BoggleGame();

        /*
        Populate the commandQueue with commands in various orders
         */
        q.add(new StartGameCommand(game));
        q.add(new RedirectScreenCommand(stage, scene, string));
        q.add(new UpdateUserChoiceCommand(game, string, string));
        q.add(new RedirectScreenCommand(stage, scene, string));
        q.add(new StartGameCommand(game));
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
    }
}
