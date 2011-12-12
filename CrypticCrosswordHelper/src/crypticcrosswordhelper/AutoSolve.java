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
public class AutoSolve {

    public static HashSet autoSolve(String mask) {
        int maskLength = mask.length();
        boolean solution = true;
        HashSet autoSolveList = new HashSet();
        for (int i = 0; i < Main.dict.length; i++) {
            if (Main.dict[i].length() == maskLength) {
                solution = true;
                for (int j = 0; j < maskLength; j++) {
                    if (mask.charAt(j) != '\u003f') {
                        if (mask.charAt(j) != Main.dict[i].charAt(j))
                            solution = false;
                    } // if
                } // for
                if (solution)
                    autoSolveList.add(Main.dict[i]);
            } // if
        } // for

        return autoSolveList;
    }

}
