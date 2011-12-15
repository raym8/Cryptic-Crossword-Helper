package crypticcrosswordhelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Most of the work happens here...
 */
public class ProcessClue {
    private static Clue currentClue;
    private static String entry;

    /*
     * Returns the current clue
     */
    public static Clue getCurrentClue() {
        return currentClue;
    } // ProcessClue()

    /*
     * Sets a new current clue
     */
    public static void setCurrentClue(Clue newClue) {
        currentClue = newClue;
        GUI.setCurrentClue(currentClue);
        GUI.clearCommentary();
        GUI.addToCommentary(currentClue.getCommentary());
        GUI.setupSubTasks(currentClue);
    } // setCurrentClue()

    /*
     * Attempt to solve the current clue
     */
    public static void procClue() {
        GUI.clearCommentary();
        solutions = new HashSet();
        solutionsDup = new ArrayList();
        occurences = new <Solution>ArrayList();

        HashSet defWords = new <String>HashSet();
        int noSynonyms = 0;
        int noDefinitions = 0;
        for (int i=0; i<currentClue.getNoWords(); i++) {
            noSynonyms += currentClue.getWord(i).getSynonyms().size();
            noDefinitions += currentClue.getWord(i).getDefinitions().size();
            Iterator iterator;
            iterator = currentClue.getWord(i).getDefinitions().iterator();
            while (iterator.hasNext()) {
                String thisDef = (String)iterator.next();
                String[] split = thisDef.split(" ");
                defWords.addAll(Arrays.asList(split));
            } // while
            testForSolution(currentClue.getWord(i).getSynonyms(), currentClue.getMask());
        }
        testForSolution(defWords, currentClue.getMask());
        
        currentClue.addToCommentary("Found " + noSynonyms + " synonyms for clue");
        currentClue.addToCommentary("Found " + noDefinitions + " definitions for clue");

        HashSet anagrams = Anagram.doAnagram(currentClue.getCleanClue(), currentClue.getMaskLength());
        if (anagrams != null) {
            testForSolution(anagrams, currentClue.getMask());
            currentClue.addToCommentary("Anagrams of solution length:");
            Iterator iterator = anagrams.iterator();
            String anagramResults = "\t";
            while (iterator.hasNext())
                anagramResults += iterator.next().toString() + ", ";
            currentClue.addToCommentary(anagramResults);
        } // if

        if (currentClue.getHiddenWords().isEmpty())
            currentClue.addToCommentary("No hidden words found in clue");
        else {
            currentClue.addToCommentary("Hidden words in clue:");
            currentClue.addToCommentary("\t" + currentClue.getHiddenWords().toString());
            testForSolution(currentClue.getHiddenWords(), currentClue.getMask());
        } // else

        currentClue.addToCommentary("Words that fit solution mask:");
        currentClue.addToCommentary("\t" + currentClue.getAutoSolveWords().size() + " words");
        
        HashSet usefulWords = WikipediaParser.getUsefulWords(currentClue.getOrigClue());
        currentClue.addToCommentary("Useful words to look up with Wikipedia:" + usefulWords.toString());
        Iterator iterator = usefulWords.iterator();
        String next = null;
        while (iterator.hasNext()) {
            next = iterator.next().toString();
            for (int i=0 ;i<currentClue.getNoWords(); i++) {
                if (currentClue.getWord(i).getWord().equals(next)) {
                    try {
                        entry = WikipediaParser.getEntry(currentClue.getWord(i).getWord());
                    } catch (IOException ex) {
                        Logger.getLogger(ProcessClue.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    HashSet usefulEntryWords = WikipediaParser.getUsefulWords(entry);
                    testForSolution(usefulEntryWords, currentClue.getMask());
                }
            }
        }

        removeDuplicates();
 
        // Print out possible solutions!
        currentClue.addToCommentary("\nPossible solutions:");
        if (solutions == null)
            currentClue.addToCommentary("No solutions found :(");
        else {
            currentClue.addToCommentary(solutions.toString());
        } // else
        
        findMaxSols();
        
        // Print out MORE possible solutions!
        currentClue.addToCommentary("\nMore likely solutions:");
        if (morePossSolutions == null)
            currentClue.addToCommentary("No more likely solutions found :(");
        else {
            String report = "";
            Iterator i = morePossSolutions.iterator();
            while (i.hasNext()) {
                Solution thisSol = (Solution)i.next();
                report += thisSol.getWord() + ", ";
            } // while
            if (report.equals(""))
                report = "none found";
            currentClue.addToCommentary(report);
        } // else
        
        currentClue.addToCommentary("\nMost probable solution: " + maxOccuringWord.toUpperCase() + ", occuring " + maxOccurenceVal + " times");
                
        GUI.addToCommentary(currentClue.getCommentary());
    } // initClue()
    
    private static int maxOccurenceVal;
    private static String maxOccuringWord;
    
    private static void findMaxSols() {
        maxOccurenceVal = 0;
        maxOccuringWord = null;
        
        Iterator i;
        i = occurences.iterator();
        while (i.hasNext()) {
            Solution thisSol = (Solution)i.next();
            if (thisSol.getOccurences() > maxOccurenceVal) {
                maxOccurenceVal = thisSol.getOccurences();
                maxOccuringWord = thisSol.getWord();
            } // if
        } // while
        
        morePossSolutions = new <Solution>ArrayList();
        
        // If there is at least one solution that occurs more than once, 
        // then create new set of more posisble solutions with these solutions in
        if (maxOccurenceVal > 1) {
            i = occurences.iterator();
            while (i.hasNext()) {
                Solution thisSol = (Solution)i.next();
                if (thisSol.getOccurences() > 1) {
                    morePossSolutions.add(thisSol);
                } // if
                
            } // while
            i = morePossSolutions.iterator();
            while (i.hasNext()) {
                Solution thisSol = (Solution)i.next();
                System.out.println(thisSol.getWord() + " occurs " + thisSol.getOccurences() + " times");
            } // while
        } // if
        
    }
    
    private static HashSet solutions;
    private static ArrayList solutionsDup;
    private static ArrayList<Solution> occurences;
    private static ArrayList<Solution> morePossSolutions;

    private static void testForSolution(HashSet test, String mask) {
        String thisTest = null;

        Iterator iterator;
        iterator = test.iterator();
        while (iterator.hasNext()) {
            thisTest = iterator.next().toString();
            
                
            if (thisTest.length() == mask.length()) {
                boolean isSolution = true;
                // Don't add word to solutions if it contains special characters
                if (!thisTest.matches("[a-zA-Z0-9]*"))
                    isSolution = false;
                // Don't add word to solutions if it is a word in the original clue
                if (currentClue.containsWord(thisTest))
                    isSolution = false;
                for (int j=0; j < mask.length(); j++) {
                    if (mask.charAt(j) != '\u003f') {
                        if (mask.charAt(j) != thisTest.charAt(j))
                            isSolution = false;
                    } // if
                } // for
                if (isSolution) {
                    boolean itemFound = false;
                    Iterator i;
                    i = occurences.iterator();
                    while (i.hasNext()) {
                        Solution thisSol = (Solution)i.next();
                        if (thisSol.isEqual(thisTest)) {
                            thisSol.inc();
                            itemFound = true;
                        } // if
                    } // while
                    if (!itemFound) {
                        occurences.add(new Solution(thisTest));
                    } // if
                    solutions.add(thisTest);
                    solutionsDup.add(thisTest);
                } // if
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