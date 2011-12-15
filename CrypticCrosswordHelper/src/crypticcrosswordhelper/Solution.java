/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crypticcrosswordhelper;

/**
 *
 * @author Mike
 */
public class Solution {
    
    private String word;
    private int occurences;
    
    public Solution(String newWord) {
        word = newWord;
        occurences = 1;
    } // Solution()
    
    public boolean isEqual(String myWord) {
        if (word.equals(myWord))
            return true;
        return false;
    } // isEqual()
    
    public void inc() {
        occurences++;
    } // inc()
    
    public int getOccurences() {
        return occurences;
    }
    
    public void setOccurences(int newOcc) {
        occurences = newOcc;
    }
    
    public String getWord() {
        return word;
    }
    
    public void printSolution() {
        System.out.println(word + " occurs " + occurences + " times");
    } // printSolution()
    
} // Solution()
