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

        goodFoods = userXmlHandler.getGoodFoods();
        nearFoods = userXmlHandler.getNearFoods();
        expiredFoods = userXmlHandler.getExpiredFoods();
    }
    
    /**
     * Copy a given ArrayList of FridgeFoods
     * 
     * @param source The ArrayList to be copied
     * @return A deep copy of the source ArrayList
     */
    private ArrayList<FridgeFood> copyFoods(ArrayList<FridgeFood> source) {
        
        ArrayList<FridgeFood> copy = new ArrayList<FridgeFood>();
        for (FridgeFood f: source) {
            if (f != null) {
                copy.add((FridgeFood) f.clone());
            }
        }
        
        return copy;
    }
    
    /**
     * Get an ArrayList containing records of all the Foods that have expired,
     * based on the information from the server available at the last call to
     * reloadFoods()
     * 
     * @return An ArrayList of expired foods
     */
    public ArrayList<FridgeFood> getExpiredFoods() {
		//try {
			return copyFoods(expiredFoods);
		//} catch (Exception e) {
		//	return new ArrayList<FridgeFood>();
		//}
    }

    /**
     * Get an ArrayList containing records of all the Foods that are close to,
     * expiration based on the information from the server available at the last
     * call to reloadFoods()
     * 
     * @return An ArrayList of foods soon to expire
     */
    public ArrayList<FridgeFood> getNearFoods() {
		try {
			return copyFoods(nearFoods);
		} catch (Exception e) {
			return new ArrayList<FridgeFood>();
		}
    }

    /**
     * Get an ArrayList containing records of all the Foods that are fresh,
     * based on the information from the server available at the last call to
     * reloadFoods()
     * 
     * @return An ArrayList of fresh foods
     */
    public ArrayList<FridgeFood> getGoodFoods() {
    		//try {
        Log.d("dataclientff", "good foods size is: " + goodFoods.size());
    			return copyFoods(goodFoods);
    		//} catch (Exception e) {
    		//	return new ArrayList<FridgeFood>();
    		//}
    }

    /**
     * Post an item of food to the website database. If the id of the given
     * food exists already, the existing record will be updated on the website.
     * 
     * @param food The FridgeFood object with the fields to be posted to
     * database.
     */
    public void postFood(FridgeFood food) throws MalformedURLException, 
            IOException {
        String urlString 
            = "http://openfridge.heroku.com/fridge_foods/push/" 
              + food.getUserId() + "/" + food.getDescription() + "/" 
              + food.getExpirationYear()+ "/" + food.getExpirationMonth()
              + "/" + food.getExpirationDay();
        URL pushUrl = new URL(urlString);
        
        pushUrl.openStream().read();
    }
    
    //Arraylist for data from XML
    private ArrayList<FridgeFood> goodFoods;
    private ArrayList<FridgeFood> nearFoods;
    private ArrayList<FridgeFood> expiredFoods;
    private XMLReader xr;
    private SAXParser sp;
    private SAXParserFactory spf;
    private URL url;
}
