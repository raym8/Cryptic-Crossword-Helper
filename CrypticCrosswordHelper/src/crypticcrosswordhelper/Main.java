/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crypticcrosswordhelper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author raym8
 */
public class Main {

    public static String[] dict; // Stores the Dictionary data
    public static ArrayList<String> notUsefulWords; // Stores the NOT useful words
    private static Crossword currentCrossword;

    public static Crossword getCurrentCrossword() {
        return currentCrossword;
    }

    public static void setCurrentCrossword(Crossword newCurrentCrossword) {
        currentCrossword = newCurrentCrossword;
        GUI.updateHistory();
        ProcessClue.setCurrentClue(currentCrossword.getClue(0));
    }

    /**
     * Main method
     */
    public static void main(String[] args) throws IOException {
       dict = loadWordsData(); // Load external Dictionary data file into String array
       notUsefulWords = loadWordFreqData(); // Load external word frequency file to find NOT useful words
       
       Crossword initCrossword = new Crossword("initCrossword");
       currentCrossword = initCrossword;

       GUI.main(args); // Initialise the GUI
    } // main

    public static ArrayList<String> loadWordFreqData() {
        BufferedReader in = null;
        String thisLine;
        String[] thisLineArray;
        ArrayList<String> list = new ArrayList<String>();

        try {
            in = new BufferedReader(new FileReader("wordFreq.txt"));
            while ((thisLine = in.readLine()) != null) {
                thisLine.trim();
                thisLineArray = thisLine.split("\t");

                if (Integer.parseInt(thisLineArray[3]) > 5000)
                    list.add(thisLineArray[0]);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            return list;
        }
    }

    /*
     * Loads the Dictionary data from external text file named "words"
     * into a String array for use in the program
     */
    public static String[] loadWordsData() {
        String thisLine;
        final List<String> s = new ArrayList<String>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("words"));
            while ((thisLine = br.readLine()) != null)
                s.add(thisLine.trim());
        } catch (IOException ioe) {
            System.err.println("Error reading file");
            throw new RuntimeException(ioe);
        } finally {
            if (br!=null) try {
                br.close();
                } catch (IOException e) {
                }
        }
        return s.toArray(new String[s.size()]);
    } // loadWordsData()

} // Main
