package com.openfridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ShoppingListActivity extends Activity {
	private ArrayAdapter<ShoppingItem> adapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			MainMenuActivity.client.reloadFoods();
		} catch (Exception e) {
			// For debugging
			e.printStackTrace();
		}
		setContentView(R.layout.shopping_list);

		adapter = new ArrayAdapter<ShoppingItem>(this,
				android.R.layout.simple_list_item_1,
				MainMenuActivity.client.getShoppingList());

		ListView lv = (ListView) findViewById(R.id.shoppingLV);
		lv.setTextFilterEnabled(true);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ShoppingListActivity.this.startActivity(new Intent(ShoppingListActivity.this, ShoppingActivity.class));
			}
		});
		// Make items not focusable to avoid listitem / button conflicts
		lv.setItemsCanFocus(false);

	}

	public void addItemToList(View view) {
		String itemToAdd = ((EditText) findViewById(R.id.editText1)).getText()
				.toString();

		adapter.add(new ShoppingItem(itemToAdd, 9, 1));

		Toast.makeText(view.getContext(), "Added Item: " + itemToAdd,
				Toast.LENGTH_SHORT).show();
	}
}
