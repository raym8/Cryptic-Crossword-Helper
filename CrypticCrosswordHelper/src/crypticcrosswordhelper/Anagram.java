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
public class Anagram {

    private static ArrayList<String> anagramList;

    private static char[] clueArray;
    private static String clue;

    public static ArrayList<String> doAnagram(String myClue, int windowSize) {
        anagramList = new ArrayList<String>();
        
        if (windowSize > myClue.length()) {
            anagramList.add("Select a smaller length size");
            return anagramList;
        } // if

        clue = myClue;

        clue = clue.replaceAll("\\s+", ""); // remove all spaces from the clue
        clue = clue.toLowerCase(); // make sure clue is all lower case

        for (int i=0; i<=(clue.length()-windowSize); i++)
        {
            String myAnagram = "";
            for (int j=0; j<windowSize; j++)
                myAnagram = myAnagram + clue.charAt(i+j);

            String thisAnagram = orderString(myAnagram);

            anagram(thisAnagram);
        } // for

        if (anagramList.isEmpty())
            return null;

        return anagramList;
    }

    private static void anagram(String thisClue) {
        String possMatch;
        char[] possMatchArray;

        for (int i= 0; i < Main.dict.length; i++) {
            if (thisClue.length() == Main.dict[i].length()) {
                possMatch = orderString(Main.dict[i]);
                if (thisClue.equals(possMatch))
                    anagramList.add(Main.dict[i]);
            } // if
        } // for
        
    } // anagram
    
    public static boolean lookForAnagrams(Clue myClue) {

        boolean isAnagram = false;

        for (int i=0; i < anagramIndicators.length; i++) {
            for (int j=0; j < myClue.getNoWords(); j++) {
                if (anagramIndicators[i].equals(myClue.getWord(j))) {
                    GUI.addToCommentary(myClue.getWord(j) + " indicates an anagram!");
                    isAnagram = true;
                } // if
            } // for
        } // for

        return isAnagram;
    } // lookForAnagrams

    private static String orderString(String myString) {
        myString.toLowerCase();
        char[] myStringArray = myString.toCharArray();
        java.util.Arrays.sort(myStringArray);
        return(new String(myStringArray));
    } // orderString

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
