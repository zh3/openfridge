package com.openfridge;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

//DONE Make list headers colored only JW

public class ExpirationListActivity extends Activity {
	private static final int ROW_HEIGHT = 100;
	private Intent expire, itemEdit;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
	    expire = new Intent(this, ExpireActivity.class);
	    itemEdit = new Intent(this, ItemEditActivity.class);
		
		DataClient.getInstance().reloadFoods();
		setContentView(R.layout.expiration_list);
				
		for (ExpState key : ExpState.values()) {
			initFridgeFoodListView(key.getListViewID(), DataClient.getInstance().getFoods(key),
					new PastFridgeItemClickListener());
		}
	}

	private void initFridgeFoodListView(int viewId, List<FridgeFood> foods,
			OnItemClickListener listener) {
		ArrayAdapter<FridgeFood> a = new ArrayAdapter<FridgeFood>(this,
				android.R.layout.simple_list_item_1, foods);
		DataClient.getInstance().addListeningAdapter(a);
		ListView listView = (ListView) findViewById(viewId);
		listView.setTextFilterEnabled(true);
		listView.setAdapter(a);
		listView.setOnItemClickListener(listener);

		// Make items not focusable to avoid listitem / button conflicts
		listView.setItemsCanFocus(false);

		ListAdapter listAdapter = listView.getAdapter();

		int rows = listAdapter.getCount();
		// int height = android.R.attr.listPreferredItemHeight * rows;
		// for some reason listPreferredItemHeight didn't have a reasonable
		// value...
		int height = ROW_HEIGHT * rows;

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = height;
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	public void loadItemEdit(View view) {
		startActivity(itemEdit);
	}

	private class PastFridgeItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Log.d("Fridge debug", "list item clicked");
			if (parent.getClass() == ListView.class) {
				ListView parentList = (ListView) parent;

				FridgeFood food = (FridgeFood) parentList
						.getItemAtPosition(position);

				Toast.makeText(parentList.getContext(),
						"Expiration Date: " + food.getExpirationDateString(),
						Toast.LENGTH_SHORT).show();

				startActivity(expire);

				DataClient client = DataClient.getInstance();
				try {
					client.postFood(food);
				} catch (Exception e) {
					Toast.makeText(parentList.getContext(),
							"Connection error occurred", Toast.LENGTH_SHORT);
				}
			}
		}

	}
}
