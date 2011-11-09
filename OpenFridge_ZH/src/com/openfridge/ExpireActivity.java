package com.openfridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//TODO Make the add to list push to shopping list  EL
//TODO Change add to list to add to shopping list  EL
//TODO Log action (Thrown/Eaten)                   EL
//TODO Thrown/Eaten should delete from web         EL
//TODO Pass item info to edit/postpone             ZH
//TODO Make edit/postpone button link to activity  ZH

public class ExpireActivity extends Activity {
	
	private Intent expirationList, itemEdit;
	private FridgeFood food;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expire);
        
        TextView fdTV = (TextView) findViewById(R.id.foodDescription);
        Bundle bundledFood = getIntent().getExtras();
        food = FridgeFood.getFoodFromBundle(bundledFood);
        
        fdTV.setText(food.getDescription());

        expirationList = new Intent(this, ExpirationListActivity.class);
        itemEdit = new Intent(this, ItemEditActivity.class);
	}
	
	public void EditPostponeClick(View view){
		startActivity(itemEdit);
	}
	
	public void DoneClick(View view){
		finish();
	}
	
	public void CancelClick(View view){
		finish();
	}
}
