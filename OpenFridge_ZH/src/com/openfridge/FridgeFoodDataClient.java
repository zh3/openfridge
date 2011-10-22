package com.openfridge;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * A class which facilitates communication with the server, allowing the
 * retrieving and updating of fridge food items on the database server.
 * 
 * @author Tom, Shimona
 */
public class FridgeFoodDataClient {
    
    public FridgeFoodDataClient() throws IOException, MalformedURLException, 
            ParserConfigurationException, SAXException {
        reloadFoods();
    }
    
    public void reloadFoods() throws IOException, MalformedURLException, 
            ParserConfigurationException, SAXException {
        /* Create a URL we want to load some xml-data from. */
        /* If you check this url, it's a mini xml from elvin's db */
        url = new URL("http://openfridge.heroku.com/fridge_foods.xml");

        /* Get a SAXParser from the SAXPArserFactory. */
        spf = SAXParserFactory.newInstance();
        sp = spf.newSAXParser();

        /* Get the XMLReader of the SAXParser we created. */
        xr = sp.getXMLReader();
        
        /* Create a new ContentHandler and apply it to the XML-Reader*/ 
        FridgeFoodHandler userXmlHandler = new FridgeFoodHandler();
        xr.setContentHandler(userXmlHandler);
        
        /* Parse the xml-data from our URL. */
        xr.parse(new InputSource(url.openStream()));
        /* Parsing has finished. */

        foods = userXmlHandler.getParsedData();
    }
    
    /**
     * Get a list of containing records of all the foods on the website 
     * database.
     * 
     * @return A list of foods from the website.
     */
    public ArrayList<FridgeFood> getFoods() throws IOException, 
            MalformedURLException, ParserConfigurationException, SAXException {
        ArrayList<FridgeFood> copy = new ArrayList<FridgeFood>();
        for (FridgeFood f: foods) {
            copy.add((FridgeFood) f.clone());
        }
        
        return copy;
    }
    
    /**
     * Post an item of food to the website database. If the id of the given
     * food exists already, the existing record will be updated on the website.
     * 
     * @param food The FridgeFood object with the fields to be posted to
     * database.
     */
    public void postFood(FridgeFood food) {
        // TODO need elvin
    }
    
    //Arraylist for data from XML
    private ArrayList<FridgeFood> foods;
    private XMLReader xr;
    private SAXParser sp;
    private SAXParserFactory spf;
    private URL url;
}
