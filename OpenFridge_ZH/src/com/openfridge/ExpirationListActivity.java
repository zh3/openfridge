package com.openfridge;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ExpirationListActivity extends Activity {
	//Tag for debug log
	private static final String DEBUG_TAG = "Openfridge";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(DEBUG_TAG, "checkpoint");

		//This code gets the xml and sets up the SAX Parser
		FridgeFoodDataClient client = new FridgeFoodDataClient();
		try {
			client.reloadFoods();
		} catch (Exception e) {
			// For debugging
			e.printStackTrace();
		}

		setContentView(R.layout.expiration_list);

		//Attach our data Array to the listview



		((ListView)findViewById(R.id.pastLV)).setAdapter(new ArrayAdapter<FridgeFood>(this,
				R.layout.expiration_list_item, R.id.text, client.getPastFoods()));
		((ListView)findViewById(R.id.nearLV)).setAdapter(new ArrayAdapter<FridgeFood>(this,
				R.layout.expiration_list_item, R.id.text, client.getNearFoods()));
		((ListView)findViewById(R.id.goodLV)).setAdapter(new ArrayAdapter<FridgeFood>(this,
				R.layout.expiration_list_item, R.id.text, client.getGoodFoods()));

	}
	 public void removeItem(View view) {
		 Toast.makeText(this, "remove clicked!", 
					Toast.LENGTH_SHORT).show();
	 }
	public void onListItemClick(
			ListView parent, View v, int position, long id) 
	{   
		//---toggle the check displayed next to the item---
		parent.setItemChecked(position, parent.isItemChecked(position));    

		if (parent.isItemChecked(position)) {
			Toast.makeText(this, "hahaha", 
					Toast.LENGTH_SHORT).show();
		}
	}  
}