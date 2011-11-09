package com.openfridge;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

/**
 * A class which facilitates communication with the server, allowing the
 * retrieving and updating of fridge food items on the database server.
 * 
 * @author Tom, Shimona, Jesse
 */

public class DataClient {

	private static final int RED = Color.parseColor("#ED1C24");
	private static final int YELLOW = Color.parseColor("#FFD300"); 
	private static final int GREEN = Color.parseColor("#228B22");
	// ArrayLists for the data from the XML
	private Map<ExpState, List<FridgeFood>> foods = new HashMap<ExpState, List<FridgeFood>>(); 
	private List<ShoppingItem> shoppingList = new ArrayList<ShoppingItem>();
	// Parsing stuff
	private XMLReader xr;
	private SAXParser sp;
	private SAXParserFactory spf;
	private URL fridgeFoodURL, shoppingItemURL;
	// Update notification stuff
	private Set<ArrayAdapter<?>> listeners = new HashSet<ArrayAdapter<?>>();
	private GetDataAsyncTask getDataTask;
	{
		for (ExpState key : ExpState.values()) {
			foods.put(key, new ArrayList<FridgeFood>());
		}
		/* Create the URLs we want to load xml-data from. */
		/* If you check them, you'll see they are mini-xml documents generated from elvin's database. */
			try {
				fridgeFoodURL = new URL("http://openfridge.heroku.com/fridge_foods.xml");
				shoppingItemURL = new URL("http://openfridge.heroku.com/shopping_lists.xml");
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
			
		
		/* Get a SAXParser from the SAXPArserFactory. */
		spf = SAXParserFactory.newInstance();
		try {
			/* Get the XMLReader of the SAXParser we created. */
			sp = spf.newSAXParser();
			xr = sp.getXMLReader();
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		}
	}

	private DataClient() {
	}

	private void parse(SAXHandler<?> h, URL url) {
		xr.setContentHandler(h);
		try {
			xr.parse(new InputSource(url.openStream()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
	}
	private class GetDataAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... arg0) {

			FridgeFoodHandler ffH = new FridgeFoodHandler();
			parse(ffH,fridgeFoodURL);

			// Changes the contents of the ArrayList's,
			// rather than re-assigning them.
			for (ExpState key : ExpState.values()) {
				foods.get(key).clear();
				foods.get(key).addAll(ffH.getFoods(key));
			}

			ShoppingItemHandler siH = new ShoppingItemHandler();
			parse(siH, shoppingItemURL);

			shoppingList.clear();
			shoppingList.addAll(siH.getFoods());
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			getDataTask = null;
			for (ArrayAdapter<?> a : listeners) {
				a.notifyDataSetChanged();
			}
		}
	}

	public void addListeningAdapter(ArrayAdapter<?> a) {
		listeners.add(a);
	}

	public void removeListeningAdapter(ArrayAdapter<?> a) {
		listeners.remove(a);
	}

	public void reloadFoods() {
		if (getDataTask==null) {
			getDataTask = new GetDataAsyncTask();
			getDataTask.execute();
		}
	}
	
	/**
	 * Post an item of food to the cloud database. If the id of the given food
	 * exists already, the existing record will be updated in the database.
	 * 
	 * @param food
	 *            The FridgeFood object with the fields to be posted to
	 *            database.
	 */
	public void postFood(FridgeFood food) throws MalformedURLException,
			IOException {
		(new URL(
				String.format(
						"http://openfridge.heroku.com/fridge_foods/push/%d/%s/%d/%d/%d",
						food.getUserId(),
						URLEncoder.encode(food.getDescription(), "UTF-8"),
						food.getExpirationYear(), food.getExpirationMonth(),
						food.getExpirationDay()))).openStream().read();
	}

	public List<ShoppingItem> getShoppingList() {
		return shoppingList;
	}
	public List<FridgeFood> getFoods(ExpState key) {
		return foods.get(key);
	}
	public int getExpirationListColor() {
		if (!getFoods(ExpState.EXPIRED).isEmpty()) {
			//There are expired items:
			return RED;
		} else if (!getFoods(ExpState.NEAR).isEmpty()) {
			//There are stale items:
			return YELLOW;
		}
		return GREEN;
	}
	public int getShoppingListColor() {
		return Color.parseColor("#FFFFFF");
	}
	
	public static DataClient getInstance() {
		return DataClientHolder.client;
	}
	
	private static class DataClientHolder /* Pugh's Way */ {
		public static final DataClient client = new DataClient();
	}
}