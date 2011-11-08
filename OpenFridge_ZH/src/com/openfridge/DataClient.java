package com.openfridge;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;

/**
 * A class which facilitates communication with the server, allowing the
 * retrieving and updating of fridge food items on the database server.
 * 
 * @author Tom, Shimona
 */

// private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
// /** The system calls this to perform work in a worker thread and
// * delivers it the parameters given to AsyncTask.execute() */
// protected Bitmap doInBackground(String... urls) {
// return loadImageFromNetwork(urls[0]);
// }
//
// /** The system calls this to perform work in the UI thread and delivers
// * the result from doInBackground() */
// protected void onPostExecute(Bitmap result) {
// mImageView.setImageBitmap(result);
// }
// }

public class DataClient {

	// ArrayLists for the data from the XML
	private ArrayList<FridgeFood> goodFoods = new ArrayList<FridgeFood>();
	private ArrayList<FridgeFood> nearFoods = new ArrayList<FridgeFood>();
	private ArrayList<FridgeFood> expiredFoods = new ArrayList<FridgeFood>();
	private List<ShoppingItem> shoppingList = new ArrayList<ShoppingItem>();
	// Parsing stuff
	private XMLReader xr;
	private SAXParser sp;
	private SAXParserFactory spf;
	private URL fridgeFoodURL, shoppingItemURL;
	// Update notification stuff
	private Set<ArrayAdapter<?>> listeners = new HashSet<ArrayAdapter<?>>();
	{
		/* Create the URLs we want to load xml-data from. */
		/* If you check them, you'll see they are mini-xml documents generated from elvin's database. */
		try {
			fridgeFoodURL = new URL("http://openfridge.heroku.com/fridge_foods.xml");
			shoppingItemURL = new URL("http://openfridge.heroku.com/shopping_lists.xml");
		} catch (MalformedURLException e) {
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

	private void parse(Handler<?> h, URL url) {
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
			goodFoods.clear();
			goodFoods.addAll(ffH.getGoodFoods());
			nearFoods.clear();
			nearFoods.addAll(ffH.getNearFoods());
			expiredFoods.clear();
			expiredFoods.addAll(ffH.getExpiredFoods());

			ShoppingItemHandler siH = new ShoppingItemHandler();
			parse(siH, shoppingItemURL);

			shoppingList.clear();
			shoppingList.addAll(siH.getFoods());
//			shoppingList.addAll(Arrays.asList(new ShoppingItem("Milk", 1, 1),
//					new ShoppingItem("Eggs", 2, 1), new ShoppingItem("Kale", 3,
//							1), new ShoppingItem("Beer", 4, 1),
//					new ShoppingItem("Beef", 5, 1)));
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
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
		new GetDataAsyncTask().execute();
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

	public static DataClient getInstance() {
		return DataClientHolder.client;
	}
	
	private static class DataClientHolder /* Pugh's Way */ {
		public static final DataClient client = new DataClient();
	}
}
