package crypticcrosswordhelper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*
 * Parses wikipedia.org for encyclopeadia entries
 */
public class WikipediaParser
{
    public static String getEntry(String query) throws IOException {
        query = query.replaceAll(" ", "_");
        String result = "";
        
        try {
            URL url = new URL("http://en.wikipedia.org/wiki/" + query);

            Document doc = Jsoup.parse(url, 100000);            
            Elements entry = doc.select("div.mw-content-ltr");
            Elements pEntries = entry.select("p");
            result = pEntries.get(0).text();

            /*
             * To retreive the entire Wikipedia entry:
             * 
                for (int i=0; i<pEntries.size(); i++)
                {
                    Element thisEntry = pEntries.get(i);
                    result += thisEntry.text() + "\n\n";
                } 
             */            
        }
        catch (MalformedURLException ex) {
            Logger.getLogger(WikipediaParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static HashSet getUsefulWords(String result) {
        HashSet usefulWords = new HashSet();

        String[] words = result.split(" ");
        String thisWord = null;
        for (int i=0; i<words.length; i++) {
            thisWord = words[i];
            if (!Main.notUsefulWords.contains(thisWord) && !thisWord.equals(""))
                usefulWords.add(thisWord);
        }        
        return usefulWords;
    }
}
            
     