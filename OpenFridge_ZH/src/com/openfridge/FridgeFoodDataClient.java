package com.openfridge;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.util.Log;

/**
 * A class which facilitates communication with the server, allowing the
 * retrieving and updating of fridge food items on the database server.
 * 
 * @author Tom, Shimona
 */
public class FridgeFoodDataClient {
    
    public FridgeFoodDataClient() {
   
    }
    
    public void reloadFoods() throws IOException, SAXException {
        
        /* Create a new ContentHandler and apply it to the XML-Reader*/ 
        FridgeFoodHandler userXmlHandler = new FridgeFoodHandler();
        xr.setContentHandler(userXmlHandler);
        
        /* Create a URL we want to load some xml-data from. */
        /* If you check this url, it's a mini xml from elvin's db */
        
        /* Parse the xml-data from our URL. */
        xr.parse(new InputSource(dataURL.openStream()));
        /* Parsing has finished. */

        //Changes the contents of the ArrayList's, 
        //rather than re-assigning them.
        goodFoods.clear();
        goodFoods.addAll(userXmlHandler.getGoodFoods());
        nearFoods.clear(); 
        nearFoods.addAll(userXmlHandler.getNearFoods());
        expiredFoods.clear(); 
        expiredFoods.addAll(userXmlHandler.getExpiredFoods());
    }
    
    /**
     * Get an ArrayList containing records of all the Foods that have expired,
     * based on the information from the server available at the last call to
     * reloadFoods()
     * 
     * @return An ArrayList of expired foods
     */
    public ArrayList<FridgeFood> getExpiredFoods() {
    	return expiredFoods;
    }

    /**
     * Get an ArrayList containing records of all the Foods that are close to,
     * expiration based on the information from the server available at the last
     * call to reloadFoods()
     * 
     * @return An ArrayList of foods soon to expire
     */
    public ArrayList<FridgeFood> getNearFoods() {
    	return nearFoods;
    }

    /**
     * Get an ArrayList containing records of all the Foods that are fresh,
     * based on the information from the server available at the last call to
     * reloadFoods()
     * 
     * @return An ArrayList of fresh foods
     */
    public ArrayList<FridgeFood> getGoodFoods() {
    	return goodFoods;
    }

    /**
     * Post an item of food to the cloud database. If the id of the given
     * food exists already, the existing record will be updated in the database.
     * 
     * @param food The FridgeFood object with the fields to be posted to
     * database.
     */
    public void postFood(FridgeFood food) throws MalformedURLException, 
            IOException {
        (new URL(String.format("http://openfridge.heroku.com/fridge_foods/push/%d/%s/%d/%d/%d", 
                food.getUserId(), URLEncoder.encode(food.getDescription(),"UTF-8"), 
                food.getExpirationYear(), food.getExpirationMonth(),
                food.getExpirationDay()))).openStream().read();
    }
    
    //ArrayLists for the data from the XML
    private ArrayList<FridgeFood> goodFoods;
    private ArrayList<FridgeFood> nearFoods;
    private ArrayList<FridgeFood> expiredFoods;
    //Parsing stuff
    private XMLReader xr;
    private SAXParser sp;
    private SAXParserFactory spf;
    private URL dataURL;
    {
    	try {
    		dataURL = new URL("http://openfridge.heroku.com/fridge_foods.xml");
    	} catch (MalformedURLException e) {}
        /* Get a SAXParser from the SAXPArserFactory. */
        spf = SAXParserFactory.newInstance();
        try {
            /* Get the XMLReader of the SAXParser we created. */
        	sp = spf.newSAXParser();
			xr = sp.getXMLReader();
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {}
    }
}
