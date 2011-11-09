package com.openfridge;

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
import android.widget.Toast;

//DONE Make list headers colored only JW

public class ExpirationListActivity extends Activity implements Observer {
	private static final int ROW_HEIGHT = 100;
	private Intent itemEdit;
	private Set<ArrayAdapter<?>> arrayAdapters = new HashSet<ArrayAdapter<?>>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		itemEdit = new Intent(this, ItemEditActivity.class);
		
		setContentView(R.layout.expiration_list);

		for (ExpState key : ExpState.values()) {
			initFridgeFoodListView(key.getListViewID(), DataClient
					.getInstance().getFoods(key),
					new PastFridgeItemClickListener());
		}
	}

	private void initFridgeFoodListView(int viewId, List<FridgeFood> foods,
			OnItemClickListener listener) {
		ArrayAdapter<FridgeFood> a = new ArrayAdapter<FridgeFood>(this,
				android.R.layout.simple_list_item_1, foods);
		arrayAdapters.add(a);
		ListView listView = (ListView) findViewById(viewId);
		listView.setTextFilterEnabled(true);
		listView.setAdapter(a);
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
				FridgeFood food = (FridgeFood) parentList
						                        .getItemAtPosition(position);
				Bundle bundledFood = FridgeFood.bundleFood(food);

				Toast.makeText(parentContext,
						"Expiration Date: " + food.getExpirationDateString(),
						Toast.LENGTH_SHORT).show();

				expire = new Intent(parentContext, ExpireActivity.class);
				expire.putExtras(bundledFood);
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

	@Override
	public void update(Observable observable, Object data) {
		for (ArrayAdapter<?> a : arrayAdapters) {
			a.notifyDataSetChanged();
		}
		for (ExpState key : ExpState.values()) {
			setHeight(key.getListViewID());
		}
	}
}
