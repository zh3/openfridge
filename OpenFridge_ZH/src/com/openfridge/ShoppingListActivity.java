package com.openfridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ShoppingListActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list);
	}
	public void loadItemEdit(View view) {
		Intent intent = new Intent(this, ItemEditActivity.class);
		startActivity(intent);
	}

}
