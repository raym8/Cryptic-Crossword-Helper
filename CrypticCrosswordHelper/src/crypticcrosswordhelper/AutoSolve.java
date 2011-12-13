package crypticcrosswordhelper;

import java.util.HashSet;

/**
 * Finds all the words that automatically fit the solution mask
 */
public class AutoSolve {

    // Finds all the words that automaticall fir the solution mask
    public static HashSet autoSolve(String mask) {
        int maskLength = mask.length();
        boolean solution = true;
        
        // HashSet to store auto-solve words
        HashSet autoSolveList = new HashSet();
        
        // For every entry in the Dictionary
        for (int i = 0; i < Main.dict.length; i++) {
            // if entry is same length as solution mask
            if (Main.dict[i].length() == maskLength) {
                // Assume that it is an auto-solve word for now,
                solution = true;
                // For each char in solution length,
                for (int j = 0; j < maskLength; j++) {
                    // If char is not a '?',
                    if (mask.charAt(j) != '\u003f') {
                        // If char is not the same as the char at the same
                        // position in the Dictionary entry, then it is
                        // not an auto-solve word
                        if (mask.charAt(j) != Main.dict[i].charAt(j))
                            solution = false;
                    } // if
                } // for
                // If solution has not been made false,
                // add it to the list of auto-solve words
                if (solution)
                    autoSolveList.add(Main.dict[i]);
            } // if
        } // for

        return autoSolveList;
    } // autoSolve()

} // AutoSolve()
