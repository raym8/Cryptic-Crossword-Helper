/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crypticcrosswordhelper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Copyright Mike Ray
 * The Univerity of Manchester
 */
public class TestData {

    public static void loadTestData() throws FileNotFoundException {
        try {
            FileReader file = new FileReader("testData2.txt");
            BufferedReader in = new BufferedReader(file);
            String thisLocation, thisClue, thisSolution, thisMask, thisMostProbableSolution;
            Clue newClue = null;
            int noCorrect = 0;
            int noPartiallyCorrect = 0;
            int noIncorrect = 0;
            int index = 0;
            while ((thisLocation = in.readLine()) != null) {
                index++;
                thisClue = in.readLine();
                thisSolution = in.readLine();
                thisMask = thisSolution.replaceAll(".", "?");

                newClue = new Clue(thisClue, thisMask, thisLocation);
                ProcessClue.setCurrentClue(newClue);
                ProcessClue.procClue();

                thisMostProbableSolution = ProcessClue.getMaxOccuringWord();
                if (thisMostProbableSolution.equals(thisSolution)) {
                    noCorrect++;
                } // if
                else {
                    Iterator i = ProcessClue.getPossibleSolutions().iterator();
                    String thisSol;
                    while (i.hasNext()) {
                        thisSol = (String)i.next();
                        if (thisSol.equals(thisSolution))
                            noPartiallyCorrect++;
                    } // while
                } // else

                if (noCorrect+noPartiallyCorrect+noIncorrect < index)
                    noIncorrect++;

                System.out.println("Solution: " + thisSolution);
                System.out.println("PREDICTED SOLUTION: " + thisMostProbableSolution);
                System.out.println("*****************************");

            } // while

            System.out.println("noCorrect: " + noCorrect + "/" + index);
            System.out.println("noPatiallyCorrect: " + noPartiallyCorrect + "/" + index);
            System.out.println("noIncorrect: " + noIncorrect + "/" + index);
        } catch (IOException ex) {
            Logger.getLogger(TestData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
