/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crypticcrosswordhelper;

import java.util.ArrayList;

/**
 * Copyright Mike Ray
 * The Univerity of Manchester
 */
public class Crossword {

    private String name;            // name of the crossword (must be unique)
    public ArrayList clues;        // array of clues in the crossword
    private int noClues;            // number of clues in the crossword

    public Crossword(String myName) {
        name = myName;
        clues = new <Clue>ArrayList();
        noClues = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public int getNoClues() {
        return noClues;
    }

    public Clue getClue(int i) {
        return (Clue)clues.get(i);
    }

    public Clue getClue(String location) {
        for (int i=0; i<noClues; i++) {
            Clue thisClue = (Clue)clues.get(i);
            if (location.equals(thisClue.getLocation()))
                return thisClue;
        }
        return null; // should never happen
    }

    public boolean addClue(Clue newClue) {
        for (int i=0; i<noClues; i++) {
            Clue thisClue = (Clue)clues.get(i);
            if (newClue.getLocation().equals(thisClue.getLocation())) {
                System.out.println("There is already a clue at that location."
                            + " Please rename it");
                return false;
            }
        }

        boolean done = clues.add(newClue);
        noClues++;
        GUI.updateHistory();
        return true;
    }

    public void removeClue(Clue clue) {
        for (int i=0; i<noClues; i++) {
            Clue thisClue = (Clue)clues.get(i);
            if (clue.getLocation().equals(thisClue.getLocation())) {
                clues.remove(i);
                noClues--;
            }
        }
    }
}
