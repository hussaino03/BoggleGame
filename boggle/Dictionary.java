package boggle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;

/**
 * The Dictionary contains a list of words that are acceptable for Boggle
 */
public class Dictionary {

    /**
     * set of legal words for Boggle
     */
    private TreeSet<String> legalWords;

    /**
     * Dictionary constructor
     * @param filename the file containing a list of legal words.
     */
    public Dictionary(String filename) {
        String line = "";
        int wordcount = 0;
        this.legalWords = new TreeSet<String>();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null)
            {
                if (line.strip().length() > 0) {
                    legalWords.add(line.strip());
                    wordcount++;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Checks to see if a provided word is in the dictionary.
     * @param word  The word to check
     * @return A boolean indicating if the word has been found
     */
    public boolean containsWord(String word) {
        return legalWords.contains(word.toLowerCase());
    }

    /**
     * Checks to see if a provided string is a prefix of any word in the dictionary.
     *
     * @param str  The string to check
     * @return  A boolean indicating if the string has been found as a prefix
     */
    public boolean isPrefix(String str) {
        String store = legalWords.ceiling(str.toLowerCase());
        if (store == null){
            return false;
        }
        return store.indexOf(str.toLowerCase()) == 0;
    }
}
