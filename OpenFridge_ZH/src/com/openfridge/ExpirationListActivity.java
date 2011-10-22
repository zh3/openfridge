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
		try {
			FridgeFoodDataClient client = new FridgeFoodDataClient();

			setContentView(R.layout.expiration_list);

			//Attach our data Array to the listview

			//TODO: need to have 3 separate arrays, but this should
			//be wrapped in the DataClient class, not here.


			((ListView)findViewById(R.id.pastLV)).setAdapter(new ArrayAdapter<FridgeFood>(this,
					android.R.layout.simple_list_item_checked, client.getPastFoods()));
			((ListView)findViewById(R.id.nearLV)).setAdapter(new ArrayAdapter<FridgeFood>(this,
					android.R.layout.simple_list_item_checked, client.getNearFoods()));
			((ListView)findViewById(R.id.goodLV)).setAdapter(new ArrayAdapter<FridgeFood>(this,
					android.R.layout.simple_list_item_checked, client.getGoodFoods()));
		} catch (Exception e) {
			// For debugging
			e.printStackTrace();
		}

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