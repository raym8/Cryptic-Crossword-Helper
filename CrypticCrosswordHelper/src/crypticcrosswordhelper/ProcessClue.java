/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crypticcrosswordhelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Copyright Mike Ray
 * The Univerity of Manchester
 */
public class ProcessClue {

    private static Clue currentClue;
    private static String entry;

    public static Clue getCurrentClue() {
        return currentClue;
    }

    public static void setCurrentClue(Clue newClue) {
        currentClue = newClue;
        GUI.setCurrentClue(currentClue);
        GUI.clearCommentary();
        GUI.addToCommentary("Clue: " + currentClue.getOrigClue());
        GUI.addToCommentary("Mask: " + currentClue.getMask());
        GUI.addToCommentary("Location: " + currentClue.getLocation());
        GUI.setupSubTasks(currentClue);
    }

    public static void initClue(Clue newClue) throws IOException {
        solutions = new HashSet();
        if (Main.getCurrentCrossword().addClue(newClue))
            setCurrentClue(newClue);

        int noSynonyms = 0;
        int noDefinitions = 0;
        for (int i=0; i<currentClue.getNoWords(); i++) {
            noSynonyms += currentClue.getWord(i).getSynonyms().size();
            noDefinitions += currentClue.getWord(i).getDefinitions().size();
            testForSolution(currentClue.getWord(i).getSynonyms(), currentClue.getMask());
        }
        GUI.addToCommentary("**********\nFound " + noSynonyms + " synonyms for clue");
        GUI.addToCommentary("**********\nFound " + noDefinitions + " definitions for clue");

        ArrayList anagrams = Anagram.doAnagram(currentClue.getCleanClue(), currentClue.getMaskLength());
        if (anagrams != null) {
            GUI.addToCommentary("**********\nAnagrams of solution length:");
            Iterator iterator = anagrams.iterator();
            while (iterator.hasNext())
                GUI.addToCommentary(iterator.next().toString() + ", ");
            GUI.addToCommentary("");
        }

        GUI.addToCommentary("**********\nHidden words in clue:");
        GUI.addToCommentary(newClue.getHiddenWords().toString());

        GUI.addToCommentary("**********\nWords that fit solution mask:");
        GUI.addToCommentary(newClue.getAutoSolveWords().size() + " words");
        
        HashSet usefulWords = WikipediaParser.getUsefulWords(newClue.getOrigClue());
        GUI.addToCommentary("**********\nUseful words to look up with Wikipedia:");
        GUI.addToCommentary(usefulWords.toString());
        Iterator iterator = usefulWords.iterator();
        String next = null;
        while (iterator.hasNext()) {
            next = iterator.next().toString();
            for (int i=0 ;i<newClue.getNoWords(); i++) {
                if (newClue.getWord(i).getWord().equals(next)) {
                    entry = WikipediaParser.getEntry(newClue.getWord(i).getWord());
                    HashSet usefulEntryWords = WikipediaParser.getUsefulWords(entry);
                    testForSolution(usefulEntryWords, newClue.getMask());
                }
            }
        }

        removeDuplicates();
 
        // Print out possible solutions!
        GUI.addToCommentary(("**********\nSOLUTIONS:"));
        if (solutions == null)
            GUI.addToCommentary("No solutions found :(");
        else {
            GUI.addToCommentary(solutions.toString());
        } // else*/

    } // initClue


    private static HashSet solutions;

    private static void testForSolution(HashSet test, String mask) {
        String thisTest = null;

        Iterator iterator;
        iterator = test.iterator();
        while (iterator.hasNext()) {
            thisTest = iterator.next().toString();
            if (thisTest.length() == mask.length()) {
                boolean isSolution = true;
                for (int j=0; j < mask.length(); j++) {
                    if (mask.charAt(j) != '\u003f') {
                        if (mask.charAt(j) != thisTest.charAt(j))
                            isSolution = false;
                    } // if
                } // for
                if (isSolution)
                    solutions.add(thisTest);
            } // if
        } // while

    } // testForSolution

    /*
     * Method to remove duplicate entries from the solution ArrayList.
     * Does this by copying entries to a HashSet, and then back to the ArrayList.
     */
    private static void removeDuplicates() {
        HashSet hs = new HashSet(); // new HashSet
        hs.addAll(solutions);       // copy enries to HashSet
        solutions.clear();          // empty solutions ArrayList
        solutions.addAll(hs);       // copy HashSet to ArrayList
    } // removeDuplicates

} // ProcessClue