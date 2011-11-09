package com.openfridge;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

//TODO Don't let it add blank SC
//TODO Make enter enter the item SC
//TODO Limit a max number of characters SC
//TODO Make delete post to server SC/JW
//DONE Parse shopping list JW

public class ShoppingListActivity extends Activity implements Observer {
	private ArrayAdapter<ShoppingItem> adapter;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_list);

		adapter = new ArrayAdapter<ShoppingItem>(this,
				android.R.layout.simple_list_item_multiple_choice,
				DataClient.getInstance().getShoppingList());
		
		ListView lv = (ListView) findViewById(R.id.shoppingLV);
		//lv.setTextFilterEnabled(true);
		lv.setAdapter(adapter);
	    // Make items not focusable to avoid listitem / button conflicts
        lv.setItemsCanFocus(false);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int arg2,
					long arg3) {
                      CheckedTextView textView = (CheckedTextView)v;
                      textView.setChecked(!textView.isChecked());			}
		}); 
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
		update(null, null);
		DataClient.getInstance().reloadFoods();
	}

	public void addItemToList(View view) {
		String itemToAdd = ((EditText) findViewById(R.id.editText1)).getText()
				.toString();

		adapter.add(new ShoppingItem(itemToAdd, null, DataClient.getInstance().getUID()));

		Toast.makeText(view.getContext(), "Added Item: " + itemToAdd,
				Toast.LENGTH_SHORT).show();
	}

    public void deleteChecked(View view) {
        ListView lv = (ListView) findViewById(R.id.shoppingLV);
        final SparseBooleanArray checkedItems = lv.getCheckedItemPositions();
    if (checkedItems == null) {
        // That means our list is not able to handle selection
        // (choiceMode is CHOICE_MODE_NONE for example)
        Toast.makeText(view.getContext(), "Nothing selected for deletion",
                Toast.LENGTH_SHORT).show();
        return;
    }

    // For each element in the status array
    final int checkedItemsCount = checkedItems.size();
    String itemsToDelete = "Delete from DB :";
    for (int i = 0; i < checkedItemsCount; ++i) {
        // This tells us the item position we are looking at
        final int position = checkedItems.keyAt(i);

        // And this tells us the item status at the above position
        final boolean isChecked = checkedItems.valueAt(i);

        // And we can get our data from the adapter like that
        if (isChecked)
            itemsToDelete += " " + adapter.getItem(position).toString();
        
        
    }

        Toast.makeText(view.getContext(), itemsToDelete,
                Toast.LENGTH_SHORT).show();
    }

	@Override
	public void update(Observable observable, Object data) {
		adapter.notifyDataSetChanged();
	}

}
