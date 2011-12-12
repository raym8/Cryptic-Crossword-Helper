/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crypticcrosswordhelper;

import java.util.HashSet;

/**
 * Copyright Mike Ray
 * The Univerity of Manchester
 */
public class HiddenWord {
    
    private static HashSet hiddenWords;

    public static HashSet findHiddenWord(String clue, String mask) {
        hiddenWords = new HashSet();

        for (int i=0; i <= (clue.length()-mask.length()); i++) {
            String thisWord = "";
            for (int j=0; j < mask.length(); j++) {
                thisWord = thisWord + clue.charAt(i+j);
            }
            for (int j=0; j < Main.dict.length; j++) {
                if (thisWord.equals(Main.dict[j]))
                        hiddenWords.add(thisWord);
            }
        }

        return hiddenWords;
    }

}
