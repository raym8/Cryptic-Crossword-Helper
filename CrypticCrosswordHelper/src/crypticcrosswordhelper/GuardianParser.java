package crypticcrosswordhelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// Parses the html on the cryptic crosswor page on www.guardian.co.uk and
// saves all cryptic clues and their solutions to a text file
/**
 * Copyright Mike Ray
 * The Univerity of Manchester
 */

/**
 *  Extract all "img" tags from an HTML document.
 */
public class GuardianParser
{
    private static String[][] data;
    private static int solNo;
    private static BufferedWriter testData;

    public static void main(String[] args) throws IOException
    {
        testData = new BufferedWriter(new FileWriter("testData.txt"));

        for (int i=22000; i<25482; i++)
            saveCrossword(i);

        testData.close();
    }

    public static void saveCrossword(int num) {
        try {
            URL url = new URL("http://www.guardian.co.uk/crosswords/cryptic/" + num);
            Document doc = Jsoup.parse(url, 100000);
            Elements label = doc.select("label");
            Elements solutions = doc.select("script");

            data = new String[label.size()][3];

            for (int i=0; i<label.size(); i++)
            {
                Element thisLabel = label.get(i);
                String thisFor = thisLabel.attr("for");
                if ((thisFor.contains("down")) || (thisFor.contains("across"))) // narrow results down to just locations
                {
                    data[i][0] = thisFor;
                }
                String thisText = thisLabel.text();
                if (thisText.endsWith(")")) // narrow results down to just clues
                {
                    if (thisText.charAt(1) == ' ')
                        thisText = thisText.replace(thisText.subSequence(0, 2), ""); // remove location from start of clue
                    else
                        thisText = thisText.replace(thisText.subSequence(0, 3), ""); // remove location from start of clue
                    String[] split = thisText.split("\\("); // remove solution length from end of clue
                    thisText = split[0];
                    thisText = thisText.trim();
                   
                    data[i][1] = thisText;
                }
            } // for

            BufferedWriter solsOut = new BufferedWriter(new FileWriter("out.txt"));
            solsOut.write(solutions.toString());
            solsOut.close();

            getSolutions();

            saveData();
           // System.out.println(label.toString());
        } catch (IOException ex) {
            Logger.getLogger(GuardianParser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void getSolutions() throws IOException
    {
        try {
            BufferedReader in = new BufferedReader(new FileReader("out.txt"));

            String thisLine;
            solNo = -1;

            String thisLocation = "";
            String thisSolution = "";

            while ((thisLine = in.readLine()) != null)
            {
                if (thisLine.contains("solutions[\""))
                {
                    String[] splitLine = thisLine.split("\"");
                    String thisFullLocation = splitLine[1];
                    String[] splitLocation = thisFullLocation.split("-");
                    String newLocation = splitLocation[0] + "-" + splitLocation[1];
                    String thisSolChar = splitLine[3];
                    
                    if (newLocation.equals(thisLocation))
                        thisSolution += thisSolChar;
                    else
                    {
                        if (solNo > -1)
                            data[solNo][2] = thisSolution.toLowerCase();
                        solNo++;
                        thisSolution = "";
                        thisSolution += thisSolChar;
                    } // else

                    thisLocation = newLocation;
                } // if
            } // while

        } catch (FileNotFoundException ex) {
            Logger.getLogger(GuardianParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public static void saveData() throws IOException
    {

        for (int i=0; i<solNo; i++) {
            for (int j=0; j<3; j++) {
                testData.write(data[i][j] + "\n");
            }
        }
    }
}