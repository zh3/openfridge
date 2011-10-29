package com.openfridge;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
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

		setContentView(R.layout.expiration_list);
			//Setup the Listview
			
			initFridgeFoodListView(R.id.pastLV, client.getPastFoods(), 
			        new PastFridgeItemClickListener());
			initFridgeFoodListView(R.id.nearLV, client.getPastFoods(), 
                    new PastFridgeItemClickListener());
			initFridgeFoodListView(R.id.goodLV, client.getPastFoods(), 
                    new PastFridgeItemClickListener());
		} catch (Exception e) {
			// For debugging
			e.printStackTrace();
		}

	}
public void removeItem(View view) {
	 Toast.makeText(this, "remove clicked!", 
				Toast.LENGTH_SHORT).show();
}
	private void initFridgeFoodListView(int viewId, ArrayList<FridgeFood> foods,
	        OnItemClickListener listener) {
	    ListView listView = (ListView) findViewById(viewId);
        listView.setChoiceMode(2);   //CHOICE_MODE_MULTIPLE
        listView.setTextFilterEnabled(true);
        listView.setAdapter(new ArrayAdapter<FridgeFood>(this,
        		R.layout.expiration_list_item, R.id.text, foods));
        listView.setOnItemClickListener(listener);
    
	}
	
	public void loadItemEdit(View view) {
	    Intent intent = new Intent(this, ItemEditActivity.class);
        startActivity(intent);
	}
}