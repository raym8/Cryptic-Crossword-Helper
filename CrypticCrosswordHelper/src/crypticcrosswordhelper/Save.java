package crypticcrosswordhelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Saves a given crossword to an external file so it can be loaded at another time
 */
public class Save {

    public static void saveCrossword(Crossword crossword, File file) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));

            out.writeObject(crossword.getName());     // crossword name
            out.writeInt(crossword.getNoClues());  // number of clues in crossword
            out.writeObject("\n");

            // handle all clues in crossword
            for (int i=0; i<crossword.getNoClues(); i++) {
                Clue thisClue = crossword.getClue(i);
                out.writeObject(thisClue.getOrigClue()); // original clue
                out.writeObject(thisClue.getCleanClue());// clue without spaces/punctuation
                out.writeInt(thisClue.getNoWords());  // number of words in clue

                // handle all words in the clue
                for (int j=0; j<thisClue.getNoWords(); j++) {
                    Word thisWord = thisClue.getWord(j);
                    out.writeObject(thisWord.getWord()); // the word
                    out.writeObject(thisWord.getSynonyms());    // synonyms for the word
                    out.writeObject(thisWord.getDefinitions()); // definitions for the word
                } // for

                out.writeObject(thisClue.getMask());             // solution mask
                out.writeInt(thisClue.getMaskLength());       // solution mask length
                out.writeObject(thisClue.getAutoSolveWords());   // words that will fit solution mask
                out.writeObject(thisClue.getHiddenWords());      // any hidden words in clue
                out.writeObject(thisClue.getLocation());         // location of clue in crossword
                out.writeObject("\n");
            } // for

            out.close();
        } // try
        catch (IOException e) {
        } // catch   
    } // saveCrossword()

} // Save()