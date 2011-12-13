package crypticcrosswordhelper;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Stores a representation of a Word
 */
public class Word {

    private String word;            // String containing the word
    private HashSet synonyms;       // HashSet of the words synonyms
    private HashSet definitions;    // HashSet of the words definitions

    // Constructor: Word
    public Word(String myWord) {
        word = myWord;
        
        // Get the synonym and defintion data for the given word
        GetData.downloadData(word);
        synonyms = new HashSet();
        synonyms = GetData.synonyms;
        definitions = new HashSet();
        definitions = GetData.definitions;
    } // Word constructor

    public String getWord() {
        return word;
    } // getWord()

    public void setWord(String newWord) {
        word = newWord;
    } // setWord()

    public HashSet getSynonyms() {
        return synonyms;
    } // getSynonyms()

    public void setSynonyms(HashSet newSynonyms) {
        synonyms = newSynonyms;
    } // setSynonyms()

    public void setDefinitions(HashSet newDefinitions) {
        definitions = newDefinitions;
    } // setDefinitions()

    public HashSet getDefinitions() {
        return definitions;
    } // getDefinitions()

    // Print a word (For de-bugging only)
    public void printWord() {
        System.out.println("-WORD:");
        System.out.println("-word: " + word);
        System.out.print("-synonyms: ");
        Iterator iterator;
        if (!synonyms.isEmpty()) {
            iterator = synonyms.iterator();
            while (iterator.hasNext())
                System.out.print(iterator.next() + ", ");
        } // if
        else
            System.out.print("NONE");
        System.out.print("\n-definitions: ");
        if (!definitions.isEmpty()) {
            iterator = definitions.iterator();
            while (iterator.hasNext())
                System.out.print(iterator.next() + ", ");
        } // if
        else
            System.out.print("NONE");
        System.out.print("\n");
    } // printWord()

} // Word()
