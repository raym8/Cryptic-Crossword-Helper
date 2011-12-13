package crypticcrosswordhelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Stores a representation of a Clue
 */
public class Clue {

    private String origClue;        // clue as it was typed in
    private String cleanClue;       // clue with punctuation and spaces removed
    private int noWords;            // number of words in the clue
    public ArrayList words;         // array of words in the clue
    private String mask;            // solution mask as it was typed in
    private int maskLength;         // length of the solution mask
    private HashSet autoSolveWords; // words that will fit into solution mask
    private HashSet hiddenWords;    // words that are hidden in the clue
    private String location;        // location of the clue in a crossword (optional)

    // Constructor: Clue
    public Clue(String myClue, String myMask, String myLocation) {
        origClue = myClue;
        cleanClue = myClue.replaceAll("\\W", ""); // Remove all punctuation and spaces from clue
        String[] myWords = myClue.split(" ");
        words = new <Word>ArrayList();
        for (int i=0; i<myWords.length; i++) // For each word, add a new Word to ArrayList
            words.add(new Word(myWords[i]));
        noWords = words.size();
        mask = myMask;
        maskLength = myMask.length();
        autoSolveWords = new HashSet();
        autoSolveWords = AutoSolve.autoSolve(myMask); // Get auto-solve words
        hiddenWords = new HashSet();
        hiddenWords = HiddenWord.findHiddenWord(cleanClue, myMask); // Find any hidden words
        location = myLocation;
    } // Clue

    public String getOrigClue() {
        return origClue;
    } // getOrigClue()

    public void setOrigClue(String newOrigClue) {
        origClue = newOrigClue;
    } // setOrigClue()

    public String getCleanClue() {
        return cleanClue;
    } // getCleanClue()

    public void setCleanClue(String newCleanClue) {
        cleanClue = newCleanClue;
    } // setCleanClue()

    public int getNoWords() {
        return noWords;
    } // getNoWords()

    public void setNoWords(int newNoWords) {
        noWords = newNoWords;
    } // setNoWords()

    public Word getWord(int i) {
        return (Word) words.get(i);
    } // getWord()

    public void setWord(Word newWord) {
        boolean add = words.add(newWord);
    } // setWord()

    public String getMask() {
        return mask;
    } // getMask()

    public void setMask(String newMask) {
        mask = newMask;
    } // setMask()

    public int getMaskLength() {
        return maskLength;
    } // getMaskLength()

    public void setMaskLength(int newMaskLength) {
        maskLength = newMaskLength;
    } // setMaskLength()

    public HashSet getAutoSolveWords() {
        return autoSolveWords;
    } // getAutoSolveWords()

    public void setAutoSolveWords(HashSet newAutoSolveWords) {
        autoSolveWords = newAutoSolveWords;
    } // setAutoSolveWords()

    public HashSet getHiddenWords() {
        return hiddenWords;
    } // getHiddenWords()

    public void setHiddenWords(HashSet newHiddenWords) {
        hiddenWords = newHiddenWords;
    } // setHiddenWords()

    public String getLocation() {
        return location;
    } // getLocation()

    public void setLocation(String newLocation) {
        location = newLocation;
    } // setLocation()

    // Print all clue details (for de-bugging only)
    public void printClue() {
        System.out.println("CLUE:");
        System.out.println("origClue: " + origClue);
        System.out.println("cleanClue: " + cleanClue);
        System.out.println("noWords: " + noWords);
        for (int i=0; i<words.size(); i++) {
            Word thisWord = (Word)words.get(i);
            thisWord.printWord();
        } // for
        System.out.println("mask: " + mask);
        System.out.println("maskLength: " + maskLength);
        System.out.print("autoSolveWords: ");
        Iterator iterator;
        if (!autoSolveWords.isEmpty())
            System.out.print(autoSolveWords.size() + " words");
        else
            System.out.print("NONE");
        System.out.print("\nhiddenWords: ");
        if (!hiddenWords.isEmpty()) {
            iterator = hiddenWords.iterator();
            while (iterator.hasNext())
                System.out.print(iterator.next() + ", ");
        } // if
        else
            System.out.print("NONE");
        System.out.println("\nlocation: " + location);
    } // printClue()

} // Clue()