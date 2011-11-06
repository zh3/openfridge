package com.openfridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//TODO Add the name of the item to the activity
//TODO Make the add to list push to shopping list
//TODO Change add to list to add to shopping list
//TODO Log action (Thrown/Eaten)
//TODO Thrown/Eaten should delete from web
//TODO Pass item info to edit/postpone
//TODO Make edit/postpone button link to activity
//TODO Make cancel button work

public class ExpireActivity extends Activity {
	
	private Intent expirationList, itemEdit;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        expirationList = new Intent(this, ExpirationListActivity.class);
        itemEdit = new Intent(this, ItemEditActivity.class);

        setContentView(R.layout.expire);
	}
	
	public void EditPostponeClick(View view){
		startActivity(itemEdit);
	}
	
	public void DoneClick(View view){
		startActivity(expirationList);
	}
	
	public void CancelClick(View view){
		startActivity(expirationList);
	}
}
