import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Button;
import org.junit.jupiter.api.Test;
import javafx.stage.Stage;
import javafx.concurrent.Task;

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
    void initialScoreValues() {
        gameWindow gameWindow = new gameWindow();
        assertEquals(0, gameWindow.getRoundScore());
        assertEquals(0, gameWindow.getCompScore());
        assertEquals(0, gameWindow.getTotalScore());
        assertEquals(0, gameWindow.getRoundNumber());
    }
}
