package crypticcrosswordhelper;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Handles all the Anagram functions
 */
public class Anagram {
    
    private static HashSet<String> anagramList; // Stores the list of anagrammed words
    private static char[] clueArray; // String to be anagrammed as an array of chars
    private static String clue; // String to be anagrammed

    // Creates a window of a given size and 'scans' clue trying to find anagrams
    public static HashSet<String> doAnagram(String myClue, int windowSize) {
        // Initialise list of anagrammed words
        anagramList = new HashSet<String>();
        
        // If window size is larger than clue length, we cannot continue
        if (windowSize > myClue.length()) {
            anagramList.add("Select a smaller length size");
            return anagramList;
        } // if

        // Tidy up the given clue, to minimise problems
        clue = myClue;
        clue = clue.replaceAll("\\s+", ""); // remove all spaces from the clue
        clue = clue.toLowerCase(); // make sure clue is all lower case

        // Move window along the clue...
        for (int i=0; i<=(clue.length()-windowSize); i++)
        {
            String myAnagram = "";
            // Get chars within the window at this iteration
            for (int j=0; j<windowSize; j++)
                myAnagram = myAnagram + clue.charAt(i+j);

            // Order these chars for comparison later
            String thisAnagram = orderString(myAnagram);

            // Look for any anagrams for the string in this window
            anagram(thisAnagram);
        } // for

        // If no anagrams found, return null
        if (anagramList.isEmpty())
            return null;

        // Otherwise, return the list of anagrammed words
        return anagramList;
    } // doAnagram()

    // Compares a given string against the Dictionary
    // to see if it is an anagram
    private static void anagram(String thisClue) {
        String possMatch;
        char[] possMatchArray;

        // For all entries in the Dictionary
        for (int i= 0; i < Main.dict.length; i++) {
            // if string and Dictionary entry are the same length,
            if (thisClue.length() == Main.dict[i].length()) {
                // Order chars in Dict entry
                possMatch = orderString(Main.dict[i]);
                // and if it is the same as the string, then it is an anagram
                if (thisClue.equals(possMatch))
                    // Add word to anagramList
                    anagramList.add(Main.dict[i]);
            } // if
        } // for
    } // anagram()
    
    // Searches clue for an anagram indicator word
    // Returns true if there is an anagram indicated,
    // false otherwise
    public static boolean lookForAnagrams(Clue myClue) {
        boolean isAnagram = false;

        // For all words in anagramIndicators
        for (int i=0; i < anagramIndicators.length; i++) {
            // For all words in the clue
            for (int j=0; j < myClue.getNoWords(); j++) {
                if (anagramIndicators[i].equals(myClue.getWord(j))) {
                    GUI.addToCommentary(myClue.getWord(j) + " indicates an anagram!");
                    isAnagram = true;
                } // if
            } // for
        } // for

        return isAnagram;
    } // lookForAnagrams

    // Orders the characters in a string to be compared
    // to other strings to see if they are anagrams
    private static String orderString(String myString) {
        myString.toLowerCase();
        char[] myStringArray = myString.toCharArray();
        java.util.Arrays.sort(myStringArray);
        return(new String(myStringArray));
    } // orderString

    // Array of strings that indicate and anagram could be present
    private static String anagramIndicators[] = {
        "abnormal", "about", "adapt", "adjust", "affect", "agitate", "anyway",
        "around", "astray", "awkward", "bad", "becomes", "bent", "break",
        "brew", "broken", "bust", "careless", "change", "chaos", "circling",
        "compose", "concealing", "confuse", "construction", "convert",
        "correct", "crooked", "curious", "deception", "defected", "deranged",
        "design", "develop", "devious", "different", "disperse", "divert",
        "doctor", "drunk", "dubious", "effect", "emend", "engineered",
        "erratic", "false", "fashioned", "fixed", "funny", "gives", "goes",
        "gyrate", "havoc", "haywire", "horrible", "impaired", "incorrect",
        "insane", "irritated", "juggled", "kind of", "lousy", "mad", "made",
        "manipulate", "manoeuvre", "maybe", "messy", "mince", "mix", "mixed", "model",
        "modify", "molested", "mould", "muddled", "mysterious", "new",
        "obscure", "odd", "ordered", "organised", "out","over", "peculiar",
        "placed", "positioned", "possibly", "prepared", "processed", "put out",
        "queer", "repaired", "resolved", "reviewed", "revolving", "roving",
        "re-worded", "scramble", "serve up", "set", "shambles", "shift",
        "shuffled", "silly", "sorry", "sort", "spoiled", "stir", "strange",
        "terrible", "tip", "transform", "translate", "trick", "turned",
        "twisted", "untie", "unusual", "upset", "use", "variety", "vary",
        "wandering", "way", "weird", "wild", "wrong"
    }; // anagramIndicators
    
} // Anagram
