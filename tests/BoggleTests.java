package tests;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

import boggle.*;
import boggle.Dictionary;
import command.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.gameWindow;

import static org.junit.jupiter.api.Assertions.*;

public class BoggleTests {

    //BoggleGame  Test
    @Test
    void findAllWords_small() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BoggleGame game = new BoggleGame(new gameWindow());
        Method method = game.getClass().getDeclaredMethod("findAllWords", Map.class, Dictionary.class, BoggleGrid.class);
        method.setAccessible(true);

        Dictionary boggleDict = new Dictionary("wordlist.txt");
        Map<String, ArrayList<Position>> allWords = new HashMap<>();
        BoggleGrid grid = new BoggleGrid(4);
        grid.initalizeBoard("RHLDNHTGIPHSNMJO");
        Object r = method.invoke(game, allWords, boggleDict, grid);

        Set<String> expected = new HashSet<>(Arrays.asList("GHOST", "HOST", "THIN"));
        assertEquals(expected, allWords.keySet());
    }
    //Dictionary Test
    @Test
    void containsWord() {
        Dictionary dict = new Dictionary("./wordlist.txt");
        assertTrue(dict.containsWord("ENZYME"));
        assertTrue(dict.isPrefix("PeNch"));
        assertTrue(dict.isPrefix(""));
        assertFalse(dict.isPrefix("kesa"));
        assertTrue(dict.isPrefix("ly"));
        assertTrue(dict.isPrefix("salaam"));
        assertTrue(dict.isPrefix(""));
    }

    //BoggleGrid Test
    @Test
    void setupBoard() {
        BoggleGrid grid = new BoggleGrid(10);
        String letters = "";
        for (int i = 0; i < 10; i++) {
            letters = letters + "0123456789";
        }

        grid.initalizeBoard(letters);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertEquals(letters.charAt(i * 10 + j), grid.getCharAt(i, j));
            }
        }
    }

    @Test
    void setupBoard2() {
        BoggleGrid grid = new BoggleGrid(7);
        String letters = "";
        for (int i = 0; i < 7; i++) {
            letters = letters + "abcdefg";
        }

        grid.initalizeBoard(letters);

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                assertEquals(letters.charAt(i * 7 + j), grid.getCharAt(i, j));
            }
        }
    }

    //BoggleStats Test
    @Test
    void endRoundTest() {
        BoggleStats stats = BoggleStats.getInstance();
        stats.endRound();
        stats.endRound();
        stats.endRound();
        assertEquals(3, stats.getRound());
    }

    @Test
    void addWord1() {
        BoggleStats stats = BoggleStats.getInstance();
        stats.addWord("12345", BoggleStats.Player.Human);
        assertEquals(2, stats.getPScore());
    }

    @Test
    void readyTrue() { // BoggleGame.ready() should be true if all information is received
        BoggleGame game = new BoggleGame(new gameWindow());
        game.choiceProcessor.put("Grid Size", "four");
        game.choiceProcessor.put("Game Mode", "timed");
        assertTrue(game.ready());
    }

    @Test
    void readyFalse() { // BoggleGame.ready() should be false by default
        BoggleGame game = new BoggleGame(new gameWindow());
        assertFalse(game.ready());
    }

    @Test
    void UpdateUserChoiceExecute() { // Ensure UpdateUserChoiceCommand.execute() works as intended
        BoggleGame game = new BoggleGame(new gameWindow());
        String gridSize = "Grid Size";
        String gameMode = "Game Mode";
        String A = "A";
        String B = "B";
        String C = "C";

        UpdateUserChoiceCommand command = new UpdateUserChoiceCommand(game, gridSize, A);
        command.execute();
        assertEquals(game.choiceProcessor.get("Grid Size"), A);

        command = new UpdateUserChoiceCommand(game, gridSize, B);
        command.execute();
        assertEquals(game.choiceProcessor.get("Grid Size"), B);

        command = new UpdateUserChoiceCommand(game, gameMode, C);
        command.execute();
        assertEquals(game.choiceProcessor.get("Game Mode"), C);

        command = new UpdateUserChoiceCommand(game, gameMode, B);
        command.execute();
        assertEquals(game.choiceProcessor.get("Game Mode"), B);



    }

}
