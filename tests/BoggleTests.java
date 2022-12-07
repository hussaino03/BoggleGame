package tests;

import java.io.*;
import java.util.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import boggle.*;
import boggle.Dictionary;
import command.*;
import org.junit.jupiter.api.Test;
import src.GameWindow;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains the unit tests related to the boggle package
 */
public class BoggleTests {

    /**
     * BoggleTests Constructor
     */
    public BoggleTests(){}

    //BoggleGame  Test
    @Test
    void findAllWords_small() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BoggleGame game = new BoggleGame(new GameWindow());
        Method method = game.getClass().getDeclaredMethod("findAllWords", Map.class, Dictionary.class, BoggleGrid.class);
        method.setAccessible(true);

        Dictionary boggleDict = new Dictionary("wordlist.txt");
        Map<String, ArrayList<Position>> allWords = new HashMap<>();
        BoggleGrid grid = new BoggleGrid(4);
        grid.initializeBoard("RHLDNHTGIPHSNMJO");
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

        grid.initializeBoard(letters);

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

        grid.initializeBoard(letters);

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
        BoggleGame game = new BoggleGame(new GameWindow());
        game.choiceProcessor.put("Grid Size", "four");
        game.choiceProcessor.put("Game Mode", "timed");
        assertTrue(game.gridSizeSelected());
    }

    @Test
    void readyFalse() { // BoggleGame.ready() should be false by default
        BoggleGame game = new BoggleGame(new GameWindow());
        assertFalse(game.gridSizeSelected());
    }

    @Test
    void UpdateUserChoiceExecute() { // Ensure UpdateUserChoiceCommand.execute() works as intended
        BoggleGame game = new BoggleGame(new GameWindow());
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

    // Assert that stats are being properly saved across games
    @Test
    void savedStats() throws IOException, ClassNotFoundException {
        File savedStats = new File("boggle/SavedStats.ser");
        savedStats.delete();
        BoggleStats stats1 = BoggleStats.getInstance();

        stats1.setTotalRounds(100);
        stats1.setPScoreAllTime(100);
        stats1.setPAvgWordsAllTime(100);
        FileOutputStream fileOut = new FileOutputStream("boggle/SavedStats.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(stats1);

        FileInputStream fileIn = new FileInputStream("boggle/SavedStats.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        BoggleStats stats2 = (BoggleStats) in.readObject();
        savedStats.delete();
        assertEquals(stats2.getTotalRounds(), 100);
        assertEquals(stats2.getPScoreAllTime(), 100);
        assertEquals(stats2.getPAvgWordsAllTime(), 100);
    }

}
