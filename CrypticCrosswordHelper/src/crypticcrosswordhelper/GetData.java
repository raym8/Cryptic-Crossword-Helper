/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crypticcrosswordhelper;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.net.URL;
import java.io.InputStream;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * Copyright Mike Ray
 * The Univerity of Manchester
 */
public class GetData {

    private static String[] words;
    private static HashSet synonyms, definitions;

    public static HashSet getSynonyms(String word) {
        synonyms = new HashSet();
        downloadData(word, 0);
        if (synonyms == null)
            System.out.println("No synonyms retreived for " + word);
        return synonyms;
    } // getSynonyms

    public static HashSet getDefinitions(String word) {
        definitions = new HashSet();
        downloadData(word, 1);
        return definitions;
    } // getDefinitions

    public static void downloadData(String query, int method)
    {
        try
        {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

            String request = "http://www.abbreviations.com/services/v1/syno.aspx?tokenid=tk1722&word=" + query;
	    URL url = new URL(request);
	    InputStream stream = url.openStream();
	    Document doc = docBuilder.parse(stream);

            // normalize text representation
            doc.getDocumentElement ().normalize ();

            NodeList listOfResults = doc.getElementsByTagName("result");
            int totalResults = listOfResults.getLength();

            for(int s=0; s<listOfResults.getLength() ; s++)
	    {
                Node firstResultNode = listOfResults.item(s);
                if(firstResultNode.getNodeType() == Node.ELEMENT_NODE)
                {
                  Element firstResultElement = (Element)firstResultNode;

                  //-------
                  NodeList termList = firstResultElement.getElementsByTagName("term");
                  Element termElement = (Element)termList.item(0);

                  NodeList textTermList = termElement.getChildNodes();

                  //------
                  NodeList definitionsList = firstResultElement.getElementsByTagName("definition");
                  Element definitionsElement = (Element)definitionsList.item(0);

                  NodeList textDefinitionsList = definitionsElement.getChildNodes();

                  if (method == 1) {
                      String str = ((Node)textDefinitionsList.item(0)).getNodeValue().trim();
                      StringTokenizer st = new StringTokenizer(str, ",");
                      while (st.hasMoreTokens())
                          definitions.add(st.nextToken());
                  } // if definitions requested

                  //-------
                  NodeList synonymsList = firstResultElement.getElementsByTagName("synonyms");
                  Element synonymsElement = (Element)synonymsList.item(0);

                  NodeList textSynonymsList = synonymsElement.getChildNodes();

                  if (method == 0) {
                      String str = ((Node)textSynonymsList.item(0)).getNodeValue().trim();
                      StringTokenizer st = new StringTokenizer(str, ",");
                      while (st.hasMoreTokens())
                          synonyms.add(st.nextToken());
                  } // if synonyms requested
                }//end of if clause
            }//end of for loop with s var
        }catch (SAXParseException err)
        {
            System.out.println ("** Parsing error" + ", line "
                    + err.getLineNumber () + ", uri " + err.getSystemId ());
            System.out.println(" " + err.getMessage ());
        }
        catch (SAXException e)
        {
            Exception x = e.getException ();
        }
        catch (Throwable t)
        {
        }
    }

}
