package crypticcrosswordhelper;

import java.util.HashSet;

/**
 * Searches the clue for any hidden words
 */
public class HiddenWord {
    
    private static HashSet hiddenWords; // HashSet to store hidden words

    // Finds hidden words in clue, according to solution length
    public static HashSet findHiddenWord(String clue, String mask) {
        // Initialiase the hidden words HashSet
        hiddenWords = new HashSet();

        // Move window along clue of solution length
        for (int i=0; i <= (clue.length()-mask.length()); i++) {
            String thisWord = "";
            
            // Get each char in window
            for (int j=0; j < mask.length(); j++)
                thisWord = thisWord + clue.charAt(i+j);
            // For each entry in the Dictionary
            for (int j=0; j < Main.dict.length; j++) {
                // if string in window equals dictionary entry, then 
                // there is a hidden word. Add it to HashSet.
                if (thisWord.equals(Main.dict[j]))
                    hiddenWords.add(thisWord);
            } // for
        } // for

        return hiddenWords;
    } // findHiddenWord

} // HiddenWord()
