/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crypticcrosswordhelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Copyright Mike Ray
 * The Univerity of Manchester
 */
public class Load {

    public static void loadCrossword(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream in = null;
        Crossword newCrossword = null;
        Object newLine;

        try {
            in = new ObjectInputStream(new FileInputStream(file));

            String newName = (String)in.readObject();
            int newNoClues = in.readInt();
            
            newLine = in.readObject();

            newCrossword = new Crossword(newName);
            newCrossword.clues = new <Clue>ArrayList();
            
            Clue newClue = new Clue("","","");

            for (int i=0; i<newNoClues; i++) {
                newClue.setOrigClue((String)in.readObject());
                newClue.setCleanClue((String)in.readObject());
                newClue.setNoWords(in.readInt());
                newClue.words = new <Word>ArrayList();

                Word newWord = new Word("");

                for (int j=0; j<newClue.getNoWords(); j++) {
                    newWord.setWord((String)in.readObject());
                    newWord.setSynonyms((HashSet)in.readObject());
                    newWord.setDefinitions((HashSet)in.readObject());
                    newClue.setWord(newWord);
                    newWord = new Word("");
                } // for

                newClue.setMask((String)in.readObject());
                newClue.setMaskLength(in.readInt());
                newClue.setAutoSolveWords((HashSet)in.readObject());
                newClue.setHiddenWords((HashSet)in.readObject());
                newClue.setLocation((String)in.readObject());
                newLine = in.readObject();

                newCrossword.addClue(newClue);
                newClue = new Clue("","","");
            } // for

            Main.setCurrentCrossword(newCrossword);


        } // try
        catch (IOException e) {
        } // catch
        finally {
            //Close the ObjectInputStream
            try {
                if (in != null) {
                    in.close();
                } // if
            } // try
            catch (IOException ex) {
                
            } // catch
        } // finally

    } // loadCrossword

}
