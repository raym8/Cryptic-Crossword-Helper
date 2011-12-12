/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crypticcrosswordhelper;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Copyright Mike Ray
 * The Univerity of Manchester
 */
public class Word {

    private String word;
    private HashSet synonyms;
    private HashSet definitions;

    public Word(String myWord) {
        word = myWord;

        synonyms = new HashSet();
        synonyms = GetData.getSynonyms(myWord);

        definitions = new HashSet();
        definitions = GetData.getDefinitions(myWord);
    } // Word constructor

    public String getWord() {
        return word;
    }

    public void setWord(String newWord) {
        word = newWord;
    }

    public HashSet getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(HashSet newSynonyms) {
        synonyms = newSynonyms;
    }

    public void setDefinitions(HashSet newDefinitions) {
        definitions = newDefinitions;
    }

    public HashSet getDefinitions() {
        return definitions;
    }

    public void printWord() {
        System.out.println("-WORD:");
        System.out.println("-word: " + word);
        System.out.print("-synonyms: ");
        Iterator iterator;
        if (!synonyms.isEmpty()) {
            iterator = synonyms.iterator();
            while (iterator.hasNext())
                System.out.print(iterator.next() + ", ");
        }
        else
            System.out.print("NONE");
        System.out.print("\n-definitions: ");
        if (!definitions.isEmpty()) {
            iterator = definitions.iterator();
            while (iterator.hasNext())
                System.out.print(iterator.next() + ", ");
        }
        else
            System.out.print("NONE");
        System.out.print("\n");
    }

}
