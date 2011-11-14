package com.openfridge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

//DONE Make list headers colored only JW

public class ExpirationListActivity extends Activity implements Observer {
	private static final int ROW_HEIGHT = 100;
	private Intent itemEdit;
	private Set<SimpleAdapter> simpleAdapters = new HashSet<SimpleAdapter>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		itemEdit = new Intent(this, ItemEditActivity.class);
		
		setContentView(R.layout.expiration_list);

		initFridgeFoodListViews();
	}
	
	private void initFridgeFoodListViews() {
	    for (ExpState key : ExpState.values()) {
            initFridgeFoodListView(key.getListViewID(), DataClient
                    .getInstance().getFoods(key),
                    new PastFridgeItemClickListener());
        }
	}
	
	private void initFridgeFoodListView(int viewId, List<FridgeFood> foods,
			OnItemClickListener listener) {
	    ArrayList<HashMap<String, String>> mapList 
	        = new ArrayList<HashMap<String, String>>();
	    
	    for (FridgeFood f: foods) {
	        HashMap<String, String> map;

            map = new HashMap<String, String>();
            map.put("line1", f.getDescription());
            map.put("line2", f.getExpirationDateString());
            map.put("foodId", Integer.toString(f.getId()));
            mapList.add(map);
	    }
	    
	    String[] from = { "line1", "line2" };
        int[] to = { android.R.id.text1, android.R.id.text2 };

        // create the adapter and assign it to the listview
        SimpleAdapter adapter = new SimpleAdapter(this, mapList,
                android.R.layout.simple_list_item_2, from, to);
        
        ListView listView = (ListView) findViewById(viewId);
        listView.setAdapter(adapter);
	    
//		ArrayAdapter<FridgeFood> a = new ArrayAdapter<FridgeFood>(this,
//				android.R.layout.simple_list_item_1, foods);
//		arrayAdapters.add(a);
		simpleAdapters.add(adapter);
		listView.setTextFilterEnabled(true);
//		listView.setAdapter(a);
		listView.setOnItemClickListener(listener);

		// Make items not focusable to avoid listitem / button conflicts
		listView.setItemsCanFocus(false);
		setHeight(viewId);
	}

	private void setHeight(int viewID) {
		ListView listView = (ListView) findViewById(viewID);

		ListAdapter listAdapter = listView.getAdapter();

		int rows = listAdapter.getCount();
		int height = ROW_HEIGHT * rows;

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = height;
		listView.setLayoutParams(params);
		listView.requestLayout();
	}
	@Override
	protected void onPause() {
		super.onPause();
		DataClient.getInstance().deleteObserver(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		DataClient.getInstance().addObserver(this);
		update(null,null);
		DataClient.getInstance().reloadFoods();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	public void loadItemEdit(View view) {
		startActivity(itemEdit);
	}

	private class PastFridgeItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (parent.getClass() == ListView.class) {
			    Intent expire;
			    
				ListView parentList = (ListView) parent;
				Context parentContext = parentList.getContext();
				HashMap<String, String> map 
				    = (HashMap<String, String>) parentList
						                        .getItemAtPosition(position);
	
				/*
				Toast.makeText(parentContext,
						"Expiration Date: " + food.getExpirationDateString(),
						Toast.LENGTH_SHORT).show();
				*/
				FridgeFood food = getFoodFromMap(map);
				
				if (food != null) {
    				expire = new Intent(parentContext, ExpireActivity.class);
    				expire.putExtras(food.bundle());
    				startActivity(expire);
				} else {
				    Toast.makeText(parentContext,
	                        "Error: Cannot find food item",
	                        Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
	
	private FridgeFood getFoodFromMap(HashMap<String, String> map) {
	    int foodId = Integer.parseInt(map.get("foodId"));
	    
	    for (ExpState key : ExpState.values()) {
            List<FridgeFood> foods = DataClient.getInstance().getFoods(key);
            
            for (FridgeFood f: foods) {
                if (f.getId() == foodId) {
                    return f;
                }
            }
	    }
	    
	    return null;
	}

	@Override
	public void update(Observable observable, Object data) {
	    initFridgeFoodListViews();
	    
		for (ExpState key : ExpState.values()) {
			setHeight(key.getListViewID());
		}
	}
}
