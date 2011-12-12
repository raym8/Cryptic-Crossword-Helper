/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crypticcrosswordhelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Copyright Mike Ray
 * The University of Manchester
 */
public class Clue {

    private String origClue;        // clue as it was typed in
    private String cleanClue;       // clue with punctuation and spaces removed
    private int noWords;            // number of words in the clue
    public ArrayList words;           // array of words in the clue
    private String mask;            // solution mask as it was typed in
    private int maskLength;         // length of the solution mask
    private HashSet autoSolveWords; // words that will fit into solution mask
    private HashSet hiddenWords;    // words that are hidden in the clue
    private String location;        // location of the clue in a crossword (optional)

    public Clue(String myClue, String myMask, String myLocation) {
        origClue = myClue;
        cleanClue = myClue.replaceAll("\\W", ""); // Remove all punctuation and spaces from clue
        String[] myWords = myClue.split(" ");
        words = new <Word>ArrayList();
        for (int i=0; i<myWords.length; i++)
            words.add(new Word(myWords[i]));
        noWords = words.size();
        mask = myMask;
        maskLength = myMask.length();
        autoSolveWords = new HashSet();
        autoSolveWords = AutoSolve.autoSolve(myMask);
        hiddenWords = new HashSet();
        hiddenWords = HiddenWord.findHiddenWord(cleanClue, myMask);
        location = myLocation;
    }

    public String getOrigClue() {
        return origClue;
    }

    public void setOrigClue(String newOrigClue) {
        origClue = newOrigClue;
    }

    public String getCleanClue() {
        return cleanClue;
    }

    public void setCleanClue(String newCleanClue) {
        cleanClue = newCleanClue;
    }

    public int getNoWords() {
        return noWords;
    }

    public void setNoWords(int newNoWords) {
        noWords = newNoWords;
    }

    public Word getWord(int i) {
        return (Word) words.get(i);
    }

    public void setWord(Word newWord) {
        boolean add = words.add(newWord);
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String newMask) {
        mask = newMask;
    }

    public int getMaskLength() {
        return maskLength;
    }

    public void setMaskLength(int newMaskLength) {
        maskLength = newMaskLength;
    }

    public HashSet getAutoSolveWords() {
        return autoSolveWords;
    }

    public void setAutoSolveWords(HashSet newAutoSolveWords) {
        autoSolveWords = newAutoSolveWords;
    }

    public HashSet getHiddenWords() {
        return hiddenWords;
    }

    public void setHiddenWords(HashSet newHiddenWords) {
        hiddenWords = newHiddenWords;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String newLocation) {
        location = newLocation;
    }

    public void printClue() {
        System.out.println("CLUE:");
        System.out.println("origClue: " + origClue);
        System.out.println("cleanClue: " + cleanClue);
        System.out.println("noWords: " + noWords);
        for (int i=0; i<words.size(); i++) {
            Word thisWord = (Word)words.get(i);
            thisWord.printWord();
        }
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
        }
        else
            System.out.print("NONE");
        System.out.println("\nlocation: " + location);
    } // printClue

}
