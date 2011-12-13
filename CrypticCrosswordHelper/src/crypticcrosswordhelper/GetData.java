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
 * Sends a request to server at Dictionary.com
 * Response is an XML file which is parsed to retrieve
 * Synonyms and Definitions of a given word
 * These are stored in the public global HashSets
 * (synonyms and definitions) which are accessed
 * from the Word class
 */
public class GetData {
    // HashSets to store synonyms and defintions
    public static HashSet synonyms, definitions;

    // Where the work is done...
    public static void downloadData(String query)
    {
        // Initialise HashSets
        synonyms = new HashSet();
        definitions = new HashSet();
        
        try
        {
            // New DocumentBuilder for XML file
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

            // URL to send request to (including userID and the query
            String request = "http://api-pub.dictionary.com/v001?vid=n2mlnuzldqyahdobdckwfp8gijou6svuvigicf4q62&q=" + query + "&type=define&site=thesaurus";
	    URL url = new URL(request);
	    InputStream stream = url.openStream();
            // Put response into Document
	    Document doc = docBuilder.parse(stream);

            // normalize text representation
            doc.getDocumentElement().normalize();

            // Parse XML file by list of entries. Put into a NodeList
            NodeList listOfResults = doc.getElementsByTagName("entry");
            int totalResults = listOfResults.getLength();

            // For every entry...
            for(int s=0; s<listOfResults.getLength() ; s++)
	    {
                Node firstResultNode = listOfResults.item(s);
                if(firstResultNode.getNodeType() == Node.ELEMENT_NODE)
                {
                  Element firstResultElement = (Element)firstResultNode;
                  
                  // Get definitions
                  NodeList definitionsList = firstResultElement.getElementsByTagName("definition");
                  Element definitionsElement = (Element)definitionsList.item(0);
                  NodeList textDefinitionsList = definitionsElement.getChildNodes();
                  
                  // Definitions are list seperated with ","
                  String str = ((Node)textDefinitionsList.item(0)).getNodeValue().trim();
                  StringTokenizer st = new StringTokenizer(str, ",");
                  while (st.hasMoreTokens())
                      // Add each definition to HashSet
                      definitions.add(st.nextToken().trim());

                  // Get synonyms
                  NodeList synonymsList = firstResultElement.getElementsByTagName("synonyms");
                  Element synonymsElement = (Element)synonymsList.item(0);
                  NodeList textSynonymsList = synonymsElement.getChildNodes();
                  
                  // Synonyms are list seperated with ","
                  str = ((Node)textSynonymsList.item(0)).getNodeValue().trim();
                  st = new StringTokenizer(str, ",");
                  while (st.hasMoreTokens()) {
                      String thisLine = st.nextToken();
                      
                      // If synonym is HTML link (unique to Dictionary.com),
                      // then extract just the synonym from 'a' tags
                      if (thisLine.contains("<a")) {
                          String[] split = thisLine.split(">");
                          thisLine = split[1];
                          split = thisLine.split("<");
                          split[0].replaceAll("<", "");
                          thisLine = split[0];
                      } // if
                      
                      // Add synonym to HashSet
                      synonyms.add(thisLine.trim());
                  } // while
                }//end of if clause
            }//end of for loop with s var
        }catch (SAXParseException err)
        {
            System.out.println ("** Parsing error" + ", line "
                    + err.getLineNumber() + ", uri " + err.getSystemId());
            System.out.println(" " + err.getMessage());
        } // catch
        catch (SAXException e) {
            Exception x = e.getException();
        } // catch
        catch (Throwable t) {
        } // catch
    } // downloadData()
    
} // GetData()
