package crypticcrosswordhelper;

import java.util.ArrayList;

/**
 * Stores a representation of a Crossword
 */
public class Crossword {

    private String name;            // name of the crossword (must be unique)
    public ArrayList clues;         // array of clues in the crossword
    private int noClues;            // number of clues in the crossword

    // Constructor: Crossword
    public Crossword(String myName) {
        name = myName;
        clues = new <Clue>ArrayList();
        noClues = 0;
    } // Crossword()

    public String getName() {
        return name;
    } // getName()

    public void setName(String newName) {
        name = newName;
    } // setName()

    public int getNoClues() {
        return noClues;
    } // getNoClues()

    // Returns the Clue at a given index
    public Clue getClue(int i) {
        return (Clue)clues.get(i);
    } // getClue()

    // Returns the Clue at a given location
    public Clue getClue(String location) {
        for (int i=0; i<noClues; i++) {
            Clue thisClue = (Clue)clues.get(i);
            if (location.equals(thisClue.getLocation()))
                return thisClue;
        } // for
        return null; // should never happen
    } // getClue()

    // Add a new clue to the crossword
    public boolean addClue(Clue newClue) {
        // Check exisiting clues first to see if there's one already at that location
        for (int i=0; i<noClues; i++) {
            Clue thisClue = (Clue)clues.get(i);
            // If there is already a clue at that location,
            if (newClue.getLocation().equals(thisClue.getLocation())) {
                // Inform user and return false
                System.out.println("There is already a clue at that location."
                            + " Please rename it");
                return false;
            } // if
        } // for

        // Else, the new clue is definetly new, so add it to ArrayList
        boolean done = clues.add(newClue);
        // Increment number of clues
        noClues++;
        // Update history on GUI
        GUI.updateHistory();
        return true;
    } // addClue()

    // Removes a clue from the crossword
    public void removeClue(Clue clue) {
        for (int i=0; i<noClues; i++) {
            Clue thisClue = (Clue)clues.get(i);
            if (clue.getLocation().equals(thisClue.getLocation())) {
                clues.remove(i);
                noClues--;
            } // if
        } // for
    } // removeClue()
} // Crossword
