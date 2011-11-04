package com.openfridge;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ShoppingListActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FridgeFoodDataClient client = new FridgeFoodDataClient();
		try {
			client.reloadFoods();
		} catch (Exception e) {
			// For debugging
			e.printStackTrace();
		}
		List<ShoppingItem> good = Arrays.asList(new ShoppingItem("Milk",1,1), new ShoppingItem("Eggs",2,1),
				new ShoppingItem("Kale",3,1), new ShoppingItem("Beer",4,1), new ShoppingItem("Beef",5,1));
		
        setContentView(R.layout.shopping_list);
		initShoppingListView(R.id.shoppingLV, good);

	}
	private void initShoppingListView(int viewId, List<ShoppingItem> foods) {
		ListView listView = (ListView) findViewById(viewId);
		listView.setTextFilterEnabled(true);
		listView.setAdapter(new ArrayAdapter<ShoppingItem>(this,
				R.layout.list_item_with_remove, R.id.text, foods));
		
		// Make items not focusable to avoid listitem / button conflicts
		listView.setItemsCanFocus(false);
	}
	public void removeItem(View view) {
		startActivity(MainMenuActivity.expire);
	}

	public void addItemToList(View view) {
		String itemToAdd = ((EditText)findViewById(R.id.editText1)).getText().toString();
		
		ListView listView = (ListView) findViewById(R.id.shoppingLV);
		((ArrayAdapter<ShoppingItem>)listView.getAdapter()).add(new ShoppingItem(itemToAdd,9,1));
		
		Toast.makeText(view.getContext(), 
                "Added Item: " + itemToAdd, 
                Toast.LENGTH_SHORT).show();
	}
}
